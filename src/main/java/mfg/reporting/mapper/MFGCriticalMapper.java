package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.dto.MFGCriticalA3MoveSelfEQPGTargetVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MFGCriticalMapper {

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getTechList(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getStdTechList();


    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getRealModuleList(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getEqpgList(@Param("fab") String fab,@Param("module") String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLpHealthChart(@Param("fab") String fab );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLpHealthChartSummaryByEQPG(@Param("fab")String fab,@Param("module") String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLpHealthChartDetailDaily(@Param("shiftDate") String shiftDate ,@Param("fab") String fab , @Param("module") String module ,@Param("eqpg") String eqpg);

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getEqpList(@Param("fab") String fab , @Param("module") String module ,@Param("eqpg") String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDYBChart(@Param("fab") String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDybChartSummaryByeqpg(@Param("fab") String fab ,@Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDYBChartDetailDaily(@Param("shiftDate") String shiftDate ,@Param("fab") String fab , @Param("module") String module , @Param("eqpg") String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getQSRatioChart(@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getForceRatioTimeChart(@Param("fab")String fab,@Param("date") String date,@Param("type") String type);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getForceRatioBatchChart(@Param("fab")String fab,@Param("date") String date,@Param("type") String type);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getForceRatioDetail(@Param("fab")String fab,@Param("dimension") String dimension,@Param("date") String date,@Param("type") String type,@Param("eqpg") String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementNgRatio(@Param("fab")String fab,@Param("date") String date);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getMeasurementForceEqpGListByFab(@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getMeasurementNgEqpGListByFab(@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLpFilterTableList(@Param("fab")String fab,@Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDybTargetTableList(@Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> validDYBTargetInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void insertDYBTargetInfo( Map<String,Object> data);

    @UseDataSource("a3mfg")
    void updateDYBTargetInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void deleteLpFilterInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> validLpFilterInfo(Map<String,Object> data);


    @UseDataSource("a3mfg")
    void insertLpFilterInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementSRInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementSRTableByTechList(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementSRTableByProdList(Map<String,Object> data);
    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementMoveRatioInfo(@Param("fab") String fab);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getMeasurementSREQPGInfoByFab(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getMeasurementSRTechInfoByFab(@Param("fab") String fab);


    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getMeasurementSRProdgInfoByFab(@Param("fab") String fab ,@Param("eqpg") String eqpg,@Param("tech") String tech);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPilotRunFabSummaryChart(@Param("fab") String fab);

    /**
     *
     * @param fab
     * @param module
     * @param type 1 子批split上机用时 2. 子批merge用时
     *             3.母批NOPATH Split上机用时 4.母批NOPATH Merge用时
     *             5.母批有PATH Split上机用时 6.母批有PATH Merge用时
     * @return
     */
    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPilotRunSummaryChart(@Param("fab") String fab,@Param("module") String module,@Param("type") int type);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getpilotRunSummaryTableList(@Param("fab") String fab,@Param("module") String module,@Param("type") int type,@Param("shiftDate")String shiftDate);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getpilotRunDetailsTableList(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void updateLpHealthDetails(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getReworkSummaryChart(@Param("fab") String fab,@Param("module") String module,@Param("eqpg")String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getReworkMoveDetails(@Param("fab") String fab,@Param("module") String module,@Param("eqpg")String eqpg,@Param("shiftDate")String shiftDate);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getReprocessMonitorSummaryChart(@Param("fab") String fab,@Param("module") String module,@Param("eqpg")String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getReprocessMonitorDetails(@Param("fab") String fab,@Param("module") String module,@Param("eqpg")String eqpg,@Param("shiftDate")String shiftDate);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getReworkTargetList(@Param("module") String module);

    @UseDataSource("a3mfg")
    void updateReworkTargetInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> validReworkTargetInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void insertReworkTargetInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementNGDetails(@Param("fab") String fab,@Param("date") String date, @Param("eqpg")String eqpg, @Param("tech") String tech, @Param("prodg") String prodg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementWATRatioSummary(@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementWATDetail(@Param("fab")String fab,@Param("shiftDate")String shiftDate);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLotPriorityRatioChart(@Param("fab")String fab,@Param("location")String location,@Param("shiftDate")String shiftDate,@Param("hour")String hour,@Param("tech")String tech);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLotPriorityRatioTrendChartData(@Param("fab")String fab,@Param("location")String location,@Param("startDate")String shiftDate,@Param("endDate")String hour,@Param("tech")String tech,@Param("priority")String priority);


    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getCrackEqpIdList();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getCrackInfoByLot(@Param("eqpId")String eqpId);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getCrackInfoByWafer(@Param("eqpId")String eqpId);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMeasurementSRTargetInfo();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> validMeasurementSRTargetInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void insertMeasurementSRTargetInfo(Map<String,Object>data);


    @UseDataSource("a3mfg")
    void updateMeasurementSRTargetInfo(Map<String,Object>data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveRatioInfo();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveRatioInfoByEQPG(@Param("module") String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSelfMoveRatioInfoByeqpId(@Param("eqpg") String eqpg);


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveAlarmRatioInfo();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveAlarmRatioInfoByEqpG();

    @UseDataSource("a3mfg")
    void insertA3SelfMoveAction(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveAlarmDetails(@Param("status") String status);

    @UseDataSource("a3mfg")
    void updateA3SelfMoveActionStatus(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveRatioBackupRatio();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getA3SelfMoveRatioTarget();

    @UseDataSource("a3mfg")
    Map<String,Object> getA3SelfMoveRatioAlarmSetting();

    @UseDataSource("a3mfg")
    void updateA3SelfMoveRatioAlarmSetting(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void updateA3SelfMoveRatioTarget(Map<String,Object> data);


    @UseDataSource("a3mfg")
    void insertA3SelfMoveRatioTarget(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void deleteA3SelfMoveRatioTarget();

    @UseDataSource("a3mfg")
    void insertA3SelfMoveRatioTargetList(@Param("list")List<MFGCriticalA3MoveSelfEQPGTargetVO> list,String username);
}
