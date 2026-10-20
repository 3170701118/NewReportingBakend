package mfg.reporting.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TFClnSpecialRuleDTO {

    @ExcelProperty(value = {"TOOL_NAME"}, index = 0)
    private String TOOL_NAME;

    @ExcelProperty(value = {"SPECIAL_FLAG"}, index = 1)
    private String SPECIAL_FLAG;
}
