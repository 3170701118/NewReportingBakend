package mfg.reporting.service;

import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.RmsMonitorMapper;
import mfg.reporting.pojo.RMSMonitorDetailsPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RmsMonitorService {

    @Resource
    RmsMonitorMapper mapper;

    public Map<String,Object> selectRmsMonitorData() {

        List<Map<String,Object>> list = mapper.selectRmsMonitorData();

        //根据 TECH列 行转列
        List<String> techList =  list.stream()
                .map(item -> (String) item.get("TECH"))
                .distinct()
                .collect(Collectors.toList());

        List<Map<String,Object>> result = new ArrayList<>();


        //新增 10项
        result.add(new HashMap<>(Map.of("ITEM","Track 未建","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Track 不可用","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Track 过期","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Scanner 未建","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Scanner 不可用","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Scanner 过期","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 未建","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 不可用","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 过期","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","EC 未建","MODULE","PHOTO")));
        result.add(new HashMap<>( Map.of("ITEM","EC 不可用","MODULE","PHOTO")));

        result.add(new HashMap<>( Map.of("ITEM","Recipe 未建","MODULE","ETCH")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 不可用","MODULE","ETCH")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 过期","MODULE","ETCH")));
        result.add(new HashMap<>( Map.of("ITEM","EC 未建","MODULE","ETCH")));
        result.add(new HashMap<>( Map.of("ITEM","EC 不可用","MODULE","ETCH")));


        result.add(new HashMap<>( Map.of("ITEM","Recipe 未建","MODULE","TF")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 不可用","MODULE","TF")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 过期","MODULE","TF")));
        result.add(new HashMap<>( Map.of("ITEM","EC 未建","MODULE","TF")));
        result.add(new HashMap<>( Map.of("ITEM","EC 不可用","MODULE","TF")));

        result.add(new HashMap<>( Map.of("ITEM","Recipe 未建","MODULE","DIFF")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 不可用","MODULE","DIFF")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 过期","MODULE","DIFF")));
        result.add(new HashMap<>( Map.of("ITEM","EC 未建","MODULE","DIFF")));
        result.add(new HashMap<>( Map.of("ITEM","EC 不可用","MODULE","DIFF")));

        result.add(new HashMap<>( Map.of("ITEM","Recipe 未建","MODULE","WET")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 不可用","MODULE","WET")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 过期","MODULE","WET")));
        result.add(new HashMap<>( Map.of("ITEM","EC 未建","MODULE","WET")));
        result.add(new HashMap<>( Map.of("ITEM","EC 不可用","MODULE","WET")));

        result.add(new HashMap<>( Map.of("ITEM","Recipe 未建","MODULE","CMP")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 不可用","MODULE","CMP")));
        result.add(new HashMap<>( Map.of("ITEM","Recipe 过期","MODULE","CMP")));
        result.add(new HashMap<>( Map.of("ITEM","EC 未建","MODULE","CMP")));
        result.add(new HashMap<>( Map.of("ITEM","EC 不可用","MODULE","CMP")));

        for(Map<String,Object> map : result){
            for(String tech : techList){
                List<Map<String,Object>> filteredList = list.stream()
                        .filter(item -> item.get("ITEM").equals(map.get("ITEM"))
                                && item.get("MODULE").equals(map.get("MODULE"))
                                && item.get("TECH").equals(tech))
                        .collect(Collectors.toList());

                if(filteredList.size() > 0){
                    map.put(tech,filteredList.get(0).get("TOTAL_COUNT"));
                }else{
                    map.put(tech,0);
                }
            }
        }

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("techList",techList);
        resultMap.put("dataList",result);

        return resultMap;
    }


    public List<Map<String,Object>> selectRmsSummaryData(List<String> productStatus ,String fab) {
        return mapper.selectRmsSummaryData(productStatus,fab);
    }

    public List<Map<String,Object>> getRmsSummaryTableData(String module,List<String> eqpg ,String fab) {
        //调用mapper方法，根据传入的参数进行查询
        return mapper.getRmsSummaryTableData(module,eqpg,fab);
    }

    public List<RMSMonitorDetailsPO> getRmsDetailTableData(String fab,String tech,String type, String module, List<String> productStatus,List<String> eqpg) {
        return mapper.getRmsDetailTableData(fab,tech,type, module, productStatus,eqpg);
    }

    public List<CommonSelectDTO> geteqpGByModule(String module ,String fab) {
        return mapper.geteqpGByModule(module ,fab);
    }

    public List<Map<String,Object>> getRmsDetailByEQPRecipe(String fab,String eqpg,String eqpId,String recipe,String status){
        return mapper.getRmsDetailByEQPRecipe(fab,eqpg ,eqpId,recipe,status);
    }

    public List<Map<String,Object>>getSumamryTrendChart(String fab,String tech,String module,List<String> productStatus){
        return mapper.getSumamryTrendChart(fab,tech,module,productStatus);
    }

    public List<CommonSelectDTO> getTrendChartTech(){
        return mapper.getTrendChartTech();
    }
    public List<Map<String,Object>> getRmsModuleSummaryInfo(String module,String fab){
        return mapper.getRmsModuleSummaryInfo(module,fab);
    }


    public List<Map<String,Object>> getRmsNGSummaryInfo(String fab){
        return mapper.getRmsNGSummaryInfo(fab);
    }

    public List<Map<String,Object>> getRmsNGDetailsInfo(String fab,String module,String shiftDate){
        return mapper.getRmsNGDetailsInfo(fab,module,shiftDate);
    }

    public List<Map<String,Object>>getRmsNgWeekGroupInfo(String fab,String module){
        return mapper.getRmsNgWeekGroupInfo(fab,module);
    }

    public List<Map<String,Object>> getRmsRecipeSummaryGroupTotalInfo(String fab){
        return mapper.getRmsRecipeSummaryGroupTotalInfo(fab);
    }

    public List<Map<String,Object>> getRmsRecipeSummaryInfo(String fab,String module) {
        return mapper.getRmsRecipeSummaryInfo(fab,module);
    }

    public List<Map<String,Object>> getRmsRecipeDetailsInfo(String fab,String module ,String shiftDate) {
        return mapper.getRmsRecipeDetailsInfo(shiftDate,module,fab );
    }
}
