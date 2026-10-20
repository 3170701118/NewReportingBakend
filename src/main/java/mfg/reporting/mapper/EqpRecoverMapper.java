package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EqpRecoverMapper {

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getFcstRecoverChart(@Param("fab") String fab, @Param("module")String module);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getRecoverRatioByModule();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getRecoverRatioByMFGModule();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getRecoverRatioByEQPG(@Param("module") String module);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getRecoverTableDetails(@Param("module") String module);

    @UseDataSource("pdwmfg")
    void updateACTRecoverTime(
            @Param("eqpId") String eqpId
            ,@Param("actReturnTime") String actReturnTime
            ,@Param("updateUser") String updateUser
            ,@Param("fcomment")String fcomment
    );

    @UseDataSource("pdw")
    List<Map<String,Object>> getProcessRate();

    @UseDataSource("pdw")
    void updateProcessRate(@Param("key1")String key1, @Param("key2")String key2, @Param("key3")String key3);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getFcstRecoverChartByPri(@Param("module") String module);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseData();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportTimeData();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>>  getExportdataType();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseDataByModule(@Param("pri") String pri,@Param("curDate") String curDate);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseTimeByModule(@Param("pri") String pri,@Param("curDate") String curDate);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseDataByEQPG(@Param("pri") String pri,@Param("curDate") String curDate,@Param("module") String module);
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseTimeByEQPG(@Param("pri") String pri,@Param("curDate") String curDate,@Param("module") String module);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseDataByEqpId(@Param("pri") String pri,@Param("curDate") String curDate,@Param("eqpg") String eqpg);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getExportBaseTimeByEqpId(@Param("pri") String pri,@Param("curDate") String curDate,@Param("eqpg") String eqpg);

}
