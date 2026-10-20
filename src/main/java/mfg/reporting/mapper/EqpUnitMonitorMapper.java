package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.pojo.EqpUnitMonitorPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EqpUnitMonitorMapper {

    @UseDataSource("pdwmfg")
    List<EqpUnitMonitorPO> getEqpUnitMonitorInfo(@Param("module") String module,@Param("eqpG") String eqpG,@Param("unitFlag")String unitFlag,@Param("unitJudge")String unitJudge );

    @UseDataSource("pdwmfg")
    List<CommonSelectDTO> getEQPGroupListByModule(@Param("module") String module);

    @UseDataSource("pdwmfg")
    List<EqpUnitMonitorPO> getHstEQPUnitMonitorInfo(@Param("eqpId")String eqpId,@Param("unitId")String unitId,@Param("unitFlag")String unitFlag ,@Param("startDate")String startDate,@Param("endDate")String endDate);

    @UseDataSource("pdw")
    void updateFcomment(@Param("eqpId")String eqpId,@Param("unitId")String unitId,@Param("occurTime")String occurTime,@Param("fcomment")String fcomment,@Param("userName")String userName);

}
