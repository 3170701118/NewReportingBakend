package mfg.reporting.service;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.mapper.EqpRecoverMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EqpRecoverService {

    @Resource
    private EqpRecoverMapper mapper;

    public List<Map<String,Object>> getFcstRecoverChart(String fab, String module){
        return mapper.getFcstRecoverChart(fab,module);
    }

    public List<Map<String,Object>> getRecoverRatioByModule(){
        return mapper.getRecoverRatioByModule();
    }

    public List<Map<String,Object>> getRecoverRatioByMFGModule(){
        return mapper.getRecoverRatioByMFGModule();
    }

    public List<Map<String,Object>> getRecoverRatioByEQPG(String module){
        return mapper.getRecoverRatioByEQPG(module);
    }

    public List<Map<String,Object>> getRecoverTableDetails(String module){
        return mapper.getRecoverTableDetails(module);
    }

    public void updateACTRecoverTime(String eqpId, String actReturnTime,String updateUser,String fcomment){
        mapper.updateACTRecoverTime(eqpId,actReturnTime,updateUser,fcomment);
    }

    public List<Map<String,Object>> getProcessRate(){
        return mapper.getProcessRate();
    }

    public void updateProcessRate(String key1, String key2, String key3){
    	mapper.updateProcessRate(key1,key2,key3);
    }

    public List<Map<String,Object>> getFcstRecoverChartByPri(String module){
        return mapper.getFcstRecoverChartByPri(module);
    }

    public Map<String,Object> getExportData(){
        List<Map<String,Object>> baseData = mapper.getExportBaseData();
        List<Map<String,Object>> timeData = mapper.getExportTimeData();

        List<Map<String,Object>> typeData = baseData.stream()
                .map(item -> {
                    return Map.of(
                            "MFG_MODULE",item.get("MFG_MODULE"),
                            "EQP_G",item.get("EQP_G"),
                            "EQP_ID",item.get("EQP_ID"),
                            "TYPE",item.get("TYPE")
                    );
                })
                .filter(item -> item.get("MFG_MODULE") != null && item.get("EQP_G") != null && item.get("EQP_ID") != null && item.get("TYPE") != null)
                .distinct()
                .collect(Collectors.toList());

        Map<String, Map<String, Object>> dataMap = new HashMap<>();
        for (Map<String, Object> item : baseData) {
            String key = buildKey(
                    item.get("EQP_G"),
                    item.get("MFG_MODULE"),
                    item.get("EQP_ID"),
                    item.get("TYPE"),
                    item.get("FCST_TIME")
            );
            dataMap.put(key, item);
        }

        List<Map<String,Object>> resultData = new ArrayList<>();

        for (Map<String, Object> data : typeData) {
            Map<String,Object> map = new HashMap<>();

            map.put("MFG_MODULE", data.get("MFG_MODULE"));
            map.put("EQP_G", data.get("EQP_G"));
            map.put("EQP_ID", data.get("EQP_ID"));
            map.put("TYPE", data.get("TYPE"));

            for (Map<String, Object> time : timeData) {
                String shiftDateLine = time.get("SHIFT_DATE_LINE").toString();
                String key = buildKey(
                        data.get("EQP_G"),
                        data.get("MFG_MODULE"),
                        data.get("EQP_ID"),
                        data.get("TYPE"),
                        time.get("SHIFT_DATE_LINE")
                );

                Map<String, Object> matched = dataMap.get(key);
                if (matched != null) {
                    map.put(shiftDateLine, matched.get("VALUE"));
                } else {
                    map.put(shiftDateLine, "");
                }
            }

            resultData.add(map);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data",resultData);
        result.put("header",timeData);
        return result;
        }


   public Map<String,Object> getExportBaseDataByModule(String pri,String curDate){
       List<Map<String,Object>> baseData = mapper.getExportBaseTimeByModule(pri,curDate);
       List<Map<String,Object>> timeData = mapper.getExportTimeData();

       List<Map<String,Object>> typeData = mapper.getExportBaseDataByModule(pri,curDate);

       Map<String, Map<String, Object>> dataMap = new HashMap<>();
       for (Map<String, Object> item : baseData) {
           String key = buildKey(
                   item.get("EQP_G"),
                   item.get("MODULE"),
                   item.get("EQP_ID"),
                   item.get("TYPE"),
                   item.get("FCST_TIME")
           );
           dataMap.put(key, item);
       }

       List<Map<String,Object>> resultData = new ArrayList<>();

       for (Map<String, Object> data : typeData) {
           Map<String,Object> map = new HashMap<>();

           //MODULE,EQP_G,EQP_ID,TOTAL_COUNT,DTH,DELAY_RECOVER_FLAG,"TYPE",RECOVER_COUNT,TOTAL_RECOVER_RATE
           // ,DTH_RECOVER_RATE,STATUS
           map.put("MODULE", data.get("MODULE"));
           map.put("EQP_G", data.get("EQP_G"));
           map.put("EQP_ID", data.get("EQP_ID"));
           map.put("TOTAL_COUNT", data.get("TOTAL_COUNT"));
           map.put("DTH", data.get("DTH"));
           map.put("DELAY_RECOVER_FLAG", data.get("DELAY_RECOVER_FLAG"));
           map.put("TYPE", data.get("TYPE"));
           map.put("RECOVER_COUNT", data.get("RECOVER_COUNT"));
           map.put("TOTAL_RECOVER_RATE", data.get("TOTAL_RECOVER_RATE"));
           map.put("DTH_RECOVER_RATE", data.get("DTH_RECOVER_RATE"));
           map.put("STATUS", data.get("STATUS"));

           for (Map<String, Object> time : timeData) {
               String shiftDateLine = time.get("SHIFT_DATE_LINE").toString();
               String key = buildKey(
                       data.get("EQP_G"),
                       data.get("MODULE"),
                       data.get("EQP_ID"),
                       data.get("TYPE"),
                       time.get("SHIFT_DATE_LINE")
               );

               Map<String, Object> matched = dataMap.get(key);
               if (matched != null) {
                   map.put(shiftDateLine, matched.get("VALUE"));
               } else {
                   map.put(shiftDateLine, "");
               }
           }

           resultData.add(map);
       }

       Map<String,Object> result = new HashMap<>();
       result.put("data",resultData);
       result.put("header",timeData);
       return result;
   }


    public Map<String,Object> getExportBaseDataByEQPG(String pri,String curDate,String module){
        List<Map<String,Object>> baseData = mapper.getExportBaseTimeByEQPG(pri,curDate,module);
        List<Map<String,Object>> timeData = mapper.getExportTimeData();

        List<Map<String,Object>> typeData = mapper.getExportBaseDataByEQPG(pri,curDate,module);

        Map<String, Map<String, Object>> dataMap = new HashMap<>();
        for (Map<String, Object> item : baseData) {
            String key = buildKey(
                    item.get("EQP_G"),
                    item.get("MODULE"),
                    item.get("EQP_ID"),
                    item.get("TYPE"),
                    item.get("FCST_TIME")
            );
            dataMap.put(key, item);
        }

        List<Map<String,Object>> resultData = new ArrayList<>();

        for (Map<String, Object> data : typeData) {
            Map<String,Object> map = new HashMap<>();

            //MODULE,EQP_G,EQP_ID,TOTAL_COUNT,DTH,DELAY_RECOVER_FLAG,"TYPE",RECOVER_COUNT,TOTAL_RECOVER_RATE
            // ,DTH_RECOVER_RATE,STATUS
            map.put("MODULE", data.get("MODULE"));
            map.put("EQP_G", data.get("EQP_G"));
            map.put("EQP_ID", data.get("EQP_ID"));
            map.put("TOTAL_COUNT", data.get("TOTAL_COUNT"));
            map.put("DTH", data.get("DTH"));
            map.put("DELAY_RECOVER_FLAG", data.get("DELAY_RECOVER_FLAG"));
            map.put("TYPE", data.get("TYPE"));
            map.put("RECOVER_COUNT", data.get("RECOVER_COUNT"));
            map.put("TOTAL_RECOVER_RATE", data.get("TOTAL_RECOVER_RATE"));
            map.put("DTH_RECOVER_RATE", data.get("DTH_RECOVER_RATE"));
            map.put("STATUS", data.get("STATUS"));

            for (Map<String, Object> time : timeData) {
                String shiftDateLine = time.get("SHIFT_DATE_LINE").toString();
                String key = buildKey(
                        data.get("EQP_G"),
                        data.get("MODULE"),
                        data.get("EQP_ID"),
                        data.get("TYPE"),
                        time.get("SHIFT_DATE_LINE")
                );

                Map<String, Object> matched = dataMap.get(key);
                if (matched != null) {
                    map.put(shiftDateLine, matched.get("VALUE"));
                } else {
                    map.put(shiftDateLine, "");
                }
            }

            resultData.add(map);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data",resultData);
        result.put("header",timeData);
        return result;
    }

    public Map<String,Object> getExportBaseDataByEqpId(String pri ,String curDate,String eqpg){
        List<Map<String,Object>> baseData = mapper.getExportBaseTimeByEqpId(pri,curDate,eqpg);
        List<Map<String,Object>> timeData = mapper.getExportTimeData();

        List<Map<String,Object>> typeData = mapper.getExportBaseDataByEqpId(pri,curDate,eqpg);

        Map<String, Map<String, Object>> dataMap = new HashMap<>();
        for (Map<String, Object> item : baseData) {
            String key = buildKey(
                    item.get("EQP_G"),
                    item.get("MODULE"),
                    item.get("EQP_ID"),
                    item.get("TYPE"),
                    item.get("FCST_TIME")
            );
            dataMap.put(key, item);
        }

        List<Map<String,Object>> resultData = new ArrayList<>();

        for (Map<String, Object> data : typeData) {
            Map<String,Object> map = new HashMap<>();

            //MODULE,EQP_G,EQP_ID,TOTAL_COUNT,DTH,DELAY_RECOVER_FLAG,"TYPE",RECOVER_COUNT,TOTAL_RECOVER_RATE
            // ,DTH_RECOVER_RATE,STATUS
            map.put("MODULE", data.get("MODULE"));
            map.put("EQP_G", data.get("EQP_G"));
            map.put("EQP_ID", data.get("EQP_ID"));
            map.put("TOTAL_COUNT", data.get("TOTAL_COUNT"));
            map.put("DTH", data.get("DTH"));
            map.put("DELAY_RECOVER_FLAG", data.get("DELAY_RECOVER_FLAG"));
            map.put("TYPE", data.get("TYPE"));
            map.put("RECOVER_COUNT", data.get("RECOVER_COUNT"));
            map.put("TOTAL_RECOVER_RATE", data.get("TOTAL_RECOVER_RATE"));
            map.put("DTH_RECOVER_RATE", data.get("DTH_RECOVER_RATE"));
            map.put("STATUS", data.get("STATUS"));

            for (Map<String, Object> time : timeData) {
                String shiftDateLine = time.get("SHIFT_DATE_LINE").toString();
                String key = buildKey(
                        data.get("EQP_G"),
                        data.get("MODULE"),
                        data.get("EQP_ID"),
                        data.get("TYPE"),
                        time.get("SHIFT_DATE_LINE")
                );

                Map<String, Object> matched = dataMap.get(key);
                if (matched != null) {
                    map.put(shiftDateLine, matched.get("VALUE"));
                } else {
                    map.put(shiftDateLine, "");
                }
            }

            resultData.add(map);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data",resultData);
        result.put("header",timeData);
        return result;
    }


    private String buildKey(Object... parts) {
        StringBuilder sb = new StringBuilder();
        for (Object part : parts) {
            sb.append(part != null ? part.toString() : "").append("|");
        }
        return sb.toString();
    }


    }
