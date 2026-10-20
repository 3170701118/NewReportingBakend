package mfg.reporting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SGSWipSummaryDTO implements Serializable  {

    private String tech;

    private String sgsGroup;

    private String operNo;

    private Integer currentA1Wip;

    private Integer currentA3Wip;

    private Integer tdA1Wip;

    private Integer tdA3Wip;

    private Integer day1A1Wip;

    private Integer day1A3Wip;

    private Integer day2A1Wip;

    private Integer day2A3Wip;

    private Integer day3A1Wip;

    private Integer day3A3Wip;

    private Integer day4A1Wip;

    private Integer day4A3Wip;

    private Integer day5A1Wip;

    private Integer day5A3Wip;

    private Integer day6A1Wip;

    private Integer day6A3Wip;

}
