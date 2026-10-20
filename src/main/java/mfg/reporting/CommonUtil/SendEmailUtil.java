package mfg.reporting.CommonUtil;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.pojo.CommonSendEmail;
import mfg.reporting.service.SendEmailService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gszhang
 */
@Component
@Slf4j
public class SendEmailUtil {
    @Resource
    public SendEmailService sendEmailService;

    // 发送邮件底层的工具类
    public void callsendEmail(CommonSendEmail data) {
        try {
            // 发送邮件
            sendEmailService.callSendEmail(data);
        } catch (Exception e) {
            log.error("send email error: {}", e.getMessage());
        }
    }

    // 发送邮件底层的工具类
    public void callsendEmailAddCC(CommonSendEmail data) {
        try {
            // 发送邮件
            sendEmailService.callSendEmailAddCC(data);
        } catch (Exception e) {
            log.error("send email error: {}", e.getMessage());
        }
    }



    /**
     * autor:   gszhang
     * 发送MFG 需求报警邮件
     * @param data 列表数据告警
     * sender 发送人,subject 主题 ,receiver 收件人, content 内容；
     * 自动将 list 转 string mail 发送；
     *1.表格固定长度 -- 自适应宽度，最长100%
     *       2.单元格定制化宽度 -- 自适应宽度
     *       3.表格定制化颜色-- 全部都是Alarm，暂不需要，可以另外写公共方法；
     *       4.表格定制化字体 思源宋体
     *       5.sub Description 另外描述
     *       6.跳转link -- 通过<a>标签实现
     *       7.群发消息；--通过抄送人实现
     *       8. null 显示为空 -- 已解决
     */

    public void sendAlarmEmail(CommonSendEmail data) {
        try {
            if (data.getList().isEmpty()) {
                log.info("没有数据，邮件不发送");
                throw new ServiceException("没有数据，邮件不发送");
            }

            if (data.getList().size()> 400) {
                log.info("数据量过大，邮件不发送");
                throw new ServiceException("数据量过大，邮件不发送");
            }
            if(data.getHeaderList().isEmpty()){
                log.info("没有表头，邮件不发送");
                throw new ServiceException("没有表头，邮件不发送");
            }

            // 限定主样式
            StringBuilder sb = new StringBuilder();
            sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
            //增加 sub Description
            sb.append("<h3>Dear Sir:</h3>");
            if(!"".equals(data.getSubDesc())){
                sb.append("<p style='padding-left:10px'>").append(data.getSubDesc()).append("</p>");
            }

            //获取 headerList中 T 所有的label 值
            sb.append("<table><tr>");
            for (int i = 0; i < data.getHeaderList().size(); i++) {
                sb.append("<th>").append(data.getHeaderList().get(i).getValue()).append("</th>");
            }

            sb.append("</tr>");
            for (int i = 0; i < data.getList().size(); i++) {
                sb.append("<tr>");
                for (int j = 0; j < data.getHeaderList().size(); j++) {
                    String key = data.getHeaderList().get(j).getLabel();
                    if(!"".equals(data.getList().get(i).get(key))){
                        sb.append("<td>").append(data.getList().get(i).get(key)).append("</td>");
                    }else {
                        sb.append("<td></td>");
                    }
                }
                sb.append("</tr>");
            }
            String content = sb.toString().replace("<td>null</td>","<td></td>");
            data.setContent(content);
            callsendEmail(data);
        }catch (Exception e){
            log.error("send Alarm email error: {}", e.getMessage());
        }
    }



    /**
     * autor:   gszhang
     * 发送MFG 需求报警邮件
     * @param data 列表数据告警
     * sender 发送人,subject 主题 ,receiver 收件人, content 内容；
     * 自动将 list 转 string mail 发送；
     *1.表格固定长度 -- 自适应宽度，最长100%
     *       2.单元格定制化宽度 -- 自适应宽度
     *       3.表格定制化颜色-- 全部都是Alarm，暂不需要，可以另外写公共方法；
     *       4.表格定制化字体 思源宋体
     *       5.sub Description 另外描述
     *       6.跳转link -- 通过<a>标签实现
     *       7.群发消息；--通过抄送人实现
     *       8. null 显示为空 -- 已解决
     *       9.增加抄送人
     */

