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
public class MstFlowPathDTO implements Serializable  {

    private String locationFab;

    private String prodg1;

    private String prodspecId;

    private String routeId;

    private String operNo;

    private String eqpId;

    private String procUseFlag;

    private String procUpdateUser;

    private String procUpdateTime;

    private String mfgUseFlag;

    private String mfgUpdateUser;

    private String mfgUpdateTime;

}
