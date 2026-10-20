package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class NTOPathProdgDetailVO implements Serializable {

    @ExcelProperty(value = {"PRODG1"}, index = 0)
    private String PRODG1;

    @ExcelProperty(value = {"OPER_NO"}, index = 1)
    private String OPER_NO;

    @ExcelProperty(value = {"OPER_SEQ"}, index = 2)
    private Integer OPER_SEQ;

    @ExcelProperty(value = {"LAYER_NO"}, index = 3)
    private String LAYER_NO;

    @ExcelProperty(value = {"LAYER_SEQ"}, index = 4)
    private Integer LAYER_SEQ;

    @ExcelProperty(value = {"TECH"}, index = 5)
    private String TECH;

    @ExcelProperty(value = {"EQP_G"}, index = 6)
    private String EQP_G;

    @ExcelProperty(value = {"DEPT_ID"}, index = 7)
    private String DEPT_ID;

    @ExcelProperty(value = {"MFG_EQP_ID"}, index = 8)
    private String MFG_EQP_ID;

    @ExcelProperty(value = {"PATHS_LIST"}, index = 9)
    private String PATHS_LIST;

    @ExcelProperty(value = {"NTO_FOLLOW_RATE"}, index = 10)
    private Float NTO_FOLLOW_RATE;

    @ExcelProperty(value = {"FOLLOW_RATE"}, index = 11)
    private Float FOLLOW_RATE;
}
