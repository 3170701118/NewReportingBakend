package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CommonSelectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OcsMonitorMapper {

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorTableSummary(@Param("fab")String fab,@Param("shiftDate") String shiftDate,@Param("daynight") String daynight);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorPlanDetails(
            @Param("fab")String fab
            ,@Param("shiftDate") String shiftDate
            ,@Param("daynight") String daynight
            ,@Param("module") String module
            ,@Param("type") String type
    );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorStatusDetails(
            @Param("fab")String fab
            ,@Param("shiftDate") String shiftDate
            ,@Param("daynight") String daynight
            ,@Param("module") String module
            ,@Param("type") String type
    );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOCSMonitorNGChartNew(
            Map<String,Object> data
    );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorNGChartByEQPG(
            @Param("fab")String fab
            ,@Param("shiftDate") String shiftDate
            ,@Param("daynight") String daynight
            ,@Param("module") String module
            ,@Param("mode") String mode
    );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorNGChartByeqpId(
            @Param("fab")String fab
            ,@Param("shiftDate") String shiftDate
            ,@Param("daynight") String daynight
            ,@Param("testFor") String testFor
            ,@Param("eqpg") String eqpg
    );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getOcsMonitorNGChartByDummy(
            @Param("fab")String fab
            ,@Param("shiftDate") String shiftDate
            ,@Param("daynight") String daynight
            ,@Param("testFor") String testFor
            ,@Param("eqpId") String eqpId
    );


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getNgTrendChartSummary(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> geteqpgList(@Param("fab")String fab,@Param("module")String module);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getpurposeList(@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getNgDetailsByLot(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getCompeteRatioDaily(@Param("fab")String fab);


    @UseDataSource("a3mfg")
    List<Map<String,Object>>getStayTimetrendChart(Map<String,Object> data);
}
