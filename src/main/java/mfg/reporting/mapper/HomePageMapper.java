package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.MenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HomePageMapper {

    @UseDataSource("a3mfg")
     List<Map<String,Object>> getClickRateSummary(@Param("weekType") String weekType, @Param("rankType")String rankType);

    @UseDataSource("pdwmfg")
     List<MenuInfo> getMenuInfolist(@Param("menuType") String menuType);
}