    public void sendAlarmEmailAddCC(CommonSendEmail data) {
        try {
            if (data.getList().isEmpty()) {
                log.info("没有数据，邮件不发送");
                throw new ServiceException("没有数据，邮件不发送");
            }

            if (data.getList().size()> 400) {
                log.info("数据量过大，邮件不发送");
                throw new ServiceException("数据量过大，邮件不发送");
            }
            if(data.getHeaderList().isEmpty()){
                log.info("没有表头，邮件不发送");
                throw new ServiceException("没有表头，邮件不发送");
            }

            // 限定主样式
            StringBuilder sb = new StringBuilder();
            sb.append("<html><style> table {border-collapse: collapse; } p, h3,div,span, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
            //增加 sub Description
            sb.append("<h3>Dear Sir:</h3>");
            if(!"".equals(data.getSubDesc())){
                sb.append("<p style='padding-left:10px'>").append(data.getSubDesc()).append("</p>");
            }

            //获取 headerList中 T 所有的label 值
            sb.append("<table><tr>");
            for (int i = 0; i < data.getHeaderList().size(); i++) {
                sb.append("<th>").append(data.getHeaderList().get(i).getValue()).append("</th>");
            }

            sb.append("</tr>");
            for (int i = 0; i < data.getList().size(); i++) {
                sb.append("<tr>");
                for (int j = 0; j < data.getHeaderList().size(); j++) {
                    String key = data.getHeaderList().get(j).getLabel();
                    if(!"".equals(data.getList().get(i).get(key))){
                        sb.append("<td>").append(data.getList().get(i).get(key)).append("</td>");
                    }else {
                        sb.append("<td></td>");
                    }
                }
                sb.append("</tr>");
            }
            String content = sb.toString().replace("<td>null</td>","<td></td>");
            data.setContent(content);
            callsendEmailAddCC(data);
        }catch (Exception e){
            log.error("send Alarm email error: {}", e.getMessage());
        }
    }

    /**
     * 发送ETL 需求报警邮件
     * @param text 文字告警
     */
    public void sendEtlAlarmEmailToReporting(String text) {
        try {
            if("".equals(text)){
                log.info("没有文本，邮件不发送");
                throw new ServiceException("没有文本，邮件不发送");
            }

            if(text.length() > 2000){
                log.info("文本过大，邮件不发送");
                throw new ServiceException("文本过大，邮件不发送");
            }
            CommonSendEmail data = new CommonSendEmail();
            data.setSender("A12MFG@nexchip.com.cn");
            data.setSubject("需求报警");
            data.setReceiver("gszhang;zhangshuxun;renmanding");
            data.setContent(text);
            callsendEmailAddCC(data);
        }catch(Exception e){
            log.error("send Reporing Alarm email error: {}", e.getMessage());
        }
    }

    public void sendNormalEmail(CommonSendEmail data) {
        try {
            if (data.getList().isEmpty()) {
                log.info("没有数据，邮件不发送");
                throw new ServiceException("没有数据，邮件不发送");
            }

            if (data.getList().size()> 400) {
                log.info("数据量过大，邮件不发送");
                throw new ServiceException("数据量过大，邮件不发送");
            }
            if(data.getHeaderList().isEmpty()){
                log.info("没有表头，邮件不发送");
                throw new ServiceException("没有表头，邮件不发送");
            }

            // 限定主样式
            StringBuilder sb = new StringBuilder();


            if(!"".equals(data.getContent())){
                sb.append(data.getContent());
            }

            //获取 headerList中 T 所有的label 值
            if(!data.getHeaderList().isEmpty()) {
                sb.append("<table><tr>");
            }
            for (int i = 0; i < data.getHeaderList().size(); i++) {
                sb.append("<th>").append(data.getHeaderList().get(i).getValue()).append("</th>");
            }

            sb.append("</tr>");
            for (int i = 0; i < data.getList().size(); i++) {
                sb.append("<tr>");
                for (int j = 0; j < data.getHeaderList().size(); j++) {
                    String key = data.getHeaderList().get(j).getLabel();
                    if(!"".equals(data.getList().get(i).get(key))){
                        sb.append("<td>").append(data.getList().get(i).get(key)).append("</td>");
                    }else {
                        sb.append("<td></td>");
                    }
                }
                sb.append("</tr>");
            }
            String content = sb.toString().replace("<td>null</td>","<td></td>");
            if(!data.getList().isEmpty()){
                sb.append("</table>");
            }
            sb.append("</body></html>");
            data.setContent(content);
            callsendEmail(data);
        }catch (Exception e){
            log.error("send Alarm email error: {}", e.getMessage());
        }
    }

}
