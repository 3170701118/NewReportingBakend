package mfg.reporting.pojo;

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
public class PullInWebVO implements Serializable {

    @ExcelProperty(value = {"LOT_ID"}, index = 0)
    public String lotId;

    @ExcelProperty(value = {"Request Date"}, index = 1)
    public String requestDate;

    //Target OPE
    @ExcelProperty(value = {"Target OPE"}, index = 2)
    public String targetOPE;

    //purpose
    @ExcelProperty(value = {"Purpose"}, index = 3)
    public String purpose;

    public String getRequestDate() {
        return requestDate;
    }
}
