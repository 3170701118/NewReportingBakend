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
public class SGSWipDetailDTO implements Serializable  {

    private String locationFab;

    private String fab;

    private String tech;

    private String prodg1;

    private String prodspecId;

    private String lotId;

    private Integer waferQty;

    private Integer neng;

    private Integer lotPriority;

    private String lotProcState;

    private String currentOperNo;

    private String operNo;

    private String wipCategory;

    private Integer closeHour;

    private String sgsGroup;

    private String sgsEqp;

    private String fcstDate;

    private String fcstTime;



}
