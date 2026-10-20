package mfg.reporting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueChartDTO implements Serializable {

    private String revenueDate;
    private Integer revenueTarget;
    private Integer revenueOutputFcst;
    private Integer diff;
    private Integer revenueTargetAcc;
    private Integer revenueOutputFcstAcc;
    private Integer diffAcc;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "0.0")
    private Double ratio;

}
