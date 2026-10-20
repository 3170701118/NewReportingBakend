package mfg.reporting.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "druid.auto-scale")
public class DruidAutoScaleProperties {
    private boolean enabled = true;
    private int maxLimit = 100;       // 最大连接数限制
    private int step = 5;             // 扩展步长
    private int thresholdWait = 10;   // 等待线程数阈值

    // getter and setter methods
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getMaxLimit() { return maxLimit; }
    public void setMaxLimit(int maxLimit) { this.maxLimit = maxLimit; }

    public int getStep() { return step; }
    public void setStep(int step) { this.step = step; }

    public int getThresholdWait() { return thresholdWait; }
    public void setThresholdWait(int thresholdWait) { this.thresholdWait = thresholdWait; }
}
