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
public class MstFlowWipDTO implements Serializable  {

    private String castId;

    private String prodg1;

    private String prodspecId;

    private String fab;

    private String locationFab;

    private String lotID;

    private String mainpdId;

    private String operNo;

    private String eqpg;

    private String path;

    private Integer waferQty;

    private Integer lotPriority;

    private String priorityType;

    private String sgsEqp;

    private String subLotType;

    private String flowBatch;

    private String batchFur;

    private String batchFlag;

    private String eqpId;

    private String xferState;

    private String state;

    private Float duration;

    private Float remainQtime;

    private String triggerOpNo;

    private String targetOpNo;

    private String targetTime;

    private String holdComment;

    private String sampling;

    private Integer remainLayer;


}
