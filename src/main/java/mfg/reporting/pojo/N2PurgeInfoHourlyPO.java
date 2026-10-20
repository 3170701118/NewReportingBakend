package mfg.reporting.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "A1MFG.N2_PURGE_INFO_HOURLY", dataSource = "pdwmfg")
public class N2PurgeInfoHourlyPO implements Serializable {
    @ExcelProperty(value = {"LOT_ID"}, index = 0)
    @Column(value = "LOT_ID")
    private String lotId;

    @ExcelProperty(value = {"PRODSPEC_ID"}, index = 1)
    @Column(value = "PRODSPEC_ID")
    private String prodspecId;

    @ExcelProperty(value = {"LOT STATE"}, index = 2)
    @Column(value = "LOT_PROC_STATE")
    private String lotProcState;

    @ExcelProperty(value = {"FOUP"}, index = 3)
    @Column(value = "CASSETE_ID")
    private String casseteId;

    @ExcelProperty(value = {"QTY"}, index = 4)
    @Column(value = "WAFER_QTY")
    private Integer waferQty;

    @ExcelProperty(value = {"NENG FLAG"}, index = 5)
    @Column(value = "NENG_FLAG")
    private Integer nengFlag;

    @ExcelProperty(value = {"XFER_STATE"}, index = 6)
    @Column(value = "XFER_STATE")
    private String xferState;

    @ExcelProperty(value = {"START DATE"}, index = 7)
    @Column(value = "MIN_SHIFT_DATE")
    private String minShiftDate;

    @ExcelProperty(value = {"SHIFT DATE"}, index = 8)
    @Column(value = "MAX_SHIFT_DATE")
    private String maxShiftDate;

    @ExcelProperty(value = {"FCST_DONE_TIME"}, index = 9)
    @Column(value = "FCST_DONE_TIME")
    private String fcstDoneTime;

    @ExcelProperty(value = {"LAST_OP"}, index = 10)
    @Column(value = "LAST_OP")
    private String lastOp;

    @ExcelProperty(value = {"RUN_COUNT"}, index = 11)
    @Column(value = "RUN_COUNT")
    private Integer runCount;

    @ExcelProperty(value = {"PRODG1"}, index = 12)
    @Column(value = "PRODG1")
    private String prodg1;

    @ExcelProperty(value = {"Pordg Owner"}, index = 13)
    @Column(value = "PRODG_OWNER")
    private String prodgOwner;

    @ExcelProperty(value = {"Pordg Owner DeptId"}, index = 14)
    @Column(value = "PRODG_DEPT_ID")
    private String prodgDeptId;

    @ExcelProperty(value = {"Pordg Owner EmpId"}, index = 15)
    @Column(value = "PRODG_EMP_ID")
    private String prodgEmpId;

    @ExcelProperty(value = {"Hold Dept"}, index = 16)
    @Column(value = "HOLD_DEPT_CNAME")
    private String holdDeptCname;

    @ExcelProperty(value = {"Hold Comment"}, index = 17)
    @Column(value = "HOLD_COMMENT")
    private String holdComment;

    @ExcelProperty(value = {"Hold Cname"}, index = 18)
    @Column(value = "HOLD_CNAME")
    private String holdCname;

    @ExcelProperty(value = {"REMAIN_QTIME"}, index = 19)
    @Column(value = "REMAIN_QTIME")
    private Float remainQtime;

    @ExcelProperty(value = {"lot Owner EmpId"}, index = 20)
    @Column(value = "LOT_EMP_ID")
    private String lotEmpId;

    @ExcelProperty(value = {"lot Owner Name"}, index = 21)
    @Column(value = "LOT_OWNER")
    private String lotOwner;

    @ExcelProperty(value = {"lot Owner DeptId"}, index = 22)
    @Column(value = "LOT_DEPT_ID")
    private String lotDeptId;

    @ExcelProperty(value = {"lot Owner DeptName"}, index = 23)
    @Column(value = "LOT_DEPT_CNAME")
    private String lotDeptCname;

    @ExcelProperty(value = {"FORCE_EQP_ID"}, index = 24)
    @Column(value = "FORCE_EQP_ID")
    private String forceEqpId;

    @ExcelIgnore
    @Column(value = "UPDATE_TIME")
    private String updateTime;
}
