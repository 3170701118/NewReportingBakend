package mfg.reporting.mapper;


import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FoupMonitorMapper {

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getEmptyFoupDailyData(String fab);
}
