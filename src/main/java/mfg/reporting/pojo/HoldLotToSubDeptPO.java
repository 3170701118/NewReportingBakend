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
public class HoldLotToSubDeptPO implements Serializable {

    private String prodG1;
    private String chipBody;
    private String lotId;
    private String location;
    //归属生产课
    private String lotMfgModule;
    private Integer lotIdReal;
    private String prodSpecId;
    private String mainPdId;
    private String routeId;
    private String fab;
    private String opeNo;
    private String operNoReal;
    private String productOwnerEmpId;
    private String productOwnerSubDeptId;
    private String productOwnerSubDeptCname;
    private String moduleOwner;
    private String tech;
    private String eqpG;
    private String rule;
    private String sdeptId;
    private String sdeptCname;
}
