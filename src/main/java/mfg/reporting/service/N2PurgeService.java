package mfg.reporting.service;

import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.mapper.N2PurgeMapper;
import mfg.reporting.pojo.N2PurgeInfoHourlyPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class N2PurgeService {

    @Resource
    public N2PurgeMapper n2PurgeMapper;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 获取N2PurgeInfo details 信息，按小时统计
     * @return List<N2PurgeInfoHourlyPO>
     */
    public List<N2PurgeInfoHourlyPO> getN2PurgeInfoHourly(String location){
        return n2PurgeMapper.getN2PurgeInfoHourly(location);
    }

    public List<N2PurgeInfoHourlyPO> getN2PurgeInfoHourly2(String location){
        return n2PurgeMapper.getN2PurgeInfoHourly2(location);
    }

    public List<Map<String,Object>> getN2PurgetSummaryInfo(String location){
        return n2PurgeMapper.getN2PurgetSummaryInfo(location);
    }


    public void updateFcstDoneTimeByLotId(String lotId, String fcstDoneTime,String updateUser){
        n2PurgeMapper.updateFcstDoneTimeByLotId(lotId, fcstDoneTime,updateUser);
    }
}
