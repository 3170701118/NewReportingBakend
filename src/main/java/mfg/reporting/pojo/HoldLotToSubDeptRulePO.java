package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldLotToSubDeptRulePO {
    //EQP_G,MODULE_OWNER,OPE_NO,RULE,SDEPT_ID,UPDATE_USER,UPDATE_TIME,TECH

    @ExcelIgnore
    private String tech;
    @ExcelProperty(value = {"EQP_G"}, index = 1)
    private String eqpG;
    @ExcelIgnore
    private String module;
    @ExcelProperty(value = {"MODULE_OWNER"}, index = 2)
    private String moduleOwner;
    @ExcelIgnore
    private String opeNo;
    @ExcelProperty(value = {"RULE"}, index = 3)
    private String rule;
    @ExcelIgnore
    private String ruleDesc;
    @ExcelIgnore
    private String sdeptCname;
    @ExcelIgnore
    private String updateFlag;
    @ExcelProperty(value = {"Location"}, index = 0)
    private String Location;
    @ExcelProperty(value = {"SDEPT_ID"}, index = 4)
    private String sdeptId="";
    @ExcelIgnore
    private String updateUser;
    @ExcelIgnore
    private String updateTime;
}
