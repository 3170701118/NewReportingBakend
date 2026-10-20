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
public class MstFlowDybDTO implements Serializable  {
    private String Fab;

    private String locationFab;

    private String prodg1;

    private String lotId;

    private String routeId;

    private String operNo;

    private String preOperNo;

    private Integer waferQty;


}
