package mfg.reporting.service;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SendEmailUtil;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.CisDedicateMonitorMapper;
import mfg.reporting.pojo.CommonSendEmail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gszhang
 */
@Slf4j
@Service
public class CisDedicateMonitorService {
    @Resource
    public CisDedicateMonitorMapper mapper;

    @Resource
    public JwtUtils jwtUtils;

    @Resource
    public SendEmailUtil emailUtil;

    public List<Map<String, Object>> selectCISDedicateTableList() {
        try {
            return mapper.selectCISDedicateTableList();
        } catch (Exception e) {
            throw new RuntimeException("查询失败");
        }
    }

    public void addNewCISDedicateMonitorEqp(String eqpId) {
        try {
            Map<String, Object> data = mapper.getEqpInfoByEqpId(eqpId);
            if (data.isEmpty()) {
                throw new ServiceException("未找到对应的机台码");
            }

            if (data.containsKey("FLAG") && data.get("FLAG").equals("N")) {
                throw new ServiceException("输入的机台码不符合Rule,请重新输入");
            }
            String UPDATE_USER = jwtUtils.getUsernameFromToken();
            data.put("UPDATE_USER", UPDATE_USER);
            mapper.insertEqpInfoForCISMonitor(data);
        } catch (Exception e) {
            throw new RuntimeException("查询失败");
        }
    }

    public void deleteEqpInfoForCISMonitor(String eqpId) {
        mapper.deleteEqpInfoForCISMonitor(eqpId);
    }

    public void CISMonitorMailAlarm() {
        List<Map<String, Object>> list = mapper.selectCISDedicateTableList();
        // 增加Alarm 历史表；
        List<Map<String, Object>> resultHis = list.stream()
                .filter(item ->  "Y".equals(item.get("PATH_FLAG")))
                .collect(Collectors.toList());

        if(resultHis.size() == 0){
            log.info("无数据，不发送mail");
            return;
        }

        //有数据的话，直接结果插入到历史表中
        mapper.insertCISDedicateMonitorHis();

        //hashCode 六个生产课循环发mail
        List<String> moduleList = Arrays.asList("PHOTO", "ETCH", "DIFF", "TF", "WET", "CMP");
        List<String> fabList = Arrays.asList("N1A1", "N1A3");

        moduleList.forEach(module -> {
            //过滤出对应的生产课
            fabList.forEach(fab -> {
                List<Map<String, Object>> result = new ArrayList<>();

                if("N1A1".equals(fab)){
                    result = list.stream()
                            .filter(item -> module.equals(item.get("MODULE"))&& "Y".equals(item.get("PATH_FLAG")) && ("N1A1".equals(item.get("FAB")) || "N1A2".equals(item.get("FAB"))))
                            .collect(Collectors.toList());
                }

                if("N1A3".equals(fab)){
                    result = list.stream()
                            .filter(item -> module.equals(item.get("MODULE"))&& "Y".equals(item.get("PATH_FLAG")) && ("N1A3".equals(item.get("FAB")) || "N1A4".equals(item.get("FAB"))))
                            .collect(Collectors.toList());
                }


                if (result.size() > 0) {
                    if (!"PHOTO".equals(module) && !"ETCH".equals(module) && !"DIFF".equals(module) && !"TF".equals(module) && !"WET".equals(module) && !"CMP".equals(module)) {
                        log.error("无对应的生产课，暂不发送此条Alarm");
                        return;
                    }

                    if (!"N1A1".equals(fab) && !"N1A2".equals(fab) && !"N1A3".equals(fab) && !"N1A4".equals(fab)) {
                        log.error("无对应的fab，暂不发送此条Alarm");
                        return;
                    }

                    //触发邮件机制；
                    CommonSendEmail sendEmailPO = new CommonSendEmail();
                    sendEmailPO.setSubDesc("网页链接：<a href='http://n1mfgs04:8090/NewReporting/project/CISDedicateMonitor'>CIS Dedicate Monitor</a>");

                    sendEmailPO.setSubject("CIS Dedicate 机台误开Alarm");
                    List<CommonSelectDTO> tableHeaderList = new ArrayList<>();
                    tableHeaderList.add(new CommonSelectDTO("EQP_ID", "EQP_ID"));
                    tableHeaderList.add(new CommonSelectDTO("UTIL", "Util"));
                    tableHeaderList.add(new CommonSelectDTO("EQP_G", "EQP_G"));
                    tableHeaderList.add(new CommonSelectDTO("MODULE", "Module"));
                    tableHeaderList.add(new CommonSelectDTO("PATH_FLAG", "非CIS PATH未关"));
                    tableHeaderList.add(new CommonSelectDTO("TECH_LIST", "Details"));
                    sendEmailPO.setHeaderList(tableHeaderList);
                    sendEmailPO.setList(result);
                    String receiver = getCisMoinitorMailReciverByModule(module,fab);
                    //String receiver = "gszhang@nexchip.com.cn";
                    sendEmailPO.setReceiver(receiver);
                    //每小时执行一次 ，只有当前是 7点 / 16点 才抄送厂级，否则只发送给对应的生产课&部门
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24小时制
                    String ReceiverCC = "";
                    if(hour == 7 || hour == 16) {
                        log.info("当前时间是 {} 点，发送厂级抄送", hour);
                         ReceiverCC = getCisMoinitorMailReciverCCByModule(module, fab);
                        //String ReceiverCC = "gszhang@nexchip.com.cn";
                        sendEmailPO.setReceiverCC(ReceiverCC);
                    }

                    log.info("已发送Mail,记录当前Mail的条件：Module: {}, Fab: {}, Receiver: {}, ReceiverCC: {}", module, fab, receiver, ReceiverCC);
                    log.info("result：{}",result);

                    emailUtil.sendAlarmEmailAddCC(sendEmailPO);
                }
            });
        });
    }

