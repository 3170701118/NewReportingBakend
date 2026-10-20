package mfg.reporting.pojo;

import java.io.Serializable;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.mybatisflex.annotation.Table;


import com.mybatisflex.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mfg.reporting.CommonUtil.ExcelEnumValue;
import mfg.reporting.enums.ReportingDemandStatusEnum;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "A1MFG.REPORTING_DEMANDS_LIST", dataSource = "pdwmfg")
public class ReportingDemandListPO implements Serializable {

  @Column(value = "DEMANDS_ID")
  @JsonProperty("demandsId")
  private String demandsId;

  @Column(value = "DEMANDS_NAME")
  @JsonProperty("demandsName")
  private String demandsName;

  @Column(value = "DEMANDS_USER")
  @JsonProperty("demandsUser")
  private String demandsUser;

  @Column(value = "DEMANDS_CONTENT")
  @JsonProperty("demandsContent")
  private String demandsContent;

  @Column(value = "DEVELOPER")
  @JsonProperty("developer")
  private String developer;

  @Column(value = "COMPLETION_RATE")
  @JsonProperty("completionRate")
  private Integer completionRate;

  @Column(value = "STATUS")
  @JsonProperty("status")
  @ExcelEnumValue(ReportingDemandStatusEnum.class)
  private Integer status;

  @Column(value = "RANK")
  @JsonProperty("rank")
  private String rank;

  @Column(value = "DEMANDS_CONFIRM_DATE")
  @JsonProperty("demandsConfirmDate")
  @DateTimeFormat("yyyy-MM-dd")
  private String demandsConfirmDate;

  @Column(value = "DEMANDS_FORECAST_DATE")
  @JsonProperty("demandsForecastDate")
  @DateTimeFormat("yyyy-MM-dd")
  private String demandsForecastDate;

  @Column(value = "START_DATE")
  @JsonProperty("startDate")
  @DateTimeFormat("yyyy-MM-dd")
  private String startDate;

  @Column(value = "COMPLETE_DATE")
  @JsonProperty("completeDate")
  @DateTimeFormat("yyyy-MM-dd")
  private String completeDate;


  @Column(value = "REMARK")
  @JsonProperty("remark")
  private String remark;

  @Column(value = "UPDATE_TIME")
  @JsonProperty("updateTime")
  private String updateTime;


  @Column(value = "UPDATE_USER")
  @JsonProperty("updateUser")
  private String updateUser;
}
