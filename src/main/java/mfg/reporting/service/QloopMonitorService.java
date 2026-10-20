package mfg.reporting.service;

import mfg.reporting.mapper.QloopMonitorMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class QloopMonitorService {

    @Resource
    private QloopMonitorMapper mapper;

    public List<Map<String,Object>> getControlSummaryData(String fab, String nengFlag){
        return mapper.getControlSummaryData(fab,nengFlag);
    }

    public List<Map<String,Object>> getPullSummaryData(String fab, String nengFlag){
        return mapper.getPullSummaryData(fab,nengFlag);
    }

    public List<Map<String,Object>> getPushSummaryData(String fab, String nengFlag){
        return mapper.getPushSummaryData(fab,nengFlag);
    }

    public List<Map<String,Object>> getControlQloopList(Map<String,Object> data){
        if("Control".equals(data.get("pageType"))){
            return mapper.getControlQloopList(data);
        }else if("Pull".equals(data.get("pageType"))){
            return mapper.getPullQloopList(data);
        }else if("Push".equals(data.get("pageType"))){
            return mapper.getPushQloopList(data);
        }

        return mapper.getControlQloopList(data);
    }

    public List<Map<String,Object>> getLotListByQloopList(Map<String,Object> data){
        return mapper.getLotListByQloopList(data);
    }

    public List<Map<String,Object>> getAbnormalLotDetail(Map<String,Object> data){
        return mapper.getAbnormalLotDetail(data);
    }

    public void updateQtimeAbnormal(String location,String lot_id,String PRODSPEC_ID,String ope_no,String type,String reason,String empid,String UPDATE_USER,String nengFlag)
    {
        mapper.updateQtimeAbnormal(location, lot_id, PRODSPEC_ID, ope_no, type, reason, empid, UPDATE_USER,nengFlag);

    }

}
