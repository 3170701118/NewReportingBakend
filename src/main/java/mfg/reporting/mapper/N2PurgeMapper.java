package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.N2PurgeInfoHourlyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface N2PurgeMapper {

    /**
     * @return list of N2PurgeInfoHourlyPO
     */
    @UseDataSource("pdwmfg")
    List<N2PurgeInfoHourlyPO> getN2PurgeInfoHourly(@Param("location") String location);

    @UseDataSource("pdwmfg")
    List<N2PurgeInfoHourlyPO> getN2PurgeInfoHourly2(@Param("location") String location);


    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getN2PurgetSummaryInfo(@Param("location") String location);

    @UseDataSource("pdw")
    void updateFcstDoneTimeByLotId(@Param("lotId") String lotId, @Param("fcstDoneTime") String fcstDoneTime,@Param("updateUser") String updateUser);
}
