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
public class NTOPathMFGMaintainVO implements Serializable {

    @ExcelProperty(value = {"FAB"}, index = 0)
    private String FAB;

    @ExcelProperty(value = {"PRODG"}, index = 1)
    private String PRODG;

    @ExcelProperty(value = {"CHIPBODY"}, index = 2)
    private String CHIPBODY;

    @ExcelProperty(value = {"MODULE"}, index = 3)
    private String MODULE;

    @ExcelProperty(value = {"EQP_G"}, index = 4)
    private String EQP_G;

    @ExcelProperty(value = {"EQP_ID"}, index = 5)
    private String EQP_ID;
}
