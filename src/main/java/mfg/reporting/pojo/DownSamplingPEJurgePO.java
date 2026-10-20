package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownSamplingPEJurgePO implements Serializable {

    @ExcelProperty(value = {"TECH"}, index = 0)
    private String tech;
    @ExcelProperty(value = {"PRODG1"}, index = 1)
    private String prodg1;
    @ExcelProperty(value = {"PRODSPEC_ID"}, index = 2)
    private String prodspecId;
    @ExcelProperty(value = {"FLOW_ID"}, index = 3)
    private String flowId;
    @ExcelProperty(value = {"OPE_NO"}, index = 4)
    private String opeNo;
    @ExcelIgnore
    private Integer move;
    @ExcelIgnore
    private String eqpG;
    @ExcelIgnore
    private String claimTimeMax;
    @ExcelIgnore
    private String claimTimeMin;
    @ExcelProperty(value = {"SR"}, index = 7)
    private Double sr;
    @ExcelProperty(value = {"MODULE"}, index = 5)
    private String module;
    @ExcelProperty(value = {"LAST_MAIN_OPE"}, index = 9)
    private String lastMainOpe;
    @ExcelProperty(value = {"STD_SR"}, index = 8)
    private Double stdSr;
    private String uploadTime;
    @ExcelProperty(value = {"JURGE_RESULT"}, index = 10)
    private String jurgeResult;
    @ExcelProperty(value = {"WIP"}, index = 6)
    private Integer wip;
    private String srResult;
    private String updateUser;
}
