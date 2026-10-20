package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
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
@HeadRowHeight(30)  //表头行高
@ContentRowHeight(15)  //内容行高
@ColumnWidth(18)  //列宽
@ContentFontStyle(fontHeightInPoints = (short) 12) //字体大小
public class RevenueDataVO implements Serializable {

    @ExcelProperty(value = {"LINE"}, index = 0)
    private String line;

    @ExcelProperty(value = {"PRODG1"}, index = 1)
    private String prodg1;

    @ExcelProperty(value = {"REVENUE_TARGET"}, index = 2)
    private Integer revenueTarget;

    @ExcelProperty(value = {"REVENUE_DATE"}, index = 3)
    private String revenueDate;

    @ExcelProperty(value = {"RECEIVE_MORE"}, index = 4)
    private String receiveMore;

    @ExcelIgnore
    private String updateUser;

}
