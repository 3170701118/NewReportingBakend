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
public class DummyUploadVO implements Serializable  {
    @ExcelProperty(value = {"FAB"}, index = 0)
    public String fab;

    @ExcelProperty(value = {"PURPOSE"}, index = 1)
    public String purpose;

    @ExcelProperty(value = {"STD_F"}, index = 2)
    public Float stdf;

    @ExcelProperty(value = {"特殊水位"}, index = 3)
    public Integer specialStock;

    @ExcelProperty(value = {"COMMENT"}, index = 4)
    public String keyComment;

    @ExcelIgnore
    public String updateUser;
}
