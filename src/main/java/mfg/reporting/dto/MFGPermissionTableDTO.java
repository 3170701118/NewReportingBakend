package mfg.reporting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MFGPermissionTableDTO implements Serializable {

    /**
     * 所属目录Id
     */
    private String menuId;

    /**
     * 所属目录名字
     */
    private String menuName;

    /**
     * 权限Id
     */
    private String functionId;

    /**
     * 权限模式
     * 生效的场景：
     *0.管理员权限 Reporting，
     *1.全部权限，2.个人权限，3.课权限，4.部门权限,5.指定cnames和SDEPT，6.指定cnames和DEPT，7.指定SDEPT和DEPT
     *8.指定cnames、SDEPT和DEPT,9.指定部门且职位（power）
     */
    private Integer activeFlag;

    /**
     * 涉及个人的权限 emails
     */
    private String emails;

    /**
     * 涉及个人的权限 cnames
     */
    private String cnames;

    /**
     * 涉及课的权限 格式 部门Id-课名
     */
    private String sdeptId;

    /**
     * 涉及课的权限 格式 部门
     */
    private String sdeptCname;

    /**
     * 涉及部门的权限 格式 部门Id；部门Id
     */
    private String deptId;

    /**
     * 涉及部门的权限 格式 部门中文名称
     */
    private String deptCname;

    /**
     * 涉及职位 power 400 / 500
     */
    private String power;

    /**
     * 涉及职位 power 对应的职位
     */
    private String powerCname;

    /**
     * 涉及职位 power 对应的remanrk
     */
    private String remark;

}
