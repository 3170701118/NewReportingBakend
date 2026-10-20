package mfg.reporting.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MakeUpPlanVO {

    @ExcelProperty(index = 0)
    private String SHIFT_DATE;


    @ExcelProperty(index = 1)
    private String FAB;


    @ExcelProperty(index = 2)
    private String MAKEUP_PLAN;
}