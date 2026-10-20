package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MitSufferMapper {
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMitSummary(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMitSummaryNon(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getShiftDate();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMitAllSummary(@Param("fab") String fab);
}
