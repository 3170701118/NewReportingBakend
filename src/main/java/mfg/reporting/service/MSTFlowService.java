package mfg.reporting.service;

import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.RevenueDTO;
import mfg.reporting.mapper.MSTFlowMapper;
import mfg.reporting.dto.MstFlowSummaryDTO;
import mfg.reporting.dto.MstFlowWipDTO;
import mfg.reporting.dto.MstFlowMoveByProdgDTO;
import mfg.reporting.dto.MstFlowMoveByEqpgDTO;
import mfg.reporting.dto.MstFlowLotOpeHsDTO;
import mfg.reporting.dto.MstFlowQtimeDTO;
import mfg.reporting.dto.MstFlowSamplingDTO;
import mfg.reporting.dto.MstFlowPathDTO;
import mfg.reporting.dto.MstFlowDybDTO;
import mfg.reporting.dto.MstFlowSgsDTO;
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
public class MSTFlowService {
    @Resource
    public MSTFlowMapper mstFlowMapper;


    public List<MstFlowSummaryDTO> getMstFlowSummary(String tech,String prodg,String module,String eqpg,String neng,String view){
        List<MstFlowSummaryDTO> list = mstFlowMapper.getMstFlowSummary(tech,prodg,module,eqpg,neng,view);
        return list;
    }

    public List<MstFlowWipDTO> getWipDetailList(String tech,String prodg,String state,String openo,String neng,String view){
        List<MstFlowWipDTO> list = mstFlowMapper.getWipDetailList(tech,prodg,state,openo,neng,view);
        return list;
    }

    public List<MstFlowWipDTO> getBackupWipDetailList(String tech,String prodg,String state,String openo,String neng,String view){
        List<MstFlowWipDTO> list = mstFlowMapper.getBackupWipDetailList(tech,prodg,state,openo,neng,view);
        return list;
    }

    public List<MstFlowMoveByProdgDTO> getMoveByProdgList(String tech,String prodg,String date,String openo,String neng,String view){
        List<MstFlowMoveByProdgDTO> list = mstFlowMapper.getMoveByProdgList(tech,prodg,date,openo,neng,view);
        return list;
    }

    public List<MstFlowMoveByEqpgDTO> getMoveByEqpgList(String tech,String prodg,String date,String openo,String neng,String view){
        List<MstFlowMoveByEqpgDTO> list = mstFlowMapper.getMoveByEqpgList(tech,prodg,date,openo,neng,view);
        return list;
    }

    public List<MstFlowLotOpeHsDTO> getLotOpeHSList(String lotid,String prodspecid){
        List<MstFlowLotOpeHsDTO> list = mstFlowMapper.getLotOpeHSList(lotid,prodspecid);
        return list;
    }

    public List<MstFlowQtimeDTO> getQtimeDetailList(String tech,String prodg,String openo){
        List<MstFlowQtimeDTO> list = mstFlowMapper.getQtimeDetailList(tech,prodg,openo);
        return list;
    }

    public List<MstFlowSamplingDTO> getSamplingRateDetailList(String tech,String prodg,String openo){
        List<MstFlowSamplingDTO> list = mstFlowMapper.getSamplingRateDetailList(tech,prodg,openo);
        return list;
    }

    public List<MstFlowPathDTO> getpathDetailList(String prodg,String prodspecid,String openo,String mainpdid){
        List<MstFlowPathDTO> list = mstFlowMapper.getpathDetailList(prodg,prodspecid,openo,mainpdid);
        return list;
    }

    public List<MstFlowDybDTO> getDybLotDetailList(String tech,String prodg,String view){
        List<MstFlowDybDTO> list = mstFlowMapper.getDybLotDetailList(tech,prodg,view);
        return list;
    }

    public List<MstFlowSgsDTO> getSgsEqpList(String tech,String prodg,String openo,String neng,String view){
        List<MstFlowSgsDTO> list = mstFlowMapper.getSgsEqpList(tech,prodg,openo,neng,view);
        return list;
    }

    public List<MstFlowWipDTO> getSgsWipDetailList(String tech,String prodg,String sgseqp,String openo,String neng,String view){
        List<MstFlowWipDTO> list = mstFlowMapper.getSgsWipDetailList(tech,prodg,sgseqp,openo,neng,view);
        return list;
    }


    public List<Map<String,Object>> getTechList()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = mstFlowMapper.getTechList();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("TECH_VALUE"));
            map.put("label",item.get("TECH_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getProdgList(String tech)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = mstFlowMapper.getProdgList(tech);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("PRODG1_VALUE"));
            map.put("label",item.get("PRODG1_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getEqpgList(String tech,String prodg,String module)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = mstFlowMapper.getEqpgList(tech,prodg,module);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("EQPG_VALUE"));
            map.put("label",item.get("EQPG_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }



}
