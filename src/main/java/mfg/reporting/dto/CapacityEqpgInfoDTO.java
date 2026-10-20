package mfg.reporting.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapacityEqpgInfoDTO {

        @ExcelIgnore
        private String MODULE;

        @ExcelProperty(value = {"EQP_G"}, index = 0)
        private String EQP_G;

        @ExcelProperty(value = {"BATCH_SIZE"}, index = 1)
        private Integer BATCH_SIZE;

        @ExcelProperty(value = {"基准机台"}, index = 2)
        private String STD_EQP_ID;

        @ExcelIgnore
        private Float STD_OEE_TARGET;

        @ExcelProperty(value = {"A12折算机台"}, index = 3)
        private Float A12_STD_COUNT;

        @ExcelProperty(value = {"A3折算机台"}, index = 4)
        private Float A3_STD_COUNT;

    @ExcelIgnore
        private Float TOTAL_STD_COUNT;

        @ExcelProperty(value = {"A12实际贡献机台"}, index = 5)
        private Float A12_ACT_COUNT;

        @ExcelProperty(value = {"A3实际贡献机台"}, index = 6)
        private Float A3_ACT_COUNT;


    @ExcelProperty(value = {"A12实际贡献机台"}, index = 7)
    private Float A12_SELF_COUNT;

    @ExcelProperty(value = {"A3实际贡献机台"}, index = 8)
    private Float A3_SELF_COUNT;

    @ExcelIgnore
        private Float TOTAL_ACT_COUNT;
    @ExcelIgnore
        private String UPDATE_USER;

        @ExcelProperty(value = {"F_COMMENT"}, index = 9)
        private String F_COMMENT;
    @ExcelIgnore
        private String UPDATE_TIME;

}
