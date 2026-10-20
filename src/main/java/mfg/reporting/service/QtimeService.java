package mfg.reporting.service;

import mfg.reporting.mapper.QtimeMapper;
import mfg.reporting.mapper.WorkSysMapper;
import mfg.reporting.pojo.QtimeOverDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QtimeService {
    @Resource
    public QtimeMapper qtimeMapper;

    public List<Map<String,Object>> getQtimeOverSummary(String nengflag,String module,String tech){
        List<Map<String,Object>> list = qtimeMapper.getQtimeOverSummary(nengflag,module,tech);
        return list;
    }

    public List<QtimeOverDetailVO> getQtimeOverDetail(String nengflag, String module, String tech, String startdate, String enddate, String ifkeyin,String targetmodule){
        List<QtimeOverDetailVO> list = qtimeMapper.getQtimeOverDetail(nengflag,module,tech,startdate,enddate,ifkeyin,targetmodule);
        return list;
    }

    public List<Map<String,Object>> getTechList()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = qtimeMapper.getTechList();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("TECH_VALUE"));
            map.put("label",item.get("TECH_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getEditOption(String type)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        list = qtimeMapper.getEditOption(type);
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("KEY_VALUE"));
            map.put("label",item.get("KEY_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public void updateQtimeOverInfo(String SHIFT_DATE,String LOT_ID,String CURRENT_OPE_NO,String TYPE,String KEYVALUE,String REMARK,String UPDATE_USER)
    {
        if("response_MODULE".equals(TYPE)){
            qtimeMapper.updateQtimeOverModule(SHIFT_DATE,LOT_ID,CURRENT_OPE_NO,KEYVALUE,REMARK,UPDATE_USER);
        } else if ("major_REASON".equals(TYPE)) {
            qtimeMapper.updateQtimeOverReason(SHIFT_DATE,LOT_ID,CURRENT_OPE_NO,KEYVALUE,REMARK,UPDATE_USER);
        }else if ("over_COMMENT".equals(TYPE)) {
            qtimeMapper.updateQtimeOverComment(SHIFT_DATE,LOT_ID,CURRENT_OPE_NO,KEYVALUE,REMARK,UPDATE_USER);
        }
        else if("mfg_OVER".equals(TYPE))
        {
            qtimeMapper.updateQtimeMFG(SHIFT_DATE,LOT_ID,CURRENT_OPE_NO,KEYVALUE,REMARK,UPDATE_USER);
        }

    }

    public List<Map<String,Object>> getQtimeOverReason(String type)
    {
        List<Map<String,Object>> list =  qtimeMapper.getQtimeOverReason(type);
        return list;
    }

    public void deleteQtimeOverReason(String key,String type)
    {
        qtimeMapper.deleteQtimeOverReason(key,type);
    }

    public void insertQtimeOverReason(String key,String type,String updateUser)
    {
        qtimeMapper.insertQtimeOverReason(key,type,updateUser);
    }

    public List<Map<String,Object>> getSumReport(String lot_id,String ope_no){
        List<Map<String,Object>> list = qtimeMapper.getSumReport(lot_id,ope_no);
        return list;
    }

    public List<Map<String,Object>> getQtimeDetail(String lotid,String tri_openo,String tar_openo,String qtime_grage,String qtime_spec)
    {
        List<Map<String,Object>> list = qtimeMapper.getQtimeDetail(lotid,tri_openo,tar_openo,qtime_grage,qtime_spec);
        return list;
    }

    public List<Map<String,Object>> getCommByOpe(String lotid,String tri_openo,String tar_openo,String ope_no)
    {
        List<Map<String,Object>> list = qtimeMapper.getCommByOpe(lotid,tri_openo,tar_openo,ope_no);
        return list;
    }

    public List<Map<String,Object>> getCommByLot(String lotid,String tri_openo,String tar_openo)
    {
        List<Map<String,Object>> list = qtimeMapper.getCommByLot(lotid,tri_openo,tar_openo);
        return list;
    }

    public void updateQtimeOverMemo(String lotid,String tri_openo,String tar_openo,String ope_no,String memo,String UPDATE_USER)
    {
        qtimeMapper.updateQtimeOverMemo(lotid,tri_openo,tar_openo,ope_no,memo,UPDATE_USER);
    }

    public void updateQtimeOverComm(String lotid,String tri_openo,String tar_openo,String comment,String UPDATE_USER)
    {
        qtimeMapper.updateQtimeOverComm(lotid,tri_openo,tar_openo,comment,UPDATE_USER);
    }

    public List<Map<String,Object>> getQtimefLOW(String prodspec_ID,String flow_KEY,String tri_openo,String tar_openo,String qtime_grage,String qtime_spec)
    {
        List<Map<String,Object>> list = qtimeMapper.getQtimefLOW(prodspec_ID,flow_KEY,tri_openo,tar_openo,qtime_grage,qtime_spec);
        return list;
    }


}
