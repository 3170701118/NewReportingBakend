package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonSelectValueMapper {

    /**
     * ROUTE NAME 与menu Name 的对应关系
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMenuList();

    /**
     * email 与 cname 的对应关系
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getFunctionOwnerList(@Param("query") String query);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getAllCnames();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getAllSdepts();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getAllDepts();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getAllSDeptList();
}
