package mfg.reporting.service;

import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.mapper.WatHoldLotMapper;
import mfg.reporting.pojo.WatHoldLot;
import mfg.reporting.pojo.WatHoldLotChart;
import mfg.reporting.pojo.WatHoldLotSummary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :gszhang
 */
@Service
public class WatHoldLotService {

    @Resource
    public WatHoldLotMapper watHoldLotMapper;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 获取 WatHoldLot Details 列表
     * @return WatHoldLot 列表
     */
    public List<WatHoldLot> getWatHoldLotList(String module,String type) {
        return watHoldLotMapper.getWatHoldLotList(module,type);
    }

    /**
     * 合并 WatHoldLot
     * @param param 参数
     */
    public void mergeWatHoldLot(Map<String, Object> param) {
        Map<String, Object> updateParam = new HashMap<>();
        String updateUser = jwtUtils.getUsernameFromToken();
        updateParam.put("updateUser", updateUser);
        updateParam.put("scrapFlag", param.get("scrapFlag"));
        updateParam.put("scrapComment", "0".equals(param.get("scrapFlag"))?param.get("scrapComment"):param.get("outputDate"));
        updateParam.put("lotId", param.get("lotId"));
        updateParam.put("module",param.get("module"));
        updateParam.put("holdLotStartTime", param.get("holdLotStartTime"));

        watHoldLotMapper.mergeWatHoldLot(updateParam);
    }

    public List<WatHoldLotSummary> getWatHoldLotSummary(){
        return watHoldLotMapper.getWatHoldLotSummaryTable();
    }

    public List<WatHoldLotChart> getWatHoldLotSummaryChart(){
        return watHoldLotMapper.getWatHoldLotSummaryChart();
    }
}
