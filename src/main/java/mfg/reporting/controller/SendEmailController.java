package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SendEmailUtil;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.CommonSendEmail;
import mfg.reporting.service.NtoPathService;
import mfg.reporting.service.SendEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author gszhang
 */
@RestController
@RequestMapping("/sendEmail")
@Slf4j
public class SendEmailController {
    @Resource
    public SendEmailService sendEmailService;

    @Resource
    public SendEmailUtil util;

    @Resource
    public NtoPathService ntoPathService;

    @GetMapping("/photoReworkLotList")
    public ResponseResult<Void> getMenuListInfo(){
        List<Map<String,Object>> list = sendEmailService.getReworkLotList();
        CommonSendEmail data = new CommonSendEmail();
        List<CommonSelectDTO> headerList = new ArrayList<>();
        headerList.add(new CommonSelectDTO("STATE","状态"));
        headerList.add(new CommonSelectDTO("MODULE","Module"));
        headerList.add(new CommonSelectDTO("PRODG1","PRODG1"));
        headerList.add(new CommonSelectDTO("LOT_ID","LOT_ID"));
        headerList.add(new CommonSelectDTO("LOCATION","Location"));
        headerList.add(new CommonSelectDTO("LOT_PROC_STATE","STATE"));
        headerList.add(new CommonSelectDTO("OPE_NO","OPE_NO"));
        headerList.add(new CommonSelectDTO("A12_COUNT","A12 PATH_CNT"));
        headerList.add(new CommonSelectDTO("A3_COUNT","A3 PATH_CNT"));
        headerList.add(new CommonSelectDTO("WAFER_QTY","QTY"));
        headerList.add(new CommonSelectDTO("DUR","DUR"));
        headerList.add(new CommonSelectDTO("REMAIN_QTIME","REMAIN QTIME"));
        headerList.add(new CommonSelectDTO("HOLD_MODULE","HOLD MODULE"));
        headerList.add(new CommonSelectDTO("HOLD_COMMENT","HOLD COMMENT"));
        data.setHeaderList(headerList);
        data.setList(list);
        data.setSubDesc("This is a Rework Lot Alarm Email");
        data.setSubject("Rework Lot Alarm");
        data.setSender("A12MFG@nexchip.com.cn");
        data.setReceiver("gszhang");
        util.sendAlarmEmail(data);
        return ResponseResult.success("发送成功");
    }


    /**
     * 系统抓取PLM系统自动导入NTO
     * @return
     */
    @GetMapping("/sendAutoInsertMail")
    public ResponseResult<Map<String, Object>> sendAutoInsertMail()
    {
        try {
            //获取需要发送的prodg
            List<Map<String,Object>> list = sendEmailService.getAutoInsertProdgList();
            for (Map<String, Object> map : list) {
                String prodg =map.get("PRODG").toString();
                String chipbody = map.get("CHIPBODY").toString();
                sendInsertProdgMail(prodg,chipbody);
            }
            //更新flag
            ntoPathService.updateInputFlag();
            ntoPathService.updateInputFlagOther();
            return ResponseResult.success("发送系统抓取PLM系统自动导入NTOMail成功");
        } catch (Exception e) {
            log.error("发送系统抓取PLM系统自动导入NTOMail失败", e);
            return ResponseResult.fail("发送系统抓取PLM系统自动导入NTOMail失败");
        }
    }


