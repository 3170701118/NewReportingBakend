
package mfg.reporting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SendEmailUtil;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.SRMonitorMapper;
import mfg.reporting.pojo.CommonSendEmail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SRMonitorService {

    @Resource
    private final SRMonitorMapper srMonitorMapper;

    @Resource
    public SendEmailUtil emailUtil;

    private static final List<Map<String, Object>> CACHED_FIXED_LIST;

    private static final String EMPTY_STRING = "";

    private static final char SEPARATOR = '|';

    static {
        CACHED_FIXED_LIST = createFixedData();
    }

    /**
     * 获取SR监控汇总数据（行转列）
     *
     * @param eqpgList 设备组列表
     * @return 转换后的汇总数据
     * @throws ServiceException 当eqpgList为空时抛出
     */
    public List<Map<String,Object>> getSRMonitorSummary(List<String> eqpgList){

        if(eqpgList == null || eqpgList.isEmpty()){
            throw new ServiceException("eqpgList is empty");
        }

        List<Map<String,Object>> SQLResult = srMonitorMapper.getSRMonitorSummary(eqpgList);

        if(SQLResult == null || SQLResult.isEmpty()){
            log.warn("数据库查询结果为空，eqpgList: {}", eqpgList);
            return Collections.emptyList();
        }

        Map<String, Map<String, Object>> sqlDataMap = SQLResult.stream()
                .collect(Collectors.toMap(
                        item -> buildKey((String)item.get("FAB"), (String)item.get("PRODUCT_STATUS"), (String)item.get("EQP_G")),
                        item -> item,
                        (existing, replacement) -> existing
                ));

        List<Map<String,Object>> result = new ArrayList<>(CACHED_FIXED_LIST.size());

        for (Map<String, Object> fixedItem : CACHED_FIXED_LIST) {
            String fab = (String) fixedItem.get("FAB");
            String productStatus = (String) fixedItem.get("PRODUCT_STATUS");

            Map<String,Object> tempResult = new java.util.HashMap<>(4 + eqpgList.size() * 4);
            tempResult.put("FAB", fab);
            tempResult.put("PRODUCT_STATUS", productStatus);

            for (String eqpg : eqpgList) {
                String key = buildKey(fab, productStatus, eqpg);
                Map<String, Object> matchedData = sqlDataMap.get(key);

                if (matchedData != null) {
                    tempResult.put(eqpg + "_WIP", safeToString(matchedData.get("WIP")));
                    tempResult.put(eqpg + "_AVG_SR", safeToString(matchedData.get("AVG_SR")));
                    tempResult.put(eqpg + "_SR_TARGET", safeToString(matchedData.get("SR_TARGET")));
                    tempResult.put(eqpg + "_WIP_PERCENT", safeToString(matchedData.get("WIP_PERCENT")));
                    tempResult.put(eqpg + "_AVG_SAMP_RATIO", safeToString(matchedData.get("AVG_SAMP_RATIO")));
                } else {
                    tempResult.put(eqpg + "_WIP", EMPTY_STRING);
                    tempResult.put(eqpg + "_AVG_SR", EMPTY_STRING);
                    tempResult.put(eqpg + "_SR_TARGET", EMPTY_STRING);
                    tempResult.put(eqpg + "_WIP_PERCENT", EMPTY_STRING);
                    tempResult.put(eqpg + "_AVG_SAMP_RATIO", EMPTY_STRING);
                }
            }
            result.add(tempResult);
        }

        log.debug("SR监控汇总数据处理完成，返回{}条记录", result.size());
        return result;
    }

    private String buildKey(String fab, String productStatus, String eqpG) {
        int capacity = fab.length() + productStatus.length() + eqpG.length() + 2;
        return new StringBuilder(capacity)
                .append(fab)
                .append(SEPARATOR)
                .append(productStatus)
                .append(SEPARATOR)
                .append(eqpG)
                .toString();
    }

    private String safeToString(Object obj) {
        return obj == null ? EMPTY_STRING : obj.toString();
    }

    private static List<Map<String, Object>> createFixedData() {
        String[] fabs = {"N1A1", "N1A3", "N1" };
        String[] statuses = {"Mass production", "Risk", "Pilot", "SR Target", "ACT SR"};

        List<Map<String, Object>> fixedList = new ArrayList<>(fabs.length * statuses.length);

        for (String fab : fabs) {
            for (String status : statuses) {
                Map<String, Object> data = new java.util.HashMap<>(2);
                data.put("FAB", fab);
                data.put("PRODUCT_STATUS", status);
                fixedList.add(data);
            }
        }

        return fixedList;
    }


    public List<CommonSelectDTO> getSRMonitorEQPG(){
        return srMonitorMapper.getSRMonitorEQPG();
    }

    public List<Map<String,Object>> getSRMonitorChartByEQPG(String eqpg){
        return srMonitorMapper.getSRMonitorChartByEQPG(eqpg);
    }

    public List<Map<String,Object>> getSRMonitorDetailBySuffix(String eqpg){
        return srMonitorMapper.getSRMonitorDetailBySuffix(eqpg);
    }

    public List<Map<String,Object>> getSRMOnitorDetailByOPENO(String eqpg,String prodspecId, String fab){
        return srMonitorMapper.getSRMOnitorDetailByOPENO(eqpg,prodspecId,fab);
    }

    public List<Map<String,Object>> getSRMOnitorAlarmInfo(){
        return srMonitorMapper.getSRMOnitorAlarmInfo();
    }

    public List<Map<String,Object>> getPSRCheckRateInfo(){
        return srMonitorMapper.getPSRCheckRateInfo();
    }


    public void SRMonitorAlarmList(){
        log.info("查询SRMonitorAlarmList");

        List<Map<String,Object>> resultData = srMonitorMapper.SRMonitorAlarmList();
        if(resultData == null || resultData.isEmpty() ){
            log.info("数据库查询结果为空,不发送mail");
            return;
        }




        // 按照MAIL_LIST 进行分组
        Map<String, List<Map<String,Object>>> groupedData = resultData.stream()
                .collect(Collectors.groupingBy(data -> (String) data.get("MAIL_LIST")));

        CommonSendEmail data = new CommonSendEmail();

        List<CommonSelectDTO> headerList = new ArrayList<>();
        //FAB,PRODG1,PRODSPEC_ID,MNPD_ID,OPE_NO,PLATFORM_NICKNAME,SUFFIX_WIP,PRODUCT_STATUS,EQP_G,OPD_SR,SAMPLING_RATE
        headerList.add(new CommonSelectDTO("FAB","FAB"));
        headerList.add(new CommonSelectDTO("PRODG1","PRODG1"));
        headerList.add(new CommonSelectDTO("PRODSPEC_ID","PRODSPEC_ID"));
        headerList.add(new CommonSelectDTO("MNPD_ID","MNPD_ID"));
        headerList.add(new CommonSelectDTO("OPE_NO","OPE_NO"));
        headerList.add(new CommonSelectDTO("PLATFORM_NICKNAME","PLATFORM_NICKNAME"));
        headerList.add(new CommonSelectDTO("SUFFIX_WIP","SUFFIX_WIP"));
        headerList.add(new CommonSelectDTO("PRODUCT_STATUS","PRODUCT_STATUS"));
        headerList.add(new CommonSelectDTO("EQP_G","EQP_G"));
        headerList.add(new CommonSelectDTO("OPD_SR","OPD_SR"));
        headerList.add(new CommonSelectDTO("SAMPLING_RATE","SAMPLING_RATE"));
        data.setHeaderList(headerList);
        //

        // groupedData key 值 为mail对象 / Value 值 为发送内容

        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedData.entrySet()) {
            String key = entry.getKey();
            List<Map<String, Object>> value = entry.getValue();

            data.setSubDesc("<h3 style='font-size:20px;font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;'>量测群组 <span style='color:red;font-weight:bold;'>SR超target</span>站点如下，请帮忙尽快安排提单调降，谢谢！</h3>");
            data.setList(value);
            data.setSubject("SR超target Alarm");
            data.setReceiver(key);
            //data.setReceiver("gszhang@nexchip.com.cn");
            data.setReceiverCC("PT11-04@nexchip.com.cn,xueqitian@nexchip.com.cn,gszhang@nexchip.com.cn");


            emailUtil.sendAlarmEmailAddCC( data);

        }



    }

    public void SRMonitorAlarmDeptNullsList(){
        List<Map<String,Object>> result =  srMonitorMapper.SRMonitorAlarmDeptNullsList();

        log.debug("SR监控超target数据处理开始");
        if(result == null || result.isEmpty() ){
            log.debug("SR监控超target数据处理完成，无数据");
            return ;
        }


        CommonSendEmail data = new CommonSendEmail();

        List<CommonSelectDTO> headerList = new ArrayList<>();
        //FAB,PRODG1,PRODSPEC_ID,MNPD_ID,OPE_NO,PLATFORM_NICKNAME,SUFFIX_WIP,PRODUCT_STATUS,EQP_G,OPD_SR,SAMPLING_RATE
        headerList.add(new CommonSelectDTO("FAB","FAB"));
        headerList.add(new CommonSelectDTO("PRODG1","PRODG1"));
        headerList.add(new CommonSelectDTO("PRODSPEC_ID","PRODSPEC_ID"));
        headerList.add(new CommonSelectDTO("MNPD_ID","MNPD_ID"));
        headerList.add(new CommonSelectDTO("OPE_NO","OPE_NO"));
        headerList.add(new CommonSelectDTO("PLATFORM_NICKNAME","PLATFORM_NICKNAME"));
        headerList.add(new CommonSelectDTO("SUFFIX_WIP","SUFFIX_WIP"));
        headerList.add(new CommonSelectDTO("PRODUCT_STATUS","PRODUCT_STATUS"));
        headerList.add(new CommonSelectDTO("EQP_G","EQP_G"));
        headerList.add(new CommonSelectDTO("OPD_SR","OPD_SR"));
        headerList.add(new CommonSelectDTO("SAMPLING_RATE","SAMPLING_RATE"));
        data.setHeaderList(headerList);

        data.setSubDesc("<h3 style='font-size:20px;font-family: '思源宋体', 'SimSun', 'Arial', sans-serif;'>量测群组 <span style='color:red;font-weight:bold;'>SR超target</span>站点如下，请帮忙尽快安排提单调降，谢谢！</h3>");
        data.setList(result);
        data.setSubject("SR超target Alarm,无对应部门，请优先补充逻辑");
        data.setReceiver("PT11-04@nexchip.com.cn");
        data.setReceiverCC("xueqitian@nexchip.com.cn,gszhang@nexchip.com.cn");

        emailUtil.sendAlarmEmailAddCC( data);


    }


}