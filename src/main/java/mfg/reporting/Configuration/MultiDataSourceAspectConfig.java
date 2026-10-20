
package mfg.reporting.Configuration;

import com.mybatisflex.spring.datasource.DataSourceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MultiDataSourceAspectConfig {

    @Bean
    public DataSourceInterceptor dataSourceInterceptor() {
        return new DataSourceInterceptor();
    }
}