    /**
     * 维护新产品时mail通知PMC
     * @param prodg
     * @param chipbody
     * @return
     */
    @GetMapping("/sendInsertProdgMail")
    public ResponseResult<Map<String, Object>> sendInsertProdgMail(
            @RequestParam(value = "prodg") String prodg,@RequestParam(value = "chipbody") String chipbody
    ) {
        try {
            CommonSendEmail data = new CommonSendEmail();
            data.setSubject("NTO Path管控");
            data.setSender("A12MFG@nexchip.com.cn");
            //发给PMC除部经理外的人员
            //List<Map<String,Object>> list = sendEmailService.getPA20Recivers("PA20");
            //String emails = list.stream()
            //        .map(map -> map.get("EMAIL"))      // 提取EMAIL字段值
            //        .filter(Objects::nonNull)          // 过滤null值
            //        .map(Object::toString)             // 转为String
            //        .filter(email -> !email.trim().isEmpty()) // 可选：过滤空字符串
            //        .collect(Collectors.joining(","));
            //emails=emails+",PB11-01@nexchip.com.cn";
            //data.setReceiver("zhangshuxun,steviejin,gaoliang");
            String emails="steviejin@nexchip.com.cn;PA20@nexchip.com.cn;PB11-012@nexchip.com.cn";
            data.setReceiver(emails);
            StringBuilder sb = new StringBuilder();
            sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
            sb.append("<h3>Dear All:</h2>");
            sb.append("<div>");
            sb.append("新导入NTO产品：");
            sb.append(prodg);
            sb.append(",");
            sb.append(chipbody);
            sb.append(",请PMC相关人员点击以下链接DataMaintain模块维护该产品下线厂别");
            sb.append("</br>");
            sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
            sb.append("</div>");
            data.setContent(sb.toString());
            util.callsendEmail(data);
            return ResponseResult.success("发送Insert Prodg Mail 成功");

        } catch (Exception e) {
            log.error("发送Insert Prodg Mail 失败", e);
            return ResponseResult.fail("发送Insert Prodg Mail 失败");
        }
    }

    /**
     * PMC维护厂别后mail通知对应厂别制造部
     * @param prodg
     * @param chipbody
     * @param fab
     * @return
     */
    @GetMapping("/sendMfgMaintainEqpMail")
    public ResponseResult<Map<String, Object>> sendMfgMaintainEqpMail(
             @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
            ,@RequestParam(value = "fab") String fab
    ){
        try {
            CommonSendEmail data = new CommonSendEmail();
            data.setSubject("NTO Path管控");
            data.setSender("A12MFG@nexchip.com.cn");
            //发给MFG
            String deptid="PB11";
            if("A3".equals(fab)){
                deptid="PH11";
            }
            //List<Map<String,Object>> list = sendEmailService.getMfgRecivers(deptid);
            //String emails = list.stream()
            //        .map(map -> map.get("EMAIL"))      // 提取EMAIL字段值
            //        .filter(Objects::nonNull)          // 过滤null值
            //        .map(Object::toString)             // 转为String
            //        .filter(email -> !email.trim().isEmpty()) // 可选：过滤空字符串
            //        .collect(Collectors.joining(","));
            //emails=emails+",PB11-01@nexchip.com.cn";
            //data.setReceiver("zhangshuxun,steviejin,gaoliang");
            String emails="steviejin@nexchip.com.cn;"+deptid+"@nexchip.com.cn"+";PB11-012@nexchip.com.cn";
            data.setReceiver(emails);
            StringBuilder sb = new StringBuilder();
            sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
            sb.append("<h3>Dear All:</h2>");
            sb.append("<div>");
            sb.append("PMC维护NTO产品厂别为");
            sb.append(fab);
            sb.append("：");
            sb.append(prodg);
            sb.append(",");
            sb.append(chipbody);
            sb.append(",请制造相关人员点击以下链接DataMaintain模块维护该产品建议机台");
            sb.append("</br>");
            sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
            sb.append("</div>");
            data.setContent(sb.toString());
            util.callsendEmail(data);
            return ResponseResult.success("发送MfgMaintainEqp成功");

        } catch (Exception e) {
            log.error("发送MfgMaintainEqp失败", e);
            return ResponseResult.fail("发送MfgMaintainEqp失败");
        }
    }

