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
public class MoveTargetPO implements Serializable {

    //<!-- LOT_FAB,LOCATION,TECH,PRODG1,OPE_NO,MOVE_TARGET,END_DATE,MODULE,DTH_MOVE,DTH_TARGET,MOVE_RATE -->

    private String lotFab;
    private String location;
    private String tech;
    private String prodg1;
    private String opeNo;
    private Integer moveTarget;
    private String endDate;
    private String module;
    private Integer processQTY;
    private Integer dthMove;
    private Integer dthTarget;
    private Integer moveRate;
}
