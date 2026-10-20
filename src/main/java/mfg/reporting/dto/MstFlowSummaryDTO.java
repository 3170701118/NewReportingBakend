package mfg.reporting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MstFlowSummaryDTO implements Serializable  {
    private String updateTime;

    private String eqpg;

    private String module;

    private String procStage;

    private Integer operSeq;

    private String operNo;

    private String foupCategory;

    private Integer qtime;

    private String qtimeOperNo;

    private String sampling;

    private Integer totalWip;

    private Integer totalWipSum;

    private Integer runWip;

    private String runQtimeFlag;

    private String runSHLFlag;

    private Integer waitWip;

    private String waitQtimeFlag;

    private String waitSHLFlag;

    private Float waitTime;

    private Integer holdWip;

    private String holdQtimeFlag;

    private String holdSHLFlag;

    private Float holdTime;

    private Integer tdMove;

    private Integer ydMove;

    private Integer pre7dMove;

    private Float stdCycRemain;

    private Integer backupWip;

    private Integer backupWipSum;

    private Integer backupWipRun;

    private Integer backupWipWait;

    private Float backupWipWaitTime;

    private Integer backupWipHold;

    private Float backupWipHoldTime;

    private Integer bankWip;

    private Integer lotPriority;

    private String cutLineOpeNo;

    private String sgsWip;

}
