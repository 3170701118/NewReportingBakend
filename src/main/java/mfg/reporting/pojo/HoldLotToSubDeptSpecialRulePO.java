package mfg.reporting.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldLotToSubDeptSpecialRulePO {
    //FUNCTION_NAME,F_COMMENT,KEY1,KEY2,KEY3,KEY4,KEY5,KEY6,UPDATE_USER,UPDATE_TIME
    private String functionName;
    private String fcomment;
    private String location;
    private String layer;
    private String moduleOwner;
    private String eqpG;
    private String sdeptId1;
    private String sdeptId2;
    private String updateUser;
    private String updateTime;
}
