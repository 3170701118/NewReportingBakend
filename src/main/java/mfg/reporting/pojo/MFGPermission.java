package mfg.reporting.pojo;

import java.io.Serializable;

import com.mybatisflex.annotation.Table;


import com.mybatisflex.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(value = "A1MFG.SYS_PERMISSION_MANAGE", dataSource = "pdwmfg")
public class MFGPermission implements Serializable {
    /** 需要权限的ID ，上传的ID,下载的ID,目录的menuId*/
    @Column(value = "FUNCTION_ID")
    @JsonProperty("functionId")
    private String functionId;

    /** 有权限的人员EMAIL,多个用英文分号分隔 */
    @Column(value = "CNAMES")
    @JsonProperty("cnames")
    private String cnames;

    /** 有权限的课,PB11-生产系统课;，多个用英文分号分隔 */
    @Column(value = "SDEPT")
    @JsonProperty("sdept")
    private String sdept;

    /** 有权限的部门，PB11; ,多个用英文分号分隔 */
    @Column(value = "DEPT")
    @JsonProperty("dept")
    private String dept;

    /** 最低POWER,预留字段 */
    @Column(value = "POWER")
    @JsonProperty("power")
    private int power;

    /** 生效权限场景，0.管理员，1.全部，2.cnames，3.指定SDEPT，4.指定DEPT，5.指定cnames和SDEPT，6.指定cnames和DEPT，7.指定SDEPT和DEPT，8.指定cnames、SDEPT和DEPT */
    @Column(value = "ACTIVE_FLAG")
    @JsonProperty("activeFlag")
    private int activeFlag;

    /** 备注 */
    @Column(value = "REMARK")
    @JsonProperty("remark")
    private String remark;

    /** 更新时间 */
    @Column(value = "UPDATE_TIME")
    @JsonProperty("updateTime")
    private String updateTime;

    /** 更新人 */
    @Column(value = "UPDATE_USER")
    @JsonProperty("updateUser")
    private String updateUser;

}
