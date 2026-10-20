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

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "A1MFG.SYS_FILE_MANAGE", dataSource = "pdwmfg")
public class FileManage implements Serializable{

    /**
     * 所属路由
     */
    @Column(value = "ROUTE_NAME")
     private String  routeName;
    /**
     * 自定义function_Id, rule : 多级路由+功能ID 如 PcMonitor_Wait_ETCH_UPLOAD_1
     */
     @Column(value = "FUNCTION_ID")
     @JsonProperty("functionId")
     private String functionId;

     /**上传的文件实际地址 */
     @Column(value = "FILE_NAME")
     @JsonProperty("fileName")
     private String  fileName;
    
     /**上传的文件生成的可下载路径 */
     @Column(value = "FILE_PATH")
     @JsonProperty("filePath")
     private String  filePath;

     /**上传的作用：Manager/Upload(分析)/Template */
     @Column(value = "FUNCTION_TYPE")
     @JsonProperty("functionType")
     private String  functionType ;

     /**对文件的Remark */
     @Column(value = "REMARK")
     @JsonProperty("remark")
     //给默认值
     @Default private String  remark = "";

     /***创建人 */
     @Column(value = "CREATE_USER")
     @JsonProperty("createUser")
     private String  createUser;

     /***创建时间 */
     @Column(value = "CREATE_TIME")
     @JsonProperty("createTime")
     private String  createTime;
    /***更新人 */
     @Column(value = "UPDATE_USER")
     @JsonProperty("updateUser")
     private String  updateUser;

     /**更新时间 */
     @Column(value = "UPDATE_TIME")
     @JsonProperty("updateTime")
     private String  updateTime;

}
