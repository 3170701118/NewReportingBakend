package mfg.reporting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gszhang
 * @date 2025/02/17
 * @description 营收DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTO implements Serializable  {

    private String shiftMonth;


    private String customer;


    private String line;

    private String prodg1;


    private Integer revenueTarget;

    private String revenueDate;

    private String receiveMore;

    private String tech;

    private Integer outputPlan;

    private Integer revenueGap;

    private Integer oqcMtd;

    private Integer monthCommitQty;

    private Integer revenueCommitQty;

    private Integer wtWip;

    private Integer wtHoldWip;


    private Integer wtHoldCount;


    private Integer monthOutputFcst;


    private Integer revenueOutputFcst;


    private Integer outputDiff;


    private Integer revenueDiff;


    private Integer revenueWpOutput;


    private String needCt;


    private Integer remainLayer;


    private String lotId;
    private String opeNo;
}
