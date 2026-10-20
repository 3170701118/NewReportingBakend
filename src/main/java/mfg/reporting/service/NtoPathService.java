package mfg.reporting.service;

import mfg.reporting.mapper.NtoPathMapper;
import mfg.reporting.pojo.NTOPathMFGMaintainVO;
import mfg.reporting.pojo.NTOPathProdgDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NtoPathService {
    @Resource
    public NtoPathMapper ntoPathMapper;

    public List<Map<String,Object>> getNtoProdgMaintain()
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoProdgMaintain();
        return list;
    }

    public void deleteNtoProdgMaintain(String prodg,String chipbody,String updateUser)
    {
        ntoPathMapper.deleteNtoProdgMaintain(prodg,chipbody,updateUser);
    }

    public void insertNtoProdgMaintain(String prodg,String chipbody,String updateUser)
    {
        ntoPathMapper.insertNtoProdgMaintain(prodg,chipbody,updateUser);;
    }

    public List<Map<String,Object>> getProdgList()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = ntoPathMapper.getProdgList();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("PRODG_VALUE"));
            map.put("label",item.get("PRODG_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<NTOPathMFGMaintainVO> getNtoMfgPathMaintain(String prodg)
    {
        List<NTOPathMFGMaintainVO> list =  ntoPathMapper.getNtoMfgPathMaintain(prodg);
        return list;
    }

    public void updateNtoPmc(String prodg,String chipbody,String fab,String updateUser)
    {
        ntoPathMapper.updateNtoPmc(prodg,chipbody,fab,updateUser);
    }


    public List<Map<String,Object>> getEqpList(String eqpg)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = ntoPathMapper.getEqpList(eqpg);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("PRODG_VALUE"));
            map.put("label",item.get("PRODG_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public void updateNtoMfg(String prodg,String chipbody,String eqpg,String eqplist,String updateUser)
    {
        ntoPathMapper.updateNtoMfg(prodg,chipbody,eqpg,eqplist,updateUser);
    }

    public List<Map<String,Object>> getNtoModuleList(String prodg)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = ntoPathMapper.getNtoModuleList(prodg);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("MODULE_VALUE"));
            map.put("label",item.get("MODULE_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getNtoSdeptList(String prodg,String deptid)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = ntoPathMapper.getNtoSdeptList(prodg,deptid);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("MODULE_VALUE"));
            map.put("label",item.get("MODULE_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getNtoSummaryModule()
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoSummaryModule();
        return list;
    }

    public List<Map<String,Object>> getNtoSummaryModuleProdg()
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoSummaryModuleProdg();
        return list;
    }

    public List<Map<String,Object>> getNtoSummaryModuleEqpg(String prodg,String deptid,String sdept)
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoSummaryModuleEqpg(prodg,deptid,sdept);
        return list;
    }

    public List<Map<String,Object>> getNtoSummaryProdg()
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoSummaryProdg();
        return list;
    }

    public List<Map<String,Object>> getNtoProdgList()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = ntoPathMapper.getNtoProdgList();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("PRODG_VALUE"));
            map.put("label",item.get("PRODG_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getNtoSummaryProdgEqpg(String prodg)
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoSummaryProdgEqpg(prodg);
        return list;
    }

    public List<Map<String,Object>> getNtoSummaryProdgTable()
    {
        List<Map<String,Object>> list =  ntoPathMapper.getNtoSummaryProdgTable();
        return list;
    }

    public List<NTOPathProdgDetailVO> getNtoProdgDetail(String prodg,String chipbody)
    {
        List<NTOPathProdgDetailVO> list =  ntoPathMapper.getNtoProdgDetail(prodg,chipbody);
        return list;
    }

    public void updateInputFlag()
    {
        ntoPathMapper.updateInputFlag();
    }

    public void updateInputFlagOther()
    {
        ntoPathMapper.updateInputFlagOther();
    }


}

