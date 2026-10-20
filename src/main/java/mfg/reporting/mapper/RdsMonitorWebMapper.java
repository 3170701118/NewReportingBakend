package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RdsMonitorWebMapper {

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsHisSummaryChartData(String fab,String closeWipColumn,String module, String groupType);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsRealSummaryTableListGroupModule(String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsRealDetailsTableList(String fab,String module,String eqpId,String remainRdsmin,String remainRdsmax,String testfor);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsHisDetailsTableList(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsHitRatioDetail(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorPlanSetting(String fab ,String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRDSManualCountTrendChart(String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsManualCountDetail(String shiftDate,String fab,String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsHitRatioSummaryChart(String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getFeedBackRatioSummaryChart(String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsEQPGBackupJobGroup();

    @UseDataSource("a3mfg")
    void insertRdsEQPGBackupJobGroup(Map<String,Object>data);

    @UseDataSource("a3mfg")
    void deleteRdsEQPGBackupJobGroup(Map<String,Object>data);

    @UseDataSource("a3mfg")
    void insertRdsOverTimeReason(Map<String,Object>data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMoinitorPlanDetail(@Param("fab")String fab, @Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRdsHitRatioPre7DDetail(String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRDSAbnormalRecipeSummary(String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRDSAbnormalRecipeDetails(String shiftDate,String fab,String module);
}
