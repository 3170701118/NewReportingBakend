package mfg.reporting.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EqpUnitMonitorPO {
    private String eqpG;
    private String eqpId;
    private String module;
    private String alarmCode;
    private String unitId;
    private String unitFlag;
    private String occurTime;
    private String eqpStatus;
    private String eqpE10;
    private String unitStatus;
    private String unitE10;
    private String unitJudge;
    private String fcomment;
}
