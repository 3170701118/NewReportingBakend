package mfg.reporting.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapacityCommentDTO {


    @ExcelProperty(value = {"EQP_G"}, index = 0)
    public String eqpg;


    @ExcelProperty(value = {"Comment"}, index = 1)
    public String comment;

    @ExcelIgnore
    public String uploadComment1;


    @ExcelIgnore
    public String uploadComment2;

}
