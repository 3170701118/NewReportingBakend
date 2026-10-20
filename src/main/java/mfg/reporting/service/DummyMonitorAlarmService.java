package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SendEmailUtil;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.DummyMonitorAlarmMapper;
import mfg.reporting.pojo.CommonSendEmail;
import mfg.reporting.pojo.DummyUploadVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DummyMonitorAlarmService {

    @Resource
    public SendEmailUtil emailUtil;

    @Resource
    public DummyMonitorAlarmMapper dummyMonitorAlarmMapper;

    public void insertDummyMonitorAlarm(Map<String,Object>data) {

        //确认输入数据表格不存在
        List<Map<String,Object>> list = dummyMonitorAlarmMapper.getDummyMonitorAlarm(data.get("fab").toString(), "TOTAL",data.get("purpose").toString() );

        if(list.size() > 0){
            throw new ServiceException("输入数据表格已存在");
        }

        dummyMonitorAlarmMapper.insertDummyMonitorAlarm(data);
    }

    public void updateDummyMonitorAlarmActiveFlag(String fab, String purpose, float stdf, String activeFlag,String keyComment, String updateUser) {
        dummyMonitorAlarmMapper.updateDummyMonitorAlarmActiveFlag(fab, purpose, stdf, activeFlag,keyComment, updateUser);
    }

    public void deleteDummyMonitorAlarm(String fab, String purpose) {
        dummyMonitorAlarmMapper.deleteDummyMonitorAlarm(fab, purpose);
    }

    public List<Map<String,Object>> getDummyMonitorAlarm(String fab, String module, String purpose) {
        return dummyMonitorAlarmMapper.getDummyMonitorAlarm(fab, module, purpose);
    }

    public void UploadExcel(MultipartFile file, String username) throws IOException {
        //解析EXCEL
        List<DummyUploadVO> list = EasyExcel.read(file.getInputStream())
                .head(DummyUploadVO.class)
                .sheet()
                .doReadSync();

        //先验证list是否符合数据要求；

        //1.fab 是否为‘N1A1’ 'N1A3'
        for (DummyUploadVO item : list) {
            if(!"N1A1".equals(item.getFab().trim()) && !"N1A3".equals(item.getFab().trim())){
                throw new ServerException("FAB数据异常，不为 N1A1/N1A3");
            }

            //验证 item.stdf 是否为 >0 <=1 的数字
            if(item.getStdf() <= 0 || item.getStdf() > 1){
                throw new ServerException("倍率值异常，不为 0~1的数字");
            }

            item.setUpdateUser(username);
        }

        //验证数据库是否存在数据
        List<Map<String,Object>> listTemp = dummyMonitorAlarmMapper.getDummyMonitorAlarmList(list);

        //批量插入数据库
        if(listTemp.size() > 0){
            throw new ServerException("数据库中已存在相同数据");
        }

        dummyMonitorAlarmMapper.insertDummyMonitorAlarmList(list);
    }

    public void sendMail(){
        //邮件发送数据
        List<Map<String,Object>> list = dummyMonitorAlarmMapper.getDummyMailAlarmList();
        if(list.size()==0){
            log.info("没有数据，邮件不发送");
            return;
        }

        //list.size()>0 就发送，但是发送给各课；
        //hashCode 六个生产课循环发mail
        List<String> moduleList = Arrays.asList("PHOTO", "ETCH", "DIFF", "TF", "WET", "CMP","METRO");
        List<String> fabList = Arrays.asList("N1A1", "N1A3");

        moduleList.forEach(module -> {
            //过滤出对应的生产课
            fabList.forEach(fab -> {
                List<Map<String, Object>> result = new ArrayList<>();

                if("N1A1".equals(fab)){
                    result = list.stream()
                            .filter(item -> module.equals(item.get("MODULE")) && ("N1A1".equals(item.get("FAB")) ))
                            .collect(Collectors.toList());
                }

                if("N1A3".equals(fab)){
                    result = list.stream()
                            .filter(item -> module.equals(item.get("MODULE")) && ("N1A3".equals(item.get("FAB"))))
                            .collect(Collectors.toList());
                }


                if (result.size() > 0) {
                    if (!"METRO".equals(module) &&!"PHOTO".equals(module) && !"ETCH".equals(module) && !"DIFF".equals(module) && !"TF".equals(module) && !"WET".equals(module) && !"CMP".equals(module)) {
                        log.error("无对应的生产课，暂不发送此条Alarm");
                        return;
                    }

                    if (!"N1A1".equals(fab)  && !"N1A3".equals(fab) ) {
                        log.error("无对应的fab，暂不发送此条Alarm");
                        return;
                    }

                    //触发邮件机制；
                    CommonSendEmail sendEmailPO = new CommonSendEmail();
                    sendEmailPO.setSubDesc("网页链接：<a href='http://n1mfgs04:8090/NewReporting/diff/dummyMonitorAlarm?page=common'>Dummy低水位监控</a>");

                    //Purpose	Type	标准存量	MFG领用存量
                    sendEmailPO.setSubject("Dummy低水位监控邮件提醒");
                    List<CommonSelectDTO> tableHeaderList = new ArrayList<>();
                    tableHeaderList.add(new CommonSelectDTO("PURPOSE", "Purpose"));
                    tableHeaderList.add(new CommonSelectDTO("STD_STOCK", "标准存量"));
                    tableHeaderList.add(new CommonSelectDTO("MFG_QTY", "MFG领用存量"));
                    tableHeaderList.add(new CommonSelectDTO("KEY_COMMENT", "F_COMMENT"));
                    sendEmailPO.setHeaderList(tableHeaderList);
                    sendEmailPO.setList(result);
                    String receiver = getMailReciverByModule(module,fab);
                    //String receiver = "zhulong@nexchip.com.cn";
                    sendEmailPO.setReceiver(receiver);
                    //String ReceiverCC = "gszhang@nexchip.com.cn";
                    String ReceiverCC ="";
                    sendEmailPO.setReceiverCC(ReceiverCC);

                    log.info("已发送Mail,记录当前Mail的条件：Module: {}, Fab: {}, Receiver: {}, ReceiverCC: {}", module, fab, receiver, ReceiverCC);
                    log.info("result：{}",result);

                    emailUtil.sendAlarmEmailAddCC(sendEmailPO);
                }
            });
        });


    }



    private String getMailReciverByModule(String module, String fab) {
        String receiver = "";

       if ("METRO".equals(module)) {
            receiver = receiver.concat("PT11-04@nexchip.com.cn");
        }

        if ("N1A1".equals(fab) || "N1A2".equals(fab)) {
            if ("PHOTO".equals(module)) {
                receiver = receiver.concat("PB11-032@nexchip.com.cn");
            }
            if ("ETCH".equals(module)) {
                receiver = receiver.concat("PB11-042@nexchip.com.cn");
            }
            if ("DIFF".equals(module)) {
                receiver = receiver.concat("PB11-052@nexchip.com.cn");
            }

            if ("TF".equals(module)) {
                receiver = receiver.concat("PB11-062@nexchip.com.cn");
            }

            if ("WET".equals(module) || "CMP".equals(module)) {
                receiver = receiver.concat("PB11-082@nexchip.com.cn");
            }
        }

        if ("N1A3".equals(fab) || "N1A4".equals(fab)) {
            if ("PHOTO".equals(module)) {
                receiver = receiver.concat("PH11-032@nexchip.com.cn");
            }
            if ("ETCH".equals(module)) {
                receiver = receiver.concat("PH11-042@nexchip.com.cn");
            }
            if ("DIFF".equals(module)) {
                receiver = receiver.concat("PH11-052@nexchip.com.cn");
            }

            if ("TF".equals(module)) {
                receiver = receiver.concat("PH11-062@nexchip.com.cn");
            }

            if ("WET".equals(module) || "CMP".equals(module)) {
                receiver = receiver.concat("PH11-08@nexchip.com.cn");
            }
        }

        if("".equals(receiver)){
            log.error("无对应的FAB -- {},module -- {}，暂不发送此条Alarm",fab,module);
            throw new ServiceException("无对应的FAB，暂不发送此条Alarm");
        }
        return receiver;
    }
}
