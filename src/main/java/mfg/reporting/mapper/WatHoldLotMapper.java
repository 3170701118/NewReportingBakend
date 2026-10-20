package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.WatHoldLot;
import mfg.reporting.pojo.WatHoldLotChart;
import mfg.reporting.pojo.WatHoldLotSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @location : /photo/watHoldLot
 * @author : gszhang
 */
@Mapper
public interface WatHoldLotMapper {
    @UseDataSource("pdwmfg")
    List<WatHoldLot> getWatHoldLotList(@Param("module")String module,@Param("type")String type);

    @UseDataSource("pdw")
    void mergeWatHoldLot(@Param("param")Map<String, Object> param);

    @UseDataSource("pdwmfg")
    List<WatHoldLotSummary> getWatHoldLotSummaryTable();

    @UseDataSource("pdwmfg")
    List<WatHoldLotChart> getWatHoldLotSummaryChart();
}
