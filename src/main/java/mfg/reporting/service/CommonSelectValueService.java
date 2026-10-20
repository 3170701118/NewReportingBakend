package mfg.reporting.service;

import mfg.reporting.mapper.CommonMapper;
import mfg.reporting.mapper.CommonSelectValueMapper;
import mfg.reporting.pojo.FunctionList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonSelectValueService {
    @Resource
    public CommonSelectValueMapper commonSelectValueMapper;

    @Resource
    public CommonMapper commonMapper;
    /**
     * 获取 menuId,MenuName 的对应关系；
     * @return List
     */
    public List<Map<String,Object>> getMenuList(){
        List<Map<String,Object>> list = commonSelectValueMapper.getMenuList();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("ROUTE_NAME"));
            map.put("label",item.get("MENU_NAME"));
            returnList.add(map);
        }
        return returnList;
    }

    /**
     * routeName 与menuName 的对应关系
     * @return list
     */
    public List<Map<String,Object>> getFunctionOwnerList(String query){
        List<Map<String,Object>> list = commonSelectValueMapper.getFunctionOwnerList(query);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("EMAIL"));
            map.put("label",item.get("CNAME").toString().concat("( ").concat(item.get("EMAIL").toString()).concat(" )"));
            returnList.add(map);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("value","A12_MFG");
        map.put("label","A12_MFG");
        returnList.add(map);
        return returnList;
    }


    /**
     * 获取所有的functionList
     * @return
     */
    public List<Map<String,Object>> getAllFunctionList(){
        FunctionList fl = new FunctionList();
        List<FunctionList> list = commonMapper.searchFunctionList(fl);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (FunctionList item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.getFunctionId());
            map.put("label",item.getMenuName().concat(" - ").concat(item.getFunctionId()));
            returnList.add(map);
        }
        return returnList;
    }

    /**
     * 现公司 email 与 cname 的对应关系
     */
    public List<Map<String,Object>> getAllCnames(){
        List<Map<String,Object>> list = commonSelectValueMapper.getAllCnames();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("EMAIL"));
            map.put("label",item.get("CNAME").toString().concat("( ").concat(item.get("EMAIL").toString()).concat(" )"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getAllSdepts(){
        List<Map<String,Object>> list = commonSelectValueMapper.getAllSdepts();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("DEPT_ID").toString().concat("-").concat(item.get("SDEPT_CNAME").toString()));
            map.put("label",item.get("DEPT_CNAME").toString().concat(" - ").concat(item.get("SDEPT_CNAME").toString()));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getAllDepts(){
        List<Map<String,Object>> list = commonSelectValueMapper.getAllDepts();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("DEPT_ID"));
            map.put("label",item.get("DEPT_CNAME"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getAllSDeptList(){
        List<Map<String,Object>> list = commonSelectValueMapper.getAllSDeptList();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("SDEPT_ID"));
            map.put("label",item.get("SDEPT_CNAME"));
            returnList.add(map);
        }
        return returnList;
    }
}
