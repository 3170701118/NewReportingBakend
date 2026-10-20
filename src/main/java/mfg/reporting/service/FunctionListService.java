package mfg.reporting.service;

import mfg.reporting.mapper.CommonMapper;
import mfg.reporting.pojo.FunctionList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionListService {
    @Resource
    public CommonMapper cm;

    /**
     * 查询functionList
     * @param fl
     * @return
     */
    public List<FunctionList> SearchFunctionList(FunctionList fl){
        try{
            List<FunctionList> list = cm.searchFunctionList(fl);
            return list;
        }catch (Exception e){
            throw new RuntimeException("查询失败");
        }
    }

    /**
     *  根据functionId 删除functionItem
     * @param functionId
     */
    public void deleteFunctionItem(String functionId){
        cm.deleteFunctionItem(functionId);
    }

}
