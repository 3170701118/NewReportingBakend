package mfg.reporting.service;

import mfg.reporting.mapper.RdsMonitorWebMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RdsMonitorWebService {
    @Resource
    public RdsMonitorWebMapper mapper;

    public List<Map<String,Object>> getRdsHisSummaryChartData(String fab,String closeWipColumn,String module, String groupType)
    {
        return mapper.getRdsHisSummaryChartData(fab,closeWipColumn,module,groupType);
    }


    public List<Map<String,Object>> getRdsRealSummaryTableListGroupModule(String fab)
    {
        return mapper.getRdsRealSummaryTableListGroupModule(fab);
    }

    public List<Map<String,Object>> getRdsRealDetailsTableList(String fab,String module,String eqpId,String remainRdsmin,String remainRdsmax,String testfor)
    {
        return mapper.getRdsRealDetailsTableList(fab,module,eqpId,remainRdsmin,remainRdsmax,testfor);
    }

    public List<Map<String,Object>> getRdsHisDetailsTableList(Map<String,Object> data){
        return mapper.getRdsHisDetailsTableList(data);
    }

    public List<Map<String,Object>> getRdsHitRatioDetail(Map<String,Object> data){
        return mapper.getRdsHitRatioDetail(data);
    }


    public List<Map<String,Object>> getOcsMonitorPlanSetting(String fab ,String module){
        return mapper.getOcsMonitorPlanSetting(fab,module);
    }

    public List<Map<String,Object>> getRDSManualCountTrendChart(String fab){
        return mapper.getRDSManualCountTrendChart(fab);
    }


    public List<Map<String,Object>> getRdsManualCountDetail(String shiftDate,String fab,String module){
        return mapper.getRdsManualCountDetail(shiftDate,fab,module);
    }

    public List<Map<String,Object>> getRdsHitRatioSummaryChart(String fab){
        return mapper.getRdsHitRatioSummaryChart(fab);
    }


    public List<Map<String,Object>> getFeedBackRatioSummaryChart(String fab){
        return mapper.getFeedBackRatioSummaryChart(fab);
    }

    public List<Map<String,Object>> getRdsEQPGBackupJobGroup(){
        return mapper.getRdsEQPGBackupJobGroup();
    }

    public void insertRdsEQPGBackupJobGroup(Map<String,Object> data){

        mapper.insertRdsEQPGBackupJobGroup(data);
    }

    public void deleteRdsEQPGBackupJobGroup(Map<String,Object> data){
        mapper.deleteRdsEQPGBackupJobGroup(data);
    }


    public void insertRdsOverTimeReason(Map<String,Object> data){
        mapper.insertRdsOverTimeReason(data);
    }

    public List<Map<String,Object>> getMoinitorPlanDetail(String fab, String module){
        return mapper.getMoinitorPlanDetail(fab,module);
    }

    public List<Map<String,Object>> getRdsHitRatioPre7DDetail(String fab){
        return mapper.getRdsHitRatioPre7DDetail(fab);
    }

    public List<Map<String,Object>> getRDSAbnormalRecipeSummary(String fab){
        return mapper.getRDSAbnormalRecipeSummary(fab);
    }

    public List<Map<String,Object>> getRDSAbnormalRecipeDetails(String shiftDate,String fab,String module){
        return mapper.getRDSAbnormalRecipeDetails(shiftDate,fab,module);
         }
}
