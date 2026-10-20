package mfg.reporting.service;


import mfg.reporting.mapper.PathHealthMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PathHealthService {
    @Resource
    public PathHealthMapper pathHealthMapper;

    public List<Map<String,Object>> getEqp(String fab, String module, String eqpg){
        List<Map<String,Object>> list = null;
        if("N1A1".equals(fab)){
            list=pathHealthMapper.getEqpA12(module,eqpg);
        } else if ("N1A3".equals(fab)) {
            list=pathHealthMapper.getEqpA3(module,eqpg);
        }
        return list;
    }

    public List<Map<String,Object>> getEqpg(String fab, String module){
        List<Map<String,Object>> list = null;
        List<Map<String,Object>> returnList = new ArrayList<>();
        if("N1A1".equals(fab)){
            list=pathHealthMapper.getEqpgA12(module);
        } else if ("N1A3".equals(fab)) {
            list=pathHealthMapper.getEqpgA3(module);
        }
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("VAL"));
            map.put("label",item.get("TEXT"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getEqpProdg(String  fab,String module, String eqpg){
        List<Map<String,Object>> list =pathHealthMapper.getEqpProdg(fab,module,eqpg);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("VAL"));
            map.put("label",item.get("TEXT"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getTech(){
        List<Map<String,Object>> list =pathHealthMapper.getTech();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("VAL"));
            map.put("label",item.get("TEXT"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getSuffix(String  fab,String module, String eqpg,String prodg){
        List<Map<String,Object>> list =pathHealthMapper.getSuffix(fab,module,eqpg,prodg);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("VAL"));
            map.put("label",item.get("TEXT"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getOpeno(String  fab,String module, String eqpg,String prodg,String prodgspecid){
        List<Map<String,Object>> list =pathHealthMapper.getOpeno(fab,module,eqpg,prodg,prodgspecid);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("VAL"));
            map.put("label",item.get("TEXT"));
            returnList.add(map);
        }
        return returnList;
    }


    public List<Map<String,Object>> getPathDetail(String  fab,String module, String eqpg,String prodg,String prodgspecid,String openo,String recipe,String[] array,String tech ){
        String  tb="";
        if ("ETCH".equals(module))
        {
            tb = "A1_MFG.ETCH_CLOSE_PATH_DETAIL_COMM";
        } else if ("PHOTO".equals(module))
        {
            tb = "A1_MFG.PHOTO_CLOSE_PATH_DETAIL_COMM";
        }
        else if ("CMP".equals(module))
        {
            tb = "A1_MFG.CMP_CLOSE_PATH_DETAIL_COMM";
        }
        else if ("WET".equals(module))
        {
            tb = "A1_MFG.WET_CLOSE_PATH_DETAIL_COMM";
        }
        else if ("TF".equals(module))
        {
            tb = "A1_MFG.TF_CLOSE_PATH_DETAIL_COMM";
        }
        else if ("DIFF".equals(module))
        {
            tb = "A1_MFG.DIFF_CLOSE_PATH_DETAIL_COMM";
        }
        else if ("Measurement".equals(module))
        {
            tb = "A1_MFG.MIT_CLOSE_PATH_DETAIL_COMM";
        }
        List<Map<String,Object>> list =pathHealthMapper.getPathDetail(fab, module, eqpg, prodg, prodgspecid, openo, recipe, array,tb,tech);
        return list;
    }
}
