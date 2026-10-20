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
public class EtchMaintainStdTcDTO {

    @ExcelProperty(value = {"PRODG1"}, index = 0)
    private String prodg1;
    @ExcelProperty(value = {"OPE_NO"}, index = 1)
    private String opeNo;
    @ExcelProperty(value = {"EQP_G"}, index = 2)
    private String eqpG;
    @ExcelProperty(value = {"MODULE"}, index = 3)
    private String module;
    @ExcelProperty(value = {"STD_TC"}, index = 4)
    private Float stdTc;
}
