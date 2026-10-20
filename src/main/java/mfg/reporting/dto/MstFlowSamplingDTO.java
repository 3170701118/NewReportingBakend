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
public class MstFlowSamplingDTO implements Serializable  {

    private String customer;

    private String ctg;

    private String prodg1;

    private String prodspecId;

    private String module;

    private String operNo;

    private String mainSuffix;

    private String samplingRate;


}