    public List<Map<String, Object>> GetCISDedicateDetailsByTech(String tech, String eqpId) {
        List<Map<String, Object>> result = new ArrayList<>();

        //判断 eqpId 是否带 .
        String eqpChamberFlag = "EQP";
        String fab = "N1A1";
        if (eqpId != null && eqpId.contains(".")) {
            eqpChamberFlag = "Chamber";
            String actEqpId = eqpId.substring(0, eqpId.indexOf("."));
            if (actEqpId.length() == 6) {
                fab = "N1A3";
            }
        } else {
            if (eqpId.length() == 6) {
                fab = "N1A3";
            }
        }

        if (fab == "N1A1" && eqpChamberFlag == "EQP") {
            result = mapper.getN1A1EqpCISDedicateDetail(eqpId, tech);
        }

        if (fab == "N1A1" && eqpChamberFlag == "Chamber") {
            result = mapper.getN1A1ChamberCISDedicateDetail(eqpId, tech);
        }

        if (fab == "N1A3" && eqpChamberFlag == "EQP") {
            result = mapper.getN1A3EqpCISDedicateDetail(eqpId, tech);
        }

        if (fab == "N1A3" && eqpChamberFlag == "Chamber") {
            result = mapper.getN1A3EqpCISDedicateDetail(eqpId, tech);
        }

        return result;
    }

