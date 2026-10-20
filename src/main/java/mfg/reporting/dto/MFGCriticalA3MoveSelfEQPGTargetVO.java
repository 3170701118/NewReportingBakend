package mfg.reporting.dto;

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
public class MFGCriticalA3MoveSelfEQPGTargetVO implements Serializable {
    @ExcelProperty(value = {"EQP_G"}, index = 0)
    public String eqpg;

    @ExcelProperty(value = {"Target"}, index = 1)
    public String target;

}
