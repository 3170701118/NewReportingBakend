
package mfg.reporting.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableScheduling
public class MultiDataSourceAutoScaleConfig {

    @Autowired
    private Map<String, DataSource> dataSources; // 注入所有数据源

    @Autowired
    private DruidAutoScaleProperties properties;

    @Scheduled(fixedRate = 15000) // 每15秒检查一次
    public synchronized void autoScale() {
        if (!properties.isEnabled()) {
            return;
        }

        // 遍历所有数据源进行扩展管理
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            String dataSourceName = entry.getKey();
            DataSource dataSource = entry.getValue();

            if (dataSource instanceof DruidDataSource) {
                DruidDataSource ds = (DruidDataSource) dataSource;
                performScaling(ds, dataSourceName);
            }
        }
    }

    /**
     * 执行单个数据源的扩展逻辑
     */
    private void performScaling(DruidDataSource ds, String dataSourceName) {
        try {
            int activeCount = ds.getActiveCount(); // 当前活跃连接数
            int poolingCount = ds.getPoolingCount(); // 池中连接数
            int waitThreadCount = ds.getWaitThreadCount(); // 等待线程数
            int currentMaxActive = ds.getMaxActive();
            int minIdle = ds.getMinIdle();

            // 扩容逻辑 - 基于活跃连接数和等待线程数判断
            if ((activeCount >= currentMaxActive * 0.8 ||
                    waitThreadCount >= properties.getThresholdWait()) &&
                    currentMaxActive < properties.getMaxLimit()) {

                int newMaxActive = Math.min(properties.getMaxLimit(),
                        currentMaxActive + properties.getStep());

                if (newMaxActive > currentMaxActive) {
                    ds.setMaxActive(newMaxActive);
                    System.out.println(String.format(
                            "DataSource [%s]: Expanded maxActive to %d (active: %d, waiting: %d)",
                            dataSourceName, newMaxActive, activeCount, waitThreadCount));
                }
            }
            // 缩容逻辑 - 更保守的缩容策略
            else if (activeCount < currentMaxActive * 0.3 &&
                    poolingCount <= minIdle &&
                    currentMaxActive > minIdle) {

                int newMaxActive = Math.max(minIdle, currentMaxActive - properties.getStep());

                if (newMaxActive != currentMaxActive) {
                    ds.setMaxActive(newMaxActive);
                    System.out.println(String.format(
                            "DataSource [%s]: Reduced maxActive to %d (active: %d, pooling: %d)",
                            dataSourceName, newMaxActive, activeCount, poolingCount));
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred during auto scaling for DataSource [" +
                    dataSourceName + "]: " + e.getMessage());
        }
    }
}