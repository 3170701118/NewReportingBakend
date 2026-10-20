package mfg.reporting.service;


import com.alibaba.excel.EasyExcel;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.dto.MFGCriticalA3MoveSelfEQPGTargetVO;
import mfg.reporting.mapper.MFGCriticalMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MFGCriticalService {


    private static final List<String> MODULE_LIST = Arrays.asList(
            "PHOTO", "ETCH", "TF", "DIFF", "WET", "CMP", "METRO","TOTAL"
    );



    @Resource
    public MFGCriticalMapper mapper;

    public List<CommonSelectDTO> getTechList(String fab){
        return mapper.getTechList(fab);
    }

    public List<CommonSelectDTO>getStdTechList(){
        return mapper.getStdTechList();
    }

    public List<CommonSelectDTO> getRealModuleList(String fab){
        return mapper.getRealModuleList(fab);
    }

    public List<CommonSelectDTO> getEqpgList(String fab,String module){
        return mapper.getEqpgList(fab,module);
    }

    public List<Map<String,Object>> getLpHealthChart(String fab){
        return mapper.getLpHealthChart(fab);
    }

    public List<Map<String,Object>> getLpHealthChartSummaryByEQPG(String fab , String module ){
        //return mapper.getLpHealthChartSummaryByEQPG(fab,module);
        List<Map<String,Object>> list = mapper.getLpHealthChartSummaryByEQPG(fab,module);

        List<Map<String,Object>> result = new ArrayList<>();

        //shiftDateList from distinct list.SHIFT_DATE
        List<String> shiftDateList = new ArrayList<>();
        for(Map<String,Object> item : list){
            String shiftDate = item.get("SHIFT_DATE").toString();
            if(!shiftDateList.contains(shiftDate)){
                shiftDateList.add(shiftDate);
            }
        }
        //eqpglist from distinct list.EQP_G
        List<String> eqpgList = new ArrayList<>();
        for(Map<String,Object> item : list){
            String eqpG = item.get("EQP_G").toString();
            if(!eqpgList.contains(eqpG)){
                eqpgList.add(eqpG);
            }
        }

        for(String eqpg : eqpgList){
            Map<String,Object> map = new HashMap<>();
            map.put("EQP_G",eqpg);

            if("TOTAL".equals(eqpg)){
                map.put("MODULE",eqpg);
            }else{
                map.put("MODULE",module);
            }
            for(String shiftDate : shiftDateList){
                Map<String,Object> map2 = list
                        .stream()
                        .filter(item -> item.get("SHIFT_DATE").equals(shiftDate) && item.get("EQP_G").equals(eqpg))
                        .findFirst()
                        .get();
               map.put(shiftDate,map2.get("SEFA_COUNT"));
            }
            result.add(map);
        }

        return result;


    }

    public List<Map<String,Object>> getLpHealthChartDetailDaily(String shiftDate ,String fab , String module ,String eqpg){
        return mapper.getLpHealthChartDetailDaily(shiftDate,fab,module,eqpg);
    }

    public List<CommonSelectDTO> getEqpList(String fab , String module ,String eqpg){
        return mapper.getEqpList(fab,module,eqpg);
    }

    public List<Map<String,Object>> getDYBChart(String fab ){
        return mapper.getDYBChart(fab);
    }

    public List<Map<String,Object>> getDybChartSummaryByeqpg(String fab , String module ){
        //return mapper.getLpHealthChartSummaryByEQPG(fab,module);
        List<Map<String,Object>> list = mapper.getDybChartSummaryByeqpg(fab,module);

        List<Map<String,Object>> result = new ArrayList<>();

        //shiftDateList from distinct list.SHIFT_DATE
        List<String> shiftDateList = new ArrayList<>();
        List<String> eqpgList = new ArrayList<>();
        for(Map<String,Object> item : list){
            String shiftDate = item.get("SHIFT_DATE").toString();
            if(!shiftDateList.contains(shiftDate)){
                shiftDateList.add(shiftDate);
            }
            String eqpg = item.get("EQP_G").toString();
            if(!eqpgList.contains(eqpg)){
                eqpgList.add(eqpg);
            }
        }

        for(String eqpg : eqpgList){
            Map<String,Object> map = new HashMap<>();
            map.put("EQP_G",eqpg);

            if("TOTAL".equals(eqpg)){
                map.put("MODULE",eqpg);
            }else{
                map.put("MODULE",module);
            }
            for(String shiftDate : shiftDateList){
                Map<String,Object> map2 = list
                        .stream()
                        .filter(item -> item.get("SHIFT_DATE").equals(shiftDate) && item.get("EQP_G").equals(eqpg))
                        .findFirst()
                        .get();
                map.put(shiftDate,map2.get("DYB_RATIO"));
                map.put(shiftDate+"_DYB_MOVE",map2.get("DYB_MOVE"));
                map.put(shiftDate+"_TTL_MOVE",map2.get("TTL_MOVE"));
            }
            result.add(map);
        }

        return result;

    }

    public List<Map<String,Object>> getDYBChartDetailDaily(String shiftDate ,String fab , String module ,String eqpg){
        return mapper.getDYBChartDetailDaily(shiftDate,fab,module,eqpg);
    }

    public List<Map<String,Object>> getQSRatioChart(String fab){
        return mapper.getQSRatioChart(fab);
    }

    public List<Map<String,Object>> getForceRatioChart(String fab,String date,String dimension,String type){
        if ("time".equals(dimension)){
            return mapper.getForceRatioTimeChart(fab,date,type);
        }

        if("batch".equals(dimension)){
            return mapper.getForceRatioBatchChart(fab,date,type);
        }

        throw new ServiceException("维度无效");
    }

    public List<Map<String,Object>> getForceRatioDetail(String fab,String date,String dimension,String type,String eqpg){
        return mapper.getForceRatioDetail(fab,dimension,date,type,eqpg);
    }

    public List<Map<String,Object>> getMeasurementNgRatio(String fab,String date){
        return mapper.getMeasurementNgRatio(fab,date);
    }

    public List<CommonSelectDTO> getMeasurementForceEqpGListByFab(String fab){
        return mapper.getMeasurementForceEqpGListByFab(fab);
    }

    public List<CommonSelectDTO> getMeasurementNgEqpGListByFab(String fab){
        return mapper.getMeasurementNgEqpGListByFab(fab);
    }

    public List<Map<String,Object>> getLpFilterTableList(String fab,String module){
        return mapper.getLpFilterTableList(fab,module);
    }

    public List<Map<String,Object>> getDybTargetTableList(String module){
        return mapper.getDybTargetTableList(module);
    }


    public void addDYBTargetInfo(Map<String,Object> data){
        /**
         *  先判断数据库有无数据，无数据新增，有数据返回错误
         */

        List<Map<String,Object>> resultData = mapper.validDYBTargetInfo(data);

        if(resultData.size()>0){
            throw new ServiceException("已存在Target，请检查后上传");
        }

        /** 插入数据库 */

        mapper.insertDYBTargetInfo(data);
    }

    public void updateDYBTargetInfo(Map<String,Object> data){
        mapper.updateDYBTargetInfo(data);
    }

    public void addLpFilterInfo(Map<String,Object> data){
        /**
         *  先判断数据库有无数据，无数据新增，有数据返回错误
         */

        List<Map<String,Object>> resultData = mapper.validLpFilterInfo(data);

        if(resultData.size()>0){
            throw new ServiceException("已存在相关LP数据，请检查有无重复后再上传");
        }

        /** 插入数据库 */

        mapper.insertLpFilterInfo(data);
    }

    public void deleteLpFilterInfo(Map<String,Object> data){
        mapper.deleteLpFilterInfo(data);
    }


    public List<Map<String,Object>> getMeasurementMoveRatioInfo(String fab){
        return mapper.getMeasurementMoveRatioInfo(fab);
    }

    public List<Map<String,Object>> getMeasurementSRInfo(Map<String,Object> data){
        return mapper.getMeasurementSRInfo(data);
    }

    public List<Map<String,Object>> getMeasurementSRTableByTechList(Map<String,Object> data){
        return mapper.getMeasurementSRTableByTechList(data);
    }

    public List<Map<String,Object>> getMeasurementSRTableByProdList(Map<String,Object> data){
        return mapper.getMeasurementSRTableByProdList(data);
    }

    public List<CommonSelectDTO> getMeasurementSREQPGInfoByFab(String fab){
        return mapper.getMeasurementSREQPGInfoByFab(fab);
    }
    public List<CommonSelectDTO> getMeasurementSRTechInfoByFab(String fab){
        return mapper.getMeasurementSRTechInfoByFab(fab);
    }
    public List<CommonSelectDTO> getMeasurementSRProdgInfoByFab(String fab ,String eqpg,String tech){
        return mapper.getMeasurementSRProdgInfoByFab(fab,eqpg,tech);
    }

    public List<Map<String,Object>> getPilotRunFabSummaryChart(String module){
        return mapper.getPilotRunFabSummaryChart(module);
    }

    public List<Map<String,Object>> getPilotRunSummaryChart(String fab,String module,int type){
        return mapper.getPilotRunSummaryChart(fab,module, type);
    }

    public List<Map<String,Object>> getpilotRunSummaryTableList(String fab,String module,int type,String shiftDate){
        return mapper.getpilotRunSummaryTableList(fab,module,type,shiftDate);
    }

    public List<Map<String,Object>> getpilotRunDetailsTableList(Map<String,Object> data){
        return mapper.getpilotRunDetailsTableList(data);
    }

    public void updateLpHealthDetails(Map<String,Object> data){
        mapper.updateLpHealthDetails(data);
    }

    public List<Map<String,Object>> getReworkSummaryChart(String fab,String module,String eqpg){
        return mapper.getReworkSummaryChart(fab,module,eqpg);
    }

    public List<Map<String,Object>> getReworkMoveDetails(String fab,String module,String eqpg,String shiftDate){
        return mapper.getReworkMoveDetails(fab,module,eqpg,shiftDate);
    }

    public List<Map<String,Object>> getReprocessMonitorSummaryChart(String fab,String module,String eqpg){
        return mapper.getReprocessMonitorSummaryChart(fab,module,eqpg);
    }

    public List<Map<String,Object>> getReprocessMonitorDetails(String fab,String module,String eqpg,String shiftDate){
        return mapper.getReprocessMonitorDetails(fab,module,eqpg,shiftDate);
    }

    public List<Map<String,Object>> getReworkTargetList(String module){
        return mapper.getReworkTargetList(module);
    }

    public void updateReworkTargetInfo(Map<String,Object> data){
        mapper.updateReworkTargetInfo(data);
    }

    public void addReworkTargetInfo(Map<String,Object> data){
        /**
         *  先判断数据库有无数据，无数据新增，有数据返回错误
         */

        List<Map<String,Object>> resultData = mapper.validReworkTargetInfo(data);

        if(resultData.size()>0){
            throw new ServiceException("已存在Target，请检查后上传");
        }

        /** 插入数据库 */

        mapper.insertReworkTargetInfo(data);
    }

    public List<Map<String,Object>> getMeasurementNGDetails(String fab,String date,String eqpg,String tech,String prodg){
        return mapper.getMeasurementNGDetails(fab,date,eqpg,tech,prodg);
    }


    public List<Map<String,Object>> getMeasurementWATRatioSummary(String fab){
        return mapper.getMeasurementWATRatioSummary(fab);
    }

    public List<Map<String,Object>> getMeasurementWATDetail(String fab,String shiftDate){
        return mapper.getMeasurementWATDetail(fab,shiftDate);
    }

    public List<Map<String,Object>> getLotPriorityRatioChart(String fab,String location,String shiftDate,String hour,String tech){
        return mapper.getLotPriorityRatioChart(fab,location,shiftDate,hour,tech);
    }

    public List<Map<String,Object>> getLotPriorityRatioTrendChartData(String fab,String location,String startDate,String endDate,String tech,String priority){
        return mapper.getLotPriorityRatioTrendChartData(fab,location,startDate,endDate,tech,priority);
    }

    public List<CommonSelectDTO> getCrackEqpIdList(){
        return mapper.getCrackEqpIdList();
    }

    public List<Map<String,Object>> getCrackSummaryChart(String eqpId,String dimension){
        if("lot".equals(dimension)){
            return mapper.getCrackInfoByLot(eqpId);
        }

        if("wafer".equals( dimension)){
            return mapper.getCrackInfoByWafer(eqpId);
        }

        throw new ServiceException("请选择正确的维度");
    }

    public List<Map<String,Object>> getMeasurementSRTargetInfo(){
        return mapper.getMeasurementSRTargetInfo();
    }

    public void updateMeasurementSRTargetInfo(Map<String,Object> data){
        mapper.updateMeasurementSRTargetInfo(data);
    }

    public void addMeasurementSRTargetInfo(Map<String,Object> data){
        /**
         *  先判断数据库有无数据，无数据新增，有数据返回错误
         */

        List<Map<String,Object>> resultData = mapper.validMeasurementSRTargetInfo(data);

        if(resultData.size()>0){
            throw new ServiceException("已存在Target，请检查后上传");
        }

        /** 插入数据库 */

        mapper.insertMeasurementSRTargetInfo(data);
    }


    public List<Map<String,Object>> getA3SelfMoveRatioInfo() {

        // 三个字段，SHIFT_DATE,MODULE,RATIO ; MODULE 有 PHOTO,ETCH,DIFF,TF,CMP,WET,METRO ;
        // 根据  相同SHIFT_DATE, 不同MODULE 行转列 RATIO，为 MODULE_RATIO
        List<Map<String, Object>> resultData = mapper.getA3SelfMoveRatioInfo();

        if (resultData == null || resultData.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<String, Object>> shiftDateModuleMap = new HashMap<>();
        for (Map<String, Object> item : resultData) {
            String shiftDate = (String) item.get("SHIFT_DATE");
            String module = (String) item.get("MODULE");
            if (shiftDate != null && module != null) {
                shiftDateModuleMap.put(shiftDate + "_" + module, item);
            }
        }

        Set<String> shiftDates = resultData.stream()
                .map(item -> (String) item.get("SHIFT_DATE"))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toSet());

        List<Map<String, Object>> result = new ArrayList<>();
        for (String shiftDate : shiftDates) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("SHIFT_DATE", shiftDate);

            for (String module : MODULE_LIST) {
                String key = shiftDate + "_" + module;
                Map<String, Object> matchedItem = shiftDateModuleMap.get(key);
                Object ratio = matchedItem != null ? matchedItem.get("RATIO") : null;
                temp.put(module + "_RATIO", ratio);
            }

            result.add(temp);
        }

        return result;
}


    public List<Map<String,Object>> getA3SelfMoveRatioInfoByModule(){
        List<Map<String,Object>> data = mapper.getA3SelfMoveRatioInfo();

        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<String, Object>> moduleDateMap = new HashMap<>();
        for (Map<String, Object> item : data) {
            String module = (String) item.get("MODULE");
            String shiftDate = (String) item.get("SHIFT_DATE");
            if (module != null && shiftDate != null) {
                moduleDateMap.put(module + "_" + shiftDate, item);
            }
        }

        Set<String> shiftDates = data.stream()
                .map(item -> (String) item.get("SHIFT_DATE"))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toSet());

        List<String> sortedShiftDates = new ArrayList<>(shiftDates);
        Collections.sort(sortedShiftDates);

        List<Map<String,Object>> result = new ArrayList<>();
        for (String module : MODULE_LIST) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("MODULE", module);

            Object target = null;
            for (String date : sortedShiftDates) {
                String key = module + "_" + date;
                Map<String, Object> matchedItem = moduleDateMap.get(key);

                if (matchedItem != null) {
                    temp.put(date + "_RATIO", matchedItem.get("RATIO"));
                    target = matchedItem.get("TARGET");
                } else {
                    temp.put(date + "_RATIO", null);
                }
            }

            temp.put("TARGET", target);
            result.add(temp);
        }

        return result;
    }

    public List<Map<String,Object>> getA3SelfMoveRatioInfoByEQPG(String module){

        List<Map<String,Object>> data = mapper.getA3SelfMoveRatioInfoByEQPG(module);

        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<String, Object>> eqpgDateMap = new HashMap<>();
        Set<String> eqpgSet = new HashSet<>();
        Set<String> dateSet = new TreeSet<>();

        for (Map<String, Object> item : data) {
            String eqpg = (String) item.get("EQP_G");
            String shiftDate = (String) item.get("SHIFT_DATE");
            if (eqpg != null && shiftDate != null){
                eqpgDateMap.put(eqpg + "_" + shiftDate, item);
                eqpgSet.add(eqpg);
                dateSet.add(shiftDate);
            }
        }
        List<String> sortedShiftDates = new ArrayList<>(dateSet);
        List<Map<String,Object>> result = new ArrayList<>(eqpgSet.size());

        for (String eqpg : eqpgSet) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("EQP_G", eqpg);
            Object target = null;

            for (String date : sortedShiftDates) {
                StringBuilder keyBuilder = new StringBuilder(eqpg.length() + 1 + date.length());
                String key = keyBuilder.append(eqpg).append("_").append(date).toString();

                Map<String, Object> matchedItem = eqpgDateMap.get(key);
                if (matchedItem != null) {
                    temp.put(date + "_RATIO", matchedItem.get("RATIO"));
                    target = matchedItem.get("TARGET");
                } else {
                    temp.put(date + "_RATIO", null);
                }
            }

            temp.put("TARGET", target);
            result.add(temp);
        }


        result.sort((r1, r2) -> {
            String eqpG1 = (String) r1.get("EQP_G");
            String eqpG2 = (String) r2.get("EQP_G");

            boolean isModule1 = module.equalsIgnoreCase(String.valueOf(eqpG1));
            boolean isModule2 = module.equalsIgnoreCase(String.valueOf(eqpG2));

            if (isModule1 && !isModule2) {
                return 1;
            } else if (!isModule1 && isModule2) {
                return -1;
            } else {
                return 0;
            }
        });


        return result;
    }

    public List<Map<String,Object>> getA3SelfMoveRatioInfoByEQP(String eqpg){

        List<Map<String,Object>> data = mapper.getSelfMoveRatioInfoByeqpId(eqpg);

        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<String, Object>> eqpDateMap = new HashMap<>();
        Set<String> eqpSet = new HashSet<>();
        Set<String> dateSet = new TreeSet<>();

        for (Map<String, Object> item : data) {
            String eqpId = (String) item.get("EQP_ID");
            String shiftDate = (String) item.get("SHIFT_DATE");
            if (eqpId != null && shiftDate != null){
                eqpDateMap.put(eqpId + "_" + shiftDate, item);
                eqpSet.add(eqpId);
                dateSet.add(shiftDate);
            }
        }

        List<String> sortedShiftDates = new ArrayList<>(dateSet);
        List<Map<String,Object>> result = new ArrayList<>(eqpSet.size());

        for (String eqp : eqpSet) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("EQP_ID", eqp);
            Object target = null;

            for (String date : sortedShiftDates) {
                StringBuilder keyBuilder = new StringBuilder(eqp.length() + 1 + date.length());
                String key = keyBuilder.append(eqp).append("_").append(date).toString();

                Map<String, Object> matchedItem = eqpDateMap.get(key);
                if (matchedItem != null) {
                    temp.put(date + "_RATIO", matchedItem.get("RATIO"));
                    target = matchedItem.get("TARGET");
                } else {
                    temp.put(date + "_RATIO", null);
                }
            }

            temp.put("TARGET", target);
            result.add(temp);
        }

        // 将结果排序， result.EQP_G = module的数据排至最后


        return result;
    }



    public List<Map<String,Object>> getA3SelfMoveAlarmRatioInfo(){
        return mapper.getA3SelfMoveAlarmRatioInfo();
    }

    public List<Map<String,Object>> getA3SelfMoveAlarmRatioInfoByEqpG(){
        return mapper.getA3SelfMoveAlarmRatioInfoByEqpG();
    }

    public void insertA3SelfMoveAction(Map<String,Object> data){
        mapper.insertA3SelfMoveAction(data);
    }

    public List<Map<String,Object>> getA3SelfMoveAlarmDetails(String Status){
        return mapper.getA3SelfMoveAlarmDetails(Status);
    }

    public void updateA3SelfMoveActionStatus(Map<String,Object> data){
         mapper.updateA3SelfMoveActionStatus(data);
    }

        public List<Map<String,Object>> getA3SelfMoveRatioBackupRatio(){
           return mapper.getA3SelfMoveRatioBackupRatio();
        }

        public Map<String,Object> getA3SelfMoveRatioSetting(){
            List<Map<String,Object>> tableData = mapper.getA3SelfMoveRatioTarget();
            Map<String,Object> formDataSetting = mapper.getA3SelfMoveRatioAlarmSetting();

            return new HashMap<String,Object>(){{
                put("tableData",tableData);
                put("formDataSetting",formDataSetting);
            }};

        }


        public void updateA3SelfMoveRatioAlarmSetting(Map<String,Object> data){
            mapper.updateA3SelfMoveRatioAlarmSetting(data);
        }

        public void updateA3SelfMoveRatioTarget(Map<String,Object> data){
            mapper.updateA3SelfMoveRatioTarget(data);
        }

        public void insertA3SelfMoveRatioTarget(Map<String,Object> data){
            mapper.insertA3SelfMoveRatioTarget(data);
        }


        @Transactional(rollbackFor = Exception.class)
        public void uploadExcel(MultipartFile file, String username) throws IOException {
            List<MFGCriticalA3MoveSelfEQPGTargetVO> list = EasyExcel.read(file.getInputStream())
                    .head(MFGCriticalA3MoveSelfEQPGTargetVO.class)
                    .sheet()
                    .doReadSync();

            for (MFGCriticalA3MoveSelfEQPGTargetVO item : list) {
                // 验证 target 是否为数字，可以加小数点

                // 判断是否为空 & 是否 >0 & <100

                if(item.getTarget().trim().isEmpty()){
                    throw new ServiceException("Target数据异常，不能为空");
                }

                try {
                    Double.parseDouble(item.getTarget());

                } catch (NumberFormatException e) {
                    throw new ServiceException("Target数据异常，不为数字");
                }

                if(Double.parseDouble(item.getTarget()) <= 0 || Double.parseDouble(item.getTarget()) > 100){
                    throw new ServiceException("Target数据异常，不为0~100的数字");
                }

            }

            //如果解析出来数据Size>0 ,则执行先删除,后插入

            if(!list.isEmpty()){
                mapper.deleteA3SelfMoveRatioTarget();

                mapper.insertA3SelfMoveRatioTargetList(list,username);

            }


        }

    }
