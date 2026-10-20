package mfg.reporting.CommonUtil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * @author gszhang
 */ //接受 因不同环境 配置的参数
@Component  
@Data
@ConfigurationProperties(prefix="custom")
public class CustomParam {  

    /**管理文件类型的存储路径 */
    private String manageFileSaveLocation;

    /**上传文件类型的存储路径 */
    private String uploadFileSaveLocation;

    /**模板文件类型的存储路径 */
    private String templateFileSaveLocation;


    /**
     * 点击率log获取路径；
     */
    private String clickLogLocation;

}  
