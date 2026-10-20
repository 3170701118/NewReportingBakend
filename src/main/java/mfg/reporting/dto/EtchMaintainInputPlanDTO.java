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
public class EtchMaintainInputPlanDTO {
    //PRODG1,SHIFT_DATE,INPUT_PLAN_A12,INPUT_PLAN_A3,UPDATE_TIME,UPDATE_USER
    @ExcelProperty(value = {"PRODG1"}, index = 0)
    private String prodg1;
    @ExcelProperty(value = {"SHIFT_DATE"}, index = 1)
    private String shiftDate;
    @ExcelProperty(value = {"A12 input"}, index = 2)
    private Integer inputPlanA12;
    @ExcelProperty(value = {"A3 input"}, index = 3)
    private Integer inputPlanA3;
}
