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
public class CapacityProdMixDTO {

    @ExcelIgnore
    private String TECH;

    @ExcelProperty(value = {"PRODUCT"}, index = 0)
    private String PRODUCT;

    @ExcelProperty(value = {"PRODUCT_MIX_N1A1"}, index = 1)
    private Integer PRODUCT_MIX_N1A1;

    @ExcelProperty(value = {"PRODUCT_MIX_N1A3"}, index = 2)
    private Integer PRODUCT_MIX_N1A3;

    @ExcelProperty(value = {"ENG_QTY_N1A3"}, index = 3)
    private Integer PRODUCT_MIX;

    @ExcelProperty(value = {"ENG_QTY_N1A1"}, index = 4)
    private Integer ENG_QTY_N1A1;

    @ExcelProperty(value = {"ENG_QTY_N1A3"}, index = 5)
    private Integer ENG_QTY_N1A3;


    @ExcelProperty(value = {"ENG_QTY_N1A3"}, index = 6)
    private Integer ENG_QTY;

    @ExcelIgnore
    private Integer TOTAL_QTY;

    @ExcelIgnore
    private String UPDATE_USER;

    @ExcelIgnore
    private String UPDATE_TIME;

}
