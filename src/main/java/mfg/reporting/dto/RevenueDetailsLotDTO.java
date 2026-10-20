package mfg.reporting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDetailsLotDTO implements Serializable {
    private String lotId;
    private Integer waferQty;
    private String opeNo;
    private String prodspecId;
    private String lotProcState;
    private String line;
    private String prodg1;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "0.0")
    private Double holdTime;
    private String holdClaimMemo;
    private String fcstDay;
}
