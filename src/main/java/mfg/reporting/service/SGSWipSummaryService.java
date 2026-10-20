package mfg.reporting.service;

import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.RevenueDTO;
import mfg.reporting.mapper.SGSWipSummaryMapper;
import mfg.reporting.dto.SGSWipSummaryDTO;
import mfg.reporting.dto.SGSWipDetailDTO;
import mfg.reporting.mapper.WorkSysMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SGSWipSummaryService {
    @Resource
    public SGSWipSummaryMapper sgsWipSummaryMapper;


    public List<SGSWipSummaryDTO> getSGSWipSummary(String eqpid,String neng){
        List<SGSWipSummaryDTO> list = sgsWipSummaryMapper.getSGSWipSummary(eqpid,neng);
        return list;
    }

    public List<SGSWipDetailDTO> getSGSWipDetail(String tech,String openo,String sgsgroup,String eqpid,String neng,String type){
        List<SGSWipDetailDTO> list = sgsWipSummaryMapper.getSGSWipDetail(tech,openo,sgsgroup,eqpid,neng,type);
        return list;
    }

    public List<SGSWipSummaryDTO> getSGSWipTotalSummary(String eqpid,String neng){
        List<SGSWipSummaryDTO> list = sgsWipSummaryMapper.getSGSWipTotalSummary(eqpid,neng);
        return list;
    }

    public List<SGSWipDetailDTO> getSGSWipTotalDetail(String tech,String openo,String sgsgroup,String eqpid,String neng,String type){
        List<SGSWipDetailDTO> list = sgsWipSummaryMapper.getSGSWipTotalDetail(tech,openo,sgsgroup,eqpid,neng,type);
        return list;
    }


    public List<Map<String,Object>> getEqpIdList(String fab)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = sgsWipSummaryMapper.getEqpIdList(fab);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("EQPID_VALUE"));
            map.put("label",item.get("EQPID_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }



}