    private String getCisMoinitorMailReciverByModule(String module, String fab) {
        String receiver = "";

        if ("N1A1".equals(fab) || "N1A2".equals(fab)) {
            if ("PHOTO".equals(module)) {
                receiver = receiver.concat("PB11-032@nexchip.com.cn,PB21@nexchip.com.cn");
            }
            if ("ETCH".equals(module)) {
                receiver = receiver.concat("PB11-042@nexchip.com.cn,PB31@nexchip.com.cn");
            }
            if ("DIFF".equals(module)) {
                receiver = receiver.concat("PB11-052@nexchip.com.cn,PB51@nexchip.com.cn,RK70@nexchip.com.cn");
            }

            if ("TF".equals(module)) {
                receiver = receiver.concat("PB11-062@nexchip.com.cn,PB41@nexchip.com.cn");
            }

            if ("WET".equals(module) || "CMP".equals(module)) {
                receiver = receiver.concat("PB11-082@nexchip.com.cn,PF21@nexchip.com.cn");
            }
        }

        if ("N1A3".equals(fab) || "N1A4".equals(fab)) {
            if ("PHOTO".equals(module)) {
                receiver = receiver.concat("PH11-032@nexchip.com.cn,PH21@nexchip.com.cn");
            }
            if ("ETCH".equals(module)) {
                receiver = receiver.concat("PH11-042@nexchip.com.cn,PH31@nexchip.com.cn");
            }
            if ("DIFF".equals(module)) {
                receiver = receiver.concat("PH11-052@nexchip.com.cn,PH51@nexchip.com.cn");
            }

            if ("TF".equals(module)) {
                receiver = receiver.concat("PH11-062@nexchip.com.cn,PH41@nexchip.com.cn");
            }

            if ("WET".equals(module) || "CMP".equals(module)) {
                receiver = receiver.concat("PH11-08@nexchip.com.cn,PH71@nexchip.com.cn");
            }
        }

        if("".equals(receiver)){
            log.error("无对应的FAB -- {},module -- {}，暂不发送此条Alarm",fab,module);
            throw new ServiceException("无对应的FAB，暂不发送此条Alarm");
        }
        return receiver;
    }


    private String getCisMoinitorMailReciverCCByModule(String module, String fab) {
        String receiverCC = "aaronhsieh@nexchip.com.cn,frankwu@nexchip.com.cn,";
        if ("N1A1".equals(fab) || "N1A2".equals(fab)) {
            receiverCC = receiverCC.concat("cwshih@nexchip.com.cn,derekhsu@nexchip.com.cn,");
        }

        if ("N1A3".equals(fab) || "N1A4".equals(fab)) {
            receiverCC = receiverCC.concat("cclin@nexchip.com.cn,alexweng@nexchip.com.cn,");
        }

        receiverCC = receiverCC.concat("alberttian@nexchip.com.cn,pb11-012@nexchip.com.cn,PT11-02@nexchip.com.cn,PD80@nexchip.com.cn");

        return receiverCC;
    }


