package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :  gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatHoldLot implements Serializable {

    @ExcelProperty(value = {"LOT_ID"}, index = 3)
    private String lotId;
    @ExcelProperty(value = {"OPE_NO"}, index = 4)
    private String opeNo;
    @ExcelProperty(value = {"QTY"}, index = 5)
    private String waferQty;
    @ExcelIgnore
    private String prodSpecId;
    @ExcelProperty(value = {"STATE"}, index = 6)
    private String lotProcState;
    @ExcelIgnore
    private String lotType;
    @ExcelIgnore
    private String subLotType;
    @ExcelProperty(value = {"PRODG1"}, index = 7)
    private String prodG1;
    @ExcelProperty(value = {"TECH"}, index = 8)
    private String tech;
    @ExcelProperty(value = {"Hold Comment"}, index = 14)
    private String holdClaimMemo;
    @ExcelProperty(value = {"Hold Module"}, index = 13)
    private String module;
    @ExcelIgnore
    private String startTime;
    @ExcelProperty(value = {"最长Hold Time"}, index = 9)
    private String addDur;
    @ExcelProperty(value = {"Hold Dur"}, index = 10)
    private String holdTime;
    @ExcelProperty(value = {"COMMENT2"}, index = 2)
    private String scrapComment;
    @ExcelProperty(value = {"COMMENT1"}, index = 1)
    private String comment1;
    @ExcelIgnore
    private String scrapFlag;
    @ExcelProperty(value = {"状态"}, index = 0)
    private String clipStatus;
    @ExcelIgnore
    private String commitDate;
    @ExcelProperty(value = {"是否可出"}, index = 11)
    private String outputFlag;
    @ExcelProperty(value = {"是否逾期"}, index = 12)
    private String nrelease;
}
