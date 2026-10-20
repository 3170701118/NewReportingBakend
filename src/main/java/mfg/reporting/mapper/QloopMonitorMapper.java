package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface QloopMonitorMapper {

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getControlSummaryData(@Param("fab") String fab,@Param("nengFlag") String nengFlag);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPullSummaryData(@Param("fab") String fab,@Param("nengFlag") String nengFlag);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPushSummaryData(@Param("fab") String fab,@Param("nengFlag") String nengFlag);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getControlQloopList(Map<String,Object> data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPullQloopList(Map<String,Object>data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getPushQloopList(Map<String,Object>data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getLotListByQloopList(Map<String,Object>data);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getAbnormalLotDetail(Map<String,Object>data);

    @UseDataSource("a3mfg")
    void updateQtimeAbnormal(
            @Param("location")String location
            ,@Param("lot_id")String lot_id
            ,@Param("PRODSPEC_ID")String PRODSPEC_ID
            ,@Param("ope_no")String ope_no
            ,@Param("type")String type
            ,@Param("reason")String reason
            ,@Param("empid")String empid
            ,@Param("UPDATE_USER")String UPDATE_USER
            ,@Param("nengFlag")String nengFlag
    );

}


