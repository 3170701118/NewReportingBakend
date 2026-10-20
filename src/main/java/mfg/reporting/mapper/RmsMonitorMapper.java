package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.pojo.RMSMonitorDetailsPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RmsMonitorMapper {

    @UseDataSource("a3mfg")
    List<Map<String, Object>> selectRmsMonitorData();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> selectRmsSummaryData(@Param("productStatusList") List<String> productStatusList ,@Param("fab") String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsSummaryTableData(@Param("module") String module,@Param("eqpg") List<String> eqpg ,@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<RMSMonitorDetailsPO> getRmsDetailTableData(@Param("fab") String fab,@Param("tech") String tech,@Param("type")String type, @Param("module") String module, @Param("productStatusList") List<String> productStatusList,@Param("eqpg") List<String>eqpg);

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> geteqpGByModule(@Param(value="module") String module ,@Param(value="fab") String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsDetailByEQPRecipe(
            @Param(value="fab") String fab
            ,@Param(value="eqpg") String eqpg
            ,@Param(value = "eqpId") String eqpId
            ,@Param(value = "recipe") String recipe
            ,@Param(value="status") String  status );

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getSumamryTrendChart(@Param(value="fab")String fab,@Param(value="tech")String tech,@Param("module")String module,@Param("productStatusList") List<String> productStatusList);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getTrendChartTech();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsModuleSummaryInfo(@Param("module")String module ,@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsNGSummaryInfo(@Param("fab") String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsNGDetailsInfo(@Param("fab") String fab,@Param("module")String module,@Param("shiftDate")String shiftDate);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsNgWeekGroupInfo(@Param("fab") String fab,@Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsRecipeSummaryGroupTotalInfo(@Param("fab")String fab);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsRecipeSummaryInfo(@Param("fab") String fab,@Param("module")String module);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRmsRecipeDetailsInfo(@Param("shiftDate")String shiftDate,@Param("module")String module ,@Param("fab")String fab);
}
