package mfg.reporting.service;

import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.OcsMonitorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OcsMonitorService {

    @Resource
    private OcsMonitorMapper ocsMonitorMapper; // Changed from public to private

    public List<Map<String,Object>> getOcsMonitorTableSummary(String fab,String shiftDate,String daynight)
    {
        return ocsMonitorMapper.getOcsMonitorTableSummary(fab,shiftDate,daynight);
    }

    public List<Map<String,Object>> getOcsMonitorPlanDetails(String fab,String shiftDate,String daynight,String module,String type)
    {
        return ocsMonitorMapper.getOcsMonitorPlanDetails(fab,shiftDate,daynight,module,type);
    }

    public List<Map<String,Object>> getOcsMonitorStatusDetails(String fab,String shiftDate,String daynight,String module,String type)
    {
        return ocsMonitorMapper.getOcsMonitorStatusDetails(fab,shiftDate,daynight,module,type);
    }

    public List<Map<String,Object>> getOcsMonitorNGChartByEQPG(String fab,String shiftDate,String daynight,String module,String testFor)
    {
        return ocsMonitorMapper.getOcsMonitorNGChartByEQPG(fab,shiftDate,daynight,module,testFor);
    }

    public List<Map<String,Object>> getOCSMonitorNGChartNew(Map<String,Object> data){
        return ocsMonitorMapper.getOCSMonitorNGChartNew(data);
    }

    public List<Map<String,Object>> getOcsMonitorNGChartByeqpId(String fab,String shiftDate,String daynight,String testFor,String eqpg)
    {
        return ocsMonitorMapper.getOcsMonitorNGChartByeqpId(fab,shiftDate,daynight,testFor,eqpg);
    }

    public List<Map<String,Object>> getOcsMonitorNGChartByDummy(String fab,String shiftDate,String daynight,String testFor,String eqpId)
    {
        return ocsMonitorMapper.getOcsMonitorNGChartByDummy(fab,shiftDate,daynight,testFor,eqpId);
    }


    public List<Map<String,Object>> getNgTrendChartSummary(Map<String,Object> data){
        return ocsMonitorMapper.getNgTrendChartSummary(data);
    }

    public List<CommonSelectDTO> getpurposeList(String fab){
        return ocsMonitorMapper.getpurposeList(fab);
    }

    public List<CommonSelectDTO> geteqpgList(String fab,String module){
        return ocsMonitorMapper.geteqpgList(fab,module);
    }

    public List<Map<String,Object>> getNgDetailsByLot(Map<String,Object> data){
        return ocsMonitorMapper.getNgDetailsByLot(data);
    }

    public List<Map<String,Object>> getCompeteRatioDaily(String fab){
        return ocsMonitorMapper.getCompeteRatioDaily(fab);
    }

    //getStayTimetrendChart

    public List<Map<String,Object>> getStayTimetrendChart(Map<String,Object> data){
        return ocsMonitorMapper.getStayTimetrendChart(data);
    }
}
