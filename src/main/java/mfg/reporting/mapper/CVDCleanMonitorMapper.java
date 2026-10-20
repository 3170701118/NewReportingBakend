package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CVDCleanMonitorMapper {

    @UseDataSource("pdwmfg")
    List<String> getN1A1ToolIdList();

    @UseDataSource("a3pdw")
    List<String> getN1A3ToolIdList();

    @UseDataSource("a3mfg")
    void insertFDCMoveInfo(@Param("list") List<Map<String,Object>> list ,@Param("fab") String fab);

    @UseDataSource("a3mfg")
    void deleteFDCMoveInfo(@Param("fab") String fab);

}
