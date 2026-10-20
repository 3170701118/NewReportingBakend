package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CommonSelectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SRMonitorMapper {

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSRMonitorSummary(@Param("eqpgList") List<String> eqpgList);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getSRMonitorEQPG();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSRMonitorChartByEQPG(@Param("eqpg") String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSRMonitorDetailBySuffix(@Param("eqpg") String eqpg);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSRMOnitorDetailByOPENO(@Param("eqpg") String eqpg,@Param("prodspecId") String prodspecId, @Param("fab") String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSRMOnitorAlarmInfo();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPSRCheckRateInfo();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> SRMonitorAlarmList();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> SRMonitorAlarmDeptNullsList();
}
