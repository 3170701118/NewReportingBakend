package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.TFClnSpecialRuleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@Mapper
public interface FDCMonitorMapper {

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getTFEqpgFDCSummary(@Param("startDate") String startDate,@Param("endDate") String endDate );


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getTFEqpgRecipeFDCSummary(@Param("eqpg")String eqpg,@Param("date")String date,@Param("specialFlag")String specialFlag);


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getTFToolRecipeFDCSUmmary(@Param("eqpg")String eqpg,@Param("date")String date,@Param("specialFlag")String specialFlag,@Param("recipe")String recipe);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getClnRecipeChartMonitor(@Param("eqpg")String eqpg,@Param("date")String date,@Param("toolName")String toolName,@Param("recipe") String recipe,@Param("specialFlag")String specialFlag);


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getTFClnSpecialFlagMaintainInfo();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getTFClnFilterDataMaintainInfo();

    @UseDataSource("a3mfg")
    void mergeTFClnSpecialFlagMaintainInfo(@Param("list")List<TFClnSpecialRuleDTO> list, @Param("UPDATE_USER") String UPDATE_USER);

    @UseDataSource("a3mfg")
    void insertTFClnFilterDataMaintainInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void deleteTFClnSpecialFlagMaintainInfo(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void delteTFClnFilterDataMaintainInfo(Map<String,Object> data);


}
