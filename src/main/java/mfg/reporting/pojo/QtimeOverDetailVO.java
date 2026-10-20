package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@HeadRowHeight(30)  //表头行高
@ContentRowHeight(15)  //内容行高
@ColumnWidth(18)  //列宽
@ContentFontStyle(fontHeightInPoints = (short) 12) //字体大小
public class QtimeOverDetailVO implements Serializable {

    @ExcelProperty(value = {"WEEK"}, index = 0)
    private String WEEK;

    @ExcelProperty(value = {"PRODG1"}, index = 1)
    private String PRODG1;

    @ExcelProperty(value = {"LOT_ID"}, index = 2)
    private String LOT_ID;

    @ExcelProperty(value = {"ROUTE_ID"}, index = 3)
    private String CURRENT_MAINPD_ID;

    @ExcelProperty(value = {"TECH"}, index = 4)
    private String TECH;

    @ExcelProperty(value = {"班别"}, index = 5)
    private String ACLASS;

    @ExcelProperty(value = {"OVER_TIME"}, index = 6)
    private float OVER_TIME;

    @ExcelProperty(value = {"SHIFT_DATE"}, index = 7)
    private String SHIFT_DATE;

    @ExcelProperty(value = {"WAFER_QTY"}, index = 8)
    private Integer WAFER_QTY;

    @ExcelProperty(value = {"MODULE"}, index = 9)
    private String MODULE;

    @ExcelProperty(value = {"OPE_NO"}, index = 10)
    private String OPE_NO;

    @ExcelProperty(value = {"GRADE"}, index = 11)
    private String GRADE;

    @ExcelProperty(value = {"SPEC"}, index = 12)
    private Integer SPEC;

    @ExcelProperty(value = {"RESPONSE_MODULE"}, index = 13)
    private String RESPONSE_MODULE;

    @ExcelProperty(value = {"MAJOR_REASON"}, index = 14)
    private String MAJOR_REASON;

    @ExcelProperty(value = {"OVER_COMMENT"}, index = 15)
    private String OVER_COMMENT;

    @ExcelProperty(value = {"PLATFORM"}, index = 16)
    private String PLATFORM;

    @ExcelProperty(value = {"PRODSPEC_ID"}, index = 17)
    private String PRODSPEC_ID;

    @ExcelProperty(value = {"NENG_FLAG"}, index = 18)
    private String NENG_FLAG;

    @ExcelProperty(value = {"TARGET_TIME"}, index = 19)
    @DateTimeFormat("yyyy-MM-dd hh24:mi:ss")
    private String TARGET_TIME;

    @ExcelProperty(value = {"END_TIME"}, index = 20)
    @DateTimeFormat("yyyy-MM-dd hh24:mi:ss")
    private String END_TIME;

    @ExcelProperty(value = {"LOOP1"}, index = 21)
    private String LOOP1;

    @ExcelProperty(value = {"LOOP2"}, index = 22)
    private String LOOP2;

    @ExcelProperty(value = {"QT_CONCERN"}, index = 23)
    private String QT_CONCERN;

    @ExcelProperty(value = {"SUB_LOT_TYPE"}, index = 24)
    private String SUB_LOT_TYPE;

    @ExcelProperty(value = {"LOT_FAB"}, index = 25)
    private String LOT_FAB;

    @ExcelProperty(value = {"OVER_FAB"}, index = 26)
    private String OVER_FAB;

    @ExcelProperty(value = {"ACTION"}, index = 27)
    private String ACTION;

    @ExcelProperty(value = {"REMARK"}, index = 28)
    private String REMARK;

    @ExcelProperty(value = {"TARGET_MODULE"}, index = 29)
    private String TARGET_MODULE;

    @ExcelProperty(value = {"MFG_OVER"}, index = 30)
    private String MFG_OVER;

    @ExcelProperty(value = {"TRIGGER_OPE_NO"}, index = 31)
    private String TRIGGER_OPE_NO;

    @ExcelProperty(value = {"TARGET_OPE_NO"}, index = 32)
    private String TARGET_OPE_NO;

    @ExcelProperty(value = {"FLOW_KEY"}, index = 33)
    private String FLOW_KEY;

    @ExcelIgnore
    private String INSERT_TIME;


}
