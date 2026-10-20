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
public class DownSamplingSRTargetPO implements Serializable {

    @ExcelProperty(value = {"TECH"}, index = 0)
    private String tech;
    @ExcelIgnore
    private String prodg1;
    @ExcelProperty(value = {"OPE_NO"}, index = 1)
    private String opeNo;
    @ExcelProperty(value = {"STD_SR"}, index = 2)
    private Integer stdSR;
    @ExcelIgnore
    private String updateUser;
    @ExcelIgnore
    private String updateTime;
}
