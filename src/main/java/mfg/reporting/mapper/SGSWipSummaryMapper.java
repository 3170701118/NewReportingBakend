package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import mfg.reporting.dto.SGSWipSummaryDTO;
import mfg.reporting.dto.SGSWipDetailDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface SGSWipSummaryMapper {

    @UseDataSource("pdwmfg")
    List<SGSWipSummaryDTO> getSGSWipSummary(@Param("eqpid")String eqpid,@Param("neng")String neng);

    @UseDataSource("pdwmfg")
    List<SGSWipDetailDTO> getSGSWipDetail(@Param("tech")String tech,@Param("openo")String openo,@Param("sgsgroup")String sgsgroup,@Param("eqpid")String eqpid,@Param("neng")String neng,@Param("type")String type);

    @UseDataSource("pdwmfg")
    List<SGSWipSummaryDTO> getSGSWipTotalSummary(@Param("eqpid")String eqpid,@Param("neng")String neng);

    @UseDataSource("pdwmfg")
    List<SGSWipDetailDTO> getSGSWipTotalDetail(@Param("tech")String tech,@Param("openo")String openo,@Param("sgsgroup")String sgsgroup,@Param("eqpid")String eqpid,@Param("neng")String neng,@Param("type")String type);


    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getEqpIdList(@Param("fab")String fab);


}
