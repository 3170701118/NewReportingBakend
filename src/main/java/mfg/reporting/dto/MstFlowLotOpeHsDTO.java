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
public class MstFlowLotOpeHsDTO implements Serializable  {

    private String locationFab;

    private String mainpdId;

    private String operNo;

    private String claimTime;

    private String claimUser;

    private String opeCategory;

    private String eqpId;

    private String claimMemo;

    private String opeMode;

    private String holdUser;

    private String reasonCode;

    private String flowbatchId;

}
