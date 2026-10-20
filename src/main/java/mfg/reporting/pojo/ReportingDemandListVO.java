package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mfg.reporting.CommonUtil.ExcelEnumValue;
import mfg.reporting.Configuration.ExcelEnumValueConverter;
import mfg.reporting.enums.ReportingDemandStatusEnum;

import java.io.Serializable;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@HeadRowHeight(30)  //表头行高
@ContentRowHeight(15)  //内容行高
@ColumnWidth(18)  //列宽
@ContentFontStyle(fontHeightInPoints = (short) 12) //字体大小
public class ReportingDemandListVO implements Serializable {

    @ExcelIgnore
    @Column(value = "DEMANDS_ID")
    private String demandsId;

    @Column(value = "DEMANDS_NAME")
    @JsonProperty("demandsName")
    @ExcelProperty(value = {"需求名称"}, index = 0)
    private String demandsName;

    @ExcelProperty(value = {"需求人"}, index = 1)
    private String demandsUser;

    @ExcelProperty(value = {"需求内容"}, index = 2)
    private String demandsContent;

    @ExcelProperty(value = {"开发人"}, index = 3)
    private String developer;

    @ExcelProperty(value = {"进度"}, index = 4 )
    @NumberFormat("0")
    private Integer completionRate;

    @ExcelProperty(value = {"需求状态"}, index = 5 ,converter = ExcelEnumValueConverter.class)
    @ExcelEnumValue(ReportingDemandStatusEnum.class)
    private Integer status;

    @ExcelProperty(value = {"排序"}, index = 6)
    @NumberFormat("0")
    private Integer rank;

    @ExcelProperty(value = {"需求确认时间"}, index = 7)
    @DateTimeFormat("yyyy-MM-dd")
    private String demandsConfirmDate;

    @ExcelProperty(value = {"预计完成时间"}, index = 8)
    @DateTimeFormat("yyyy-MM-dd")
    private String demandsForecastDate;

    @ExcelProperty(value = {"开始时间"}, index = 9)
    @DateTimeFormat("yyyy-MM-dd")
    private String startDate;

    @ExcelProperty(value = {"完成时间"}, index = 10)
    @DateTimeFormat("yyyy-MM-dd")
    private String completeDate;


    @ExcelProperty(value = {"备注"}, index = 11)
    private String remark;

    @ExcelIgnore
    private String updateTime;


    @ExcelIgnore
    private String updateUser;
}
