package mfg.reporting.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PullInwebPO implements Serializable {
    private String shiftDate;
    private String lotId;
    private String mainpdId;
    private String lotProcState;
    private Integer waferQty;
    private String prodspecId;
    private String stbTime;
    private String opeNo;
    private String lastClaimTime;
    private String casseteId;
    private String lotFinishedState;
    private String lastMainOpeno;
    private String prodg1;
    private String tech;
    private String flowId;
    private String requestDay;
    private String targetOpe;
    private String purpose;
    private Integer lotPriority;
    private String fcstDay;
    private Integer remainStep;
    private Float remainLayer;
    private Float remainDay;
    private Float needCt;
    private Float dur;
    private Integer tdTr;
    private String fComment;
    private Integer targetTr;
    private Integer diff;
    private String eqpg;
    private String holdcomment;
    private String fab;
    private String location;
    private Float stayTime;
    private String module;
    private String clipWeek;
    private Integer dthTargetTr;
    private Float stdStayTime;
    private Float stayTimeDiff;
    private String topFlag;
}