    /**
     * MFG建议机台维护完成后mail各MODULE参照开path
     * @return
     */
    @GetMapping("/sendModuleOpenPathMail")
    public ResponseResult<Map<String, Object>> sendModuleOpenPathMail()
    {
        try {
            //获取需要发送module的部门
            List<Map<String,Object>> list = sendEmailService.getMfgToModule();
            for (Map<String, Object> map : list) {
                String prodg =map.get("PRODG1").toString();
                String deptid = map.get("DEPT_ID").toString();
                String emails="";
                if(deptid.contains("/")){
                    String[] parts = deptid.split("/");
                    for(String part : parts) {
                        emails=emails+part+"@nexchip.com.cn;";
                    }
                }else{
                    emails = deptid+"@nexchip.com.cn;";
                }
                emails="steviejin@nexchip.com.cn;"+emails+"PB11-012@nexchip.com.cn";
                CommonSendEmail data = new CommonSendEmail();
                data.setSubject("NTO Path管控");
                data.setSender("A12MFG@nexchip.com.cn");
                //data.setReceiver("zhangshuxun");
                data.setReceiver(emails);
                log.info(emails);
                StringBuilder sb = new StringBuilder();
                sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
                sb.append("<h3>Dear All:</h3>");
                sb.append("<div>");
                sb.append(prodg);
                sb.append("制造建议机台已维护完成,请");
                sb.append(deptid);
                sb.append("参照建议机台开Path");
                sb.append("</br>");
                sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
                sb.append("</div>");
                data.setContent(sb.toString());
                util.callsendEmail(data);
                //插入mail记录
                sendEmailService.insertNtoMailLog("MFG_TO_MODULE",prodg,deptid);
            }
            return ResponseResult.success("发送ModuleOpenPathMail成功");
        } catch (Exception e) {
            log.error("发送ModuleOpenPathMail失败", e);
            return ResponseResult.fail("发送ModuleOpenPathMail失败");
        }
    }


    /**
     * 投片大于200mail module不达标的部分加开path
     * @return
     */
    @GetMapping("/sendInputOver200ModuleOpenPathMail")
    public ResponseResult<Map<String, Object>> sendInputOver200ModuleOpenPathMail()
    {
        try {
            //获取需要发送module的部门
            List<Map<String,Object>> list = sendEmailService.getOver200ToModule();
            for (Map<String, Object> map : list) {
                String prodg =map.get("PRODG1").toString();
                String deptid = map.get("DEPT_ID").toString();
                CommonSendEmail data = new CommonSendEmail();
                data.setSubject("NTO Path管控");
                data.setSender("A12MFG@nexchip.com.cn");
                String emails="";
                if(deptid.contains("/")){
                    String[] parts = deptid.split("/");
                    for(String part : parts) {
                        emails=emails+part+"@nexchip.com.cn;";
                    }
                }else{
                    emails = deptid+"@nexchip.com.cn;";
                }
                emails="steviejin@nexchip.com.cn;"+emails+";PB11-012@nexchip.com.cn";
                //data.setReceiver("zhangshuxun");
                data.setReceiver(emails);
                //发送mail的细项内容
                List<Map<String,Object>> listDetail = sendEmailService.getOver200ToModuleDetail(prodg,deptid);
                StringBuilder sb = new StringBuilder();
                sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
                sb.append("<h3>Dear All:</h3>");
                sb.append("<div><p>");
                sb.append(prodg);
                sb.append("投片大于200PCS,请");
                sb.append(deptid);
                sb.append("参照建议机台开Path");
                sb.append("</br>");
                sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
                sb.append("</p></div>");
                data.setContent(sb.toString());
                List<CommonSelectDTO> headerList = new ArrayList<>();
                headerList.add(new CommonSelectDTO("PRODG1","PRODG1"));
                headerList.add(new CommonSelectDTO("CHIPBODY","CHIPBODY"));
                headerList.add(new CommonSelectDTO("OPER_NO","OPER_NO"));
                headerList.add(new CommonSelectDTO("EQP_G","EQP_G"));
                headerList.add(new CommonSelectDTO("MODULE","MODULE"));
                headerList.add(new CommonSelectDTO("MFG_EQP_ID","MFG建议机台"));
                headerList.add(new CommonSelectDTO("PATHS_LIST","PATHS_LIST"));
                headerList.add(new CommonSelectDTO("FOLLOW_RATE","FOLLOW_RATE"));
                data.setHeaderList(headerList);
                data.setList(listDetail);
                //util.callsendEmail(data);
                util.sendNormalEmail(data);
                //插入mail记录
                sendEmailService.insertNtoMailLog("OVER200_TO_MODULE",prodg,deptid);
            }
            return ResponseResult.success("发送InputOver200ModuleOpenPathMail成功");
        } catch (Exception e) {
            log.error("发送InputOver200ModuleOpenPathMail失败", e);
            return ResponseResult.fail("发送InputOver200ModuleOpenPathMail失败");
        }
    }

