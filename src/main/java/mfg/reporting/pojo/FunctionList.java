package mfg.reporting.pojo;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "A1MFG.SYS_FUNCTION_LIST", dataSource = "pdwmfg")
public class FunctionList implements Serializable{

    /**
     * 所属路由
     */
    @Column(value = "MENU_ID")
    private String  menuId;

    /**
     * 所属路由
     */
    @Column(value = "MENU_NAME")
    private String  menuName;

    /**
     * 模块
     * */
    @Column(value = "FUNCTION_ID")
    private String  functionId;

    /**
     * 所属 owner
     * */
    @Column(value = "OWNER")
    private String  owner;

    /**
     *  开发人
     * */
    @Column(value = "DEVELOPER")
    private String  developer;

    /**
     *  页面标注
     * */
    @Column(value = "REMARK")
    private String  remark;

    /**
     *  前端 tooltips
     * */
    @Column(value = "TIPS")
    private String  tips;

    /**
     *  模块用的表格名
     * */
    @Column(value = "TABLE_NAME")
    private String  tableName;

    /**
     *  模块用的etl名
     * */
    @Column(value = "ETL_NAME")
    private String  etlName;

    /**
     *  更新时间
     * */
    @Column(value = "UPDATE_TIME")
    private String  updateTime;

    /**
     *  更新人
     * */
    @Column(value = "UPDATE_USER")
    private String  updateUser;
}
