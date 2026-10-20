package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.DownSamplingPEJurgePO;
import mfg.reporting.pojo.DownSamplingSRTargetPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DownSamplingMapper {

    @UseDataSource("pdwmfg")
    List<Map<String,String>> getMainTainEqpGroup();

    @UseDataSource("pdwmfg")
    List<DownSamplingPEJurgePO> getPEJurgeList(@Param("eqpG") String eqpG);

    @UseDataSource("pdwmfg")
    void insertPEJurgeRecord(DownSamplingPEJurgePO po);

    @UseDataSource("pdwmfg")
    List<DownSamplingPEJurgePO> getPEJurgeRecordHs(@Param("eqpG") String eqpG);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getDownSamplingTargetSrList(@Param("tech") String tech, @Param("opeNo") String opeNo);
    
    @UseDataSource("pdwmfg")
    void insertPEJurgeRecordList(List<DownSamplingPEJurgePO> list);

    @UseDataSource("pdwmfg")
    void insertStdSRTargetList(List<DownSamplingSRTargetPO> list);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getDownSamplingMoveDetails(DownSamplingPEJurgePO po);
}