    /**
     * 反查MFG建议群组未填写部分
     * @return
     */
    @GetMapping("/sendMaintainToModuleMail")
    public ResponseResult<Map<String, Object>> sendMaintainToModuleMail()
    {
        try {
            //获取需要发送module的部门
            List<Map<String,Object>> list = sendEmailService.getMaintainToModule();
            for (Map<String, Object> map : list) {
                String prodg =map.get("PRODG1").toString();
                String deptid = map.get("DEPT_ID").toString();
                CommonSendEmail data = new CommonSendEmail();
                data.setSubject("NTO Path管控");
                data.setSender("A12MFG@nexchip.com.cn");
                String emails = deptid+"@nexchip.com.cn";
                emails="steviejin@nexchip.com.cn;"+emails+";PB11-012@nexchip.com.cn";
                //data.setReceiver("zhangshuxun,steviejin");
                data.setReceiver(emails);
                //发送mail的细项内容
                List<Map<String,Object>> listDetail = sendEmailService.getMaintainToModuleDetail(prodg,deptid);
                StringBuilder sb = new StringBuilder();
                sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
                sb.append("<h3>Dear All:</h3>");
                sb.append("<div><p>");
                sb.append(prodg);
                sb.append("没有维护MFG建议机台的群组如下");
                //sb.append(deptid);
                //sb.append("参照建议机台开Path");
                sb.append("</br>");
                sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
                sb.append("</p></div>");
                data.setContent(sb.toString());
                List<CommonSelectDTO> headerList = new ArrayList<>();
                headerList.add(new CommonSelectDTO("FAB","FAB"));
                headerList.add(new CommonSelectDTO("PRODG1","PRODG1"));
                headerList.add(new CommonSelectDTO("EQP_G","EQP_G"));
                headerList.add(new CommonSelectDTO("MODULE","MODULE"));
                headerList.add(new CommonSelectDTO("EQP_ID","EQP_ID"));
                headerList.add(new CommonSelectDTO("SDEPT_CNAME","SDEPT_CNAME"));
                headerList.add(new CommonSelectDTO("DEPT_ID","DEPT_ID"));
                data.setHeaderList(headerList);
                data.setList(listDetail);
                //util.callsendEmail(data);
                util.sendNormalEmail(data);
                //插入mail记录
                sendEmailService.insertNtoMailLog("MAINTAIN_TO_MODULE",prodg,deptid);
            }
            return ResponseResult.success("发送MaintainToModuleMail成功");
        } catch (Exception e) {
            log.error("发送MaintainToModuleMail失败", e);
            return ResponseResult.fail("发送MaintainToModuleMail失败");
        }
    }

