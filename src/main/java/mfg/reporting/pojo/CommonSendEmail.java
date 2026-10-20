package mfg.reporting.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mfg.reporting.dto.CommonSelectDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonSendEmail implements Serializable {
    /**
     * 邮件发送人,默认为 A12MFG
     */
    private String sender = "A12MFG@nexchip.com.cn";
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件接收人
     */
    private String receiver;

    /**
     * 邮件抄送人
     */
    private String receiverCC = "";

    /**
     * 邮件描述；内容的附属描述；如对于表格内容的描述，以及数据的网页链接补充
     */
    private String subDesc;

    /**
     * 邮件内容；
     */
    private String content = "";

    /**
     * 邮件表格内容；
     * 支持HTML;
     */
    private List<Map<String,Object>> list;

    /**
     * 需要发送Email 的表头字段 及中文内容
     */
    private List<CommonSelectDTO> headerList;

}
