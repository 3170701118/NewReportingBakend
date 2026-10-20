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
public class RMSMonitorDetailsPO implements Serializable {
    String tech;
    String prodg1;
    String module;
    String moduleOwner;
    String eqpId;
    String opeNo;
    String ppName;
    String productStatus;
    String prodspecId;
    Integer curWip;
    Integer wipClose3d;
    String trackComment;
    String scannerComment;
    String recipeComment;
    String summaryComment;
    String eqpg;
    String chamberRule;
    String wip;
    String ec;
}