    @GetMapping("/sendMaintainEQPGToOPDMail")
    public ResponseResult<Map<String, Object>> sendMaintainEQPGToOPDMail()
    {
        try {
            List<Map<String,Object>> list = sendEmailService.getMaintainEQPGToOPD();
            for (Map<String, Object> map1 : list) {
                String prodg =map1.get("PRODG1").toString();
                String deptid = map1.get("DEPTID").toString();
                //List<Map<String,Object>> listdept = sendEmailService.getPA20Recivers(deptid);
                //String emails = listdept.stream()
                //        .map(map -> map.get("EMAIL"))      // 提取EMAIL字段值
                //        .filter(Objects::nonNull)          // 过滤null值
                //        .map(Object::toString)             // 转为String
                //        .filter(email -> !email.trim().isEmpty()) // 可选：过滤空字符串
                //        .map(email -> email + "@nexchip.com.cn") // 拼接域名
                //        .collect(Collectors.joining(","));
                //emails=emails+",PB11-01@nexchip.com.cn";
                String emails="steviejin@nexchip.com.cn;"+deptid+"@nexchip.com.cn"+";PB11-012@nexchip.com.cn";
                CommonSendEmail data = new CommonSendEmail();
                data.setSubject("NTO Path管控");
                data.setSender("A12MFG@nexchip.com.cn");
                //data.setReceiver("zhangshuxun");
                log.info(emails);
                data.setReceiver(emails);
                StringBuilder sb = new StringBuilder();
                sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
                sb.append("<h3>Dear All:</h3>");
                sb.append("<div>");
                sb.append("新下线NTO产品");
                sb.append(prodg);
                sb.append(",请OPD及时Maintain FLOW EQPG");
                sb.append("</br>");
                sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
                sb.append("</div>");
                data.setContent(sb.toString());
                util.callsendEmail(data);
                //插入mail记录
                sendEmailService.insertNtoMailLog("MAIL_TO_OPD",prodg,deptid);
            }
            return ResponseResult.success("发送MaintainEQPGToOPDMail成功");
        } catch (Exception e) {
            log.error("发送MaintainEQPGToOPDMail失败", e);
            return ResponseResult.fail("发送MaintainEQPGToOPDMail失败");
        }
    }

    /**
     * 发送未维护产品给产品Owner
     * @return
     */
    @GetMapping("/sendNotMaintainToOwnerMail")
    public ResponseResult<Map<String, Object>> sendNotMaintainToOwnerMail()
    {
        try {
            List<Map<String,Object>> list = sendEmailService.getNotMaintainToOwner();
            for (Map<String, Object> map1 : list) {
                String prodg =map1.get("PRODG1").toString();
                String email = map1.get("EMAIL").toString();
                //List<Map<String,Object>> listdept = sendEmailService.getPA20Recivers(deptid);
                //String emails = listdept.stream()
                //        .map(map -> map.get("EMAIL"))      // 提取EMAIL字段值
                //        .filter(Objects::nonNull)          // 过滤null值
                //        .map(Object::toString)             // 转为String
                //        .filter(email -> !email.trim().isEmpty()) // 可选：过滤空字符串
                //        .map(email -> email + "@nexchip.com.cn") // 拼接域名
                //        .collect(Collectors.joining(","));
                //emails=emails+",PB11-01@nexchip.com.cn";
                String emails=email+"@nexchip.com.cn"+";PB11-012@nexchip.com.cn";
                CommonSendEmail data = new CommonSendEmail();
                data.setSubject("NTO Path管控");
                data.setSender("A12MFG@nexchip.com.cn");
                //data.setReceiver("zhangshuxun");
                log.info(emails);
                data.setReceiver(emails);
                StringBuilder sb = new StringBuilder();
                sb.append("<html><style> table {border-collapse: collapse; } p, h3, th, td {font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;} th, td {border: 1px solid #ddd; padding: 8px;} th {background-color: #15559a; color: white;} </style> <body>");
                sb.append("<h3>Dear All:</h3>");
                sb.append("<div>");
                sb.append("新Download产品");
                sb.append(prodg);
                sb.append(",请前往如下链接新增该产品");
                sb.append("</br>");
                sb.append("<a>http://10.51.184.35:8090/NewReporting/common/ntoPathControl</a>");
                sb.append("</div>");
                data.setContent(sb.toString());
                util.callsendEmail(data);
            }
            return ResponseResult.success("发送NotMaintainToOwnerMail成功");
        } catch (Exception e) {
            log.error("发送NotMaintainToOwnerMail失败", e);
            return ResponseResult.fail("发送NotMaintainToOwnerMail失败");
        }
    }



}