    public void mailFP_FpolyForL111GFFP10(String fab) {
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            CommonSendEmail emailPO = new CommonSendEmail();


            List<CommonSelectDTO> tableHeaderList = new ArrayList<>();
            tableHeaderList.add(new CommonSelectDTO("PRODG1", "PRODG1"));
            tableHeaderList.add(new CommonSelectDTO("PRODUCTID", "PRODUCTID"));
            tableHeaderList.add(new CommonSelectDTO("ROUTEID", "ROUTEID"));
            tableHeaderList.add(new CommonSelectDTO("OPENO", "OPENO"));
            tableHeaderList.add(new CommonSelectDTO("EQUIPMENTID", "EQUIPMENTID"));
            tableHeaderList.add(new CommonSelectDTO("PASS_FLG_PRC", "PASS_FLG_PRC"));
            tableHeaderList.add(new CommonSelectDTO("PRC_UPT_USER", "PRC_UPT_USER"));
            tableHeaderList.add(new CommonSelectDTO("PRC_UPT_TIME", "PRC_UPT_TIME"));
            tableHeaderList.add(new CommonSelectDTO("PASS_FLG_MFG", "PASS_FLG_MFG"));
            tableHeaderList.add(new CommonSelectDTO("MFG_UPT_USER", "MFG_UPT_USER"));
            tableHeaderList.add(new CommonSelectDTO("MFG_UPT_TIME", "MFG_UPT_TIME"));

            emailPO.setHeaderList(tableHeaderList);
            if ("N1A1".equals(fab)) {

                result = mapper.getFP_FpolyForL111GFFP10mailN1A1();
                emailPO.setList(result);
                emailPO.setReceiver("PB11-052@nexchip.com.cn,PB51-04@nexchip.com.cn");
            } else if ("N1A3".equals(fab)) {
                result = mapper.getFP_FpolyForL111GFFP10mailN1A3();
                emailPO.setList(result);
                emailPO.setReceiver("PH11-052@nexchip.com.cn,PH51-01@nexchip.com.cn");
            }
            log.info("FP_Fpoly result：{}", result);
            emailPO.setReceiverCC("gszhang@nexchip.com.cn");
            emailPO.setSubject("FP_Fpoly For L11平台 1G.FFP10站点 不允许开UF");
            emailPO.setSubDesc("1G.FFP10站点 不允许开UF;为避免品质风险,请立即关闭!");
            emailUtil.sendAlarmEmailAddCC(emailPO);
        }catch (Exception e){
            log.error("发送FP_FpolyForL111GFFP10邮件异常：{}",e.getMessage());
            throw new ServiceException("发送 FP_Fpoly For L11 1G.FFP10邮件异常");
        }

    }



    public void mailProdgOPE_UF_Inhibit(String fab) {
        List<Map<String, Object>> result = new ArrayList<>();

        List<CommonSelectDTO> tableHeaderList = new ArrayList<>();
        tableHeaderList.add(new CommonSelectDTO("PRODG1", "PRODG1"));
        tableHeaderList.add(new CommonSelectDTO("PRODUCTID", "PRODUCTID"));
        tableHeaderList.add(new CommonSelectDTO("ROUTEID", "ROUTEID"));
        tableHeaderList.add(new CommonSelectDTO("OPENO", "OPENO"));
        tableHeaderList.add(new CommonSelectDTO("EQUIPMENTID", "EQUIPMENTID"));
        tableHeaderList.add(new CommonSelectDTO("PASS_FLG_PRC", "PASS_FLG_PRC"));
        tableHeaderList.add(new CommonSelectDTO("PRC_UPT_USER", "PRC_UPT_USER"));
        tableHeaderList.add(new CommonSelectDTO("PRC_UPT_TIME", "PRC_UPT_TIME"));
        tableHeaderList.add(new CommonSelectDTO("PASS_FLG_MFG", "PASS_FLG_MFG"));
        tableHeaderList.add(new CommonSelectDTO("MFG_UPT_USER", "MFG_UPT_USER"));
        tableHeaderList.add(new CommonSelectDTO("MFG_UPT_TIME", "MFG_UPT_TIME"));

        CommonSendEmail emailPO = new CommonSendEmail();
        emailPO.setHeaderList(tableHeaderList);


        if ("N1A1".equals(fab)) {

            result = mapper.getProdgOPE_UF_Inhibit_mail_N1A1();
        } else if ("N1A3".equals(fab)) {
            result = mapper.getProdgOPE_UF_Inhibit_mail_N1A3();
        }

        log.info("ProdgOPE_UF_Inhibit result：{}", result);
        if(result.size()>0){
            // 获取 result 的 MAIL 字段 去重
            Set<String> mailSet = new HashSet<>();
            for (Map<String, Object> map : result) {
                mailSet.add(map.get("MAIL").toString());
            }

            for (String mail : mailSet) {
                //过滤不同的 mail
                List<Map<String, Object>> mailResult = result.stream()
                        .filter(item -> mail.equals(item.get("MAIL")))
                        .collect(Collectors.toList());
                if(mailResult.size()>0) {
                    emailPO.setList(mailResult);
                    emailPO.setReceiver(mail);
                    emailPO.setList(result);
                    emailPO.setReceiverCC("gszhang@nexchip.com.cn");
                    emailPO.setSubject("以下suffix ,站点 U/F禁止打开");
                    emailPO.setSubDesc("以下suffix ,站点 不允许开U/F;为避免品质风险,请立即关闭!");
                    emailUtil.sendAlarmEmailAddCC(emailPO);

                    log.info("emailPO , {}",emailPO);
                }
            }

        }


    }
}
