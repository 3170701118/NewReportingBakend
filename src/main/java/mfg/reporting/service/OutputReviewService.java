package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.dto.MakeUpPlanVO;
import mfg.reporting.mapper.OutputReviewMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OutputReviewService {

    private static final List<String> ALL_TYPE_LIST = Collections.unmodifiableList(Arrays.asList(
            "BASIC_TARGET", "ACT_MOVE", "UTIL", "UPTIME",
            "UTIL_TARGET", "UPTIME_TARGET", "EFF", "EFF_TARGET",
            "ACC_MOVE", "WIP", "AVG_TIME","MNG_FLAG"
    ));

    private static final Set<String> SKIP_TYPES = new HashSet<>(Arrays.asList(
            "UTIL_TARGET", "UPTIME_TARGET", "EFF_TARGET","MNG_FLAG"
    ));

    private static final Set<String> TARGET_FROM_RECORD_TYPES = new HashSet<>(Arrays.asList(
            "UTIL", "UPTIME", "EFF"
    ));

    private static final Set<String> MOVE_TYPES = new HashSet<>(Arrays.asList(
            "ACC_MOVE", "ACT_MOVE", "BASIC_TARGET"
    ));

    private static final Set<String> N1_WIP_TYPES = new HashSet<>(Arrays.asList(
            "WIP", "AVG_TIME"
    ));

    @Resource
    public OutputReviewMapper outputReviewMapper;

    public List<Map<String,Object>> getOutputSummaryByeqpg(String fab, String eqpg ,String month)
    {
        List<Map<String,Object>> list =  outputReviewMapper.getOutputSummaryByeqpg(fab,eqpg,month);

        if (list == null || list.isEmpty()) {
            log.warn("查询结果为空，fab: {}, eqpg: {}", fab, eqpg);
            return new ArrayList<>();
        }

        log.debug("开始行转列处理，原始数据行数: {}", list.size());

        List<String> shiftDateList = list.stream()
                .map(record -> String.valueOf(record.get("SHIFT_DATE")))
                .distinct()
                .collect(Collectors.toList());

        Map<String, Map<String, Object>> dateRecordMap = list.stream()
                .collect(Collectors.toMap(
                        record -> String.valueOf(record.get("SHIFT_DATE")),
                        record -> record,
                        (existing, replacement) -> existing
                ));

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> firstRecord = list.get(0);

        for (String type : ALL_TYPE_LIST) {
            if (SKIP_TYPES.contains(type)) {
                continue;
            }

            Map<String, Object> resultRow = new HashMap<>();
            resultRow.put("FAB", fab);
            resultRow.put("EQP_G", eqpg);
            resultRow.put("TYPE", type);
            resultRow.put("MNG_FLAG", firstRecord.get("MNG_FLAG"));

            setTargetValue(resultRow, type, firstRecord);
            setTypeDesc(resultRow, type);

            for (String shiftDate : shiftDateList) {
                Map<String, Object> record = dateRecordMap.get(shiftDate);
                Object value = record != null ? record.get(type) : null;
                resultRow.put(shiftDate, value);
            }

            result.add(resultRow);
        }

        log.debug("行转列完成，结果行数: {}, 日期数: {}", result.size(), shiftDateList.size());
        return result;
    }

    private void setTargetValue(Map<String, Object> resultRow, String type, Map<String, Object> firstRecord) {
        if (TARGET_FROM_RECORD_TYPES.contains(type)) {
            resultRow.put("TARGET", firstRecord.get(type + "_TARGET"));
        } else if ("ACC_MOVE".equals(type)) {
            resultRow.put("TARGET", 0);
        } else if ("ACT_MOVE".equals(type)) {
            resultRow.put("TARGET", firstRecord.get("BASIC_TARGET"));
        }
        else {
            resultRow.put("TYPE_TARGET", type);
        }
    }

    private void setTypeDesc(Map<String, Object> resultRow, String type) {
        String typeDesc;
        if (MOVE_TYPES.contains(type)) {
            typeDesc = "MOVE";
        } else if (N1_WIP_TYPES.contains(type)) {
            typeDesc = "N1 WIP";
        } else {
            typeDesc = type;
        }
        resultRow.put("TYPE_DESC", typeDesc);
    }

// ... existing code ...

    public List<Map<String,Object>> getOutputSummary(String fab,String month)
    {
        List<Map<String,Object>> list =  outputReviewMapper.getOutputSummary(fab,month);

        if (list == null || list.isEmpty()) {
            log.warn("查询结果为空，fab: {}", fab);
            return new ArrayList<>();
        }
        Object OUTPUT_PLAN = list.get(0).get("OUTPUT_PLAN");

        log.debug("开始行转列处理，原始数据行数: {}", list.size());

        List<String> typeList = Arrays.asList(
                "OUTPUT_PLAN", "OUTPUT_DAILY", "MAKEUP_PLAN",
                "ACT_OUTPUT", "DAILY_DIFF", "ACC_DIFF"
        );

        List<String> shiftDateList = list.stream()
                .map(record -> String.valueOf(record.get("SHIFT_DATE")))
                .distinct()
                .collect(Collectors.toList());

        Map<String, Map<String, Object>> dateRecordMap = list.stream()
                .collect(Collectors.toMap(
                        record -> String.valueOf(record.get("SHIFT_DATE")),
                        record -> record,
                        (existing, replacement) -> existing
                ));

        List<Map<String, Object>> result = new ArrayList<>();

        for (String type : typeList) {
            if ("OUTPUT_PLAN".equals(type)) {
                continue;
            }
            Map<String, Object> resultRow = new HashMap<>();
            resultRow.put("FAB", fab);
            resultRow.put("TYPE", type);
            resultRow.put("OUTPUT_PLAN", OUTPUT_PLAN);

            for (String shiftDate : shiftDateList) {
                Map<String, Object> record = dateRecordMap.get(shiftDate);
                Object value = record != null ? record.get(type) : null;
                resultRow.put(shiftDate, value);
            }

            result.add(resultRow);
        }

        log.debug("行转列完成，结果行数: {}, 日期数: {}", result.size(), shiftDateList.size());
        return result;
    }

    public List<Map<String,Object>> getMaintaineqpg(String module,String FLAG){
        if("Y".equals(FLAG)) {
            return outputReviewMapper.getMaintaineqpg(module, FLAG);
        }else{
            return outputReviewMapper.getMaintaineqpg(module, "");
        }
    }

    public void insertMaintaineqpg(Map<String,Object> data){

        if(data.get("MODULE") == null || data.get("EQP_G") == null ){
            log.warn("数据不完整，请检查");
            return;
        }
        //先test
        List<Map<String,Object>> list = outputReviewMapper.testMaintaineqpg(data.get("MODULE").toString(),data.get("EQP_G").toString());

        if(list.size() > 0){

            outputReviewMapper.insertMaintaineqpg(data);
        }

        return;

    }

    public void updateMaintainEqpg(Map<String,Object> data){
        if(data.get("MODULE") == null || data.get("EQP_G") == null ){
            log.warn("数据不完整，请检查");
            return;
        }
        outputReviewMapper.updateMaintainEqpg(data);

        return;
    }

    public void deleteMaintaineqpg(String EQP_G, String MODULE){
        outputReviewMapper.deleteMaintaineqpg(EQP_G,MODULE);
    }

    public List<Map<String,Object>> getkeyinSummary(String eqpg,String month){
        return outputReviewMapper.getkeyinSummary(eqpg,month);
    }

    public void updateCapData(Map<String,Object> map){
        if("Cap".equals(map.get("TECH"))){
            outputReviewMapper.updateCapQTY(map);
        }

        if("Request".equals(map.get("TECH"))){
            outputReviewMapper.updateReqQTY(map);
        }
    }

    public Map<String,Object> getComment(String eqpg,String curMonth){
        return outputReviewMapper.getComment(eqpg,curMonth);
    }

    public void updateComment(Map<String,Object> map){
        outputReviewMapper.updateComment(map);
    }

    public List<Map<String,Object>> getMakeUpPlanList(String month){
        return outputReviewMapper.getMakeUpPlanList(month);
    }

    public void insertMakeUpplan(MultipartFile file, String username) throws IOException {
        List<MakeUpPlanVO> list = EasyExcel.read(file.getInputStream())
                .head(MakeUpPlanVO.class)
                .sheet()
                .doReadSync();

        /* 验证数据 FAB 必须是 N1A1/ N1A3
           SHIFT_DATE 格式为 YYYYMMDD
           MAKEUP_PLAN 需要是数字
        *
        */
        for(MakeUpPlanVO item : list){
            String fab = item.getFAB().toString();
            if(!"N1A1".equals(fab.trim()) && !"N1A3".equals(fab.trim())){
                throw new ServerException("FAB数据异常，不为 N1A1/N1A3");
            }
            String shiftDate = item.getSHIFT_DATE().toString();
            if(!shiftDate.matches("\\d{8}")){
                throw new ServerException("SHIFT_DATE数据异常，格式为 YYYYMMDD");
            }

            String makeUpPlan = item.getMAKEUP_PLAN().toString();
            if(!makeUpPlan.matches("\\d+")){
                throw new ServerException("MAKEUP_PLAN数据异常，不为数字");
            }
        }

        outputReviewMapper.insertMakeUpplan(list, username);
    }

    public List<Map<String,Object>> getModuleeqpgRequstTarget(String EQP_G,String month){
        return outputReviewMapper.getModuleeqpgRequstTarget(EQP_G,month);
    }

    public void updateModuleeqpgMenuTarget(Map<String,Object> map){
        outputReviewMapper.updateModuleeqpgMenuTarget(map);
    }



}

