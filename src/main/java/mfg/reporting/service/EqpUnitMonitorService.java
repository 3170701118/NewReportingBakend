package mfg.reporting.service;

import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.EqpUnitMonitorMapper;
import mfg.reporting.pojo.EqpUnitMonitorPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EqpUnitMonitorService {
    @Resource
    public EqpUnitMonitorMapper mapper;

    public List<EqpUnitMonitorPO> getEqpUnitMonitorInfo(String module, String eqpG, String unitFlag, String unitJudge){
        return mapper.getEqpUnitMonitorInfo(module, eqpG, unitFlag, unitJudge);
    }

    public List<CommonSelectDTO> getEQPGroupListByModule(String module){
        return mapper.getEQPGroupListByModule(module);
    }

    public List<EqpUnitMonitorPO> getHstEQPUnitMonitorInfo( String eqpId, String unitId,String unitFlag, String startDate, String endDate){
        return mapper.getHstEQPUnitMonitorInfo(eqpId, unitId,unitFlag, startDate, endDate);
    }

    public void keyInComment(String eqpId,String unitId,String occurTime,String fcomment,String userName){
        mapper.updateFcomment(eqpId,unitId,occurTime,fcomment,userName);
    }
}
