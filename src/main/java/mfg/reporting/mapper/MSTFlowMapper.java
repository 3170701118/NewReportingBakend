package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import mfg.reporting.dto.MstFlowSummaryDTO;
import mfg.reporting.dto.MstFlowWipDTO;
import mfg.reporting.dto.MstFlowMoveByProdgDTO;
import mfg.reporting.dto.MstFlowMoveByEqpgDTO;
import mfg.reporting.dto.MstFlowLotOpeHsDTO;
import mfg.reporting.dto.MstFlowQtimeDTO;
import mfg.reporting.dto.MstFlowSamplingDTO;
import mfg.reporting.dto.MstFlowPathDTO;
import mfg.reporting.dto.MstFlowDybDTO;
import mfg.reporting.dto.MstFlowSgsDTO;
import java.util.List;
import java.util.Map;

@Mapper
public interface MSTFlowMapper {

    @UseDataSource("pdwmfg")
    List<MstFlowSummaryDTO> getMstFlowSummary(@Param("tech")String tech,@Param("prodg")String prodg,@Param("module")String module,@Param("eqpg")String eqpg,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowWipDTO> getWipDetailList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("state")String state,@Param("openo")String openo,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowWipDTO> getBackupWipDetailList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("state")String state,@Param("openo")String openo,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowMoveByProdgDTO> getMoveByProdgList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("date")String date,@Param("openo")String openo,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowMoveByEqpgDTO> getMoveByEqpgList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("date")String date,@Param("openo")String openo,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowLotOpeHsDTO> getLotOpeHSList(@Param("lotid")String lotid,@Param("prodspecid")String prodspecid);

    @UseDataSource("pdwmfg")
    List<MstFlowQtimeDTO> getQtimeDetailList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("openo")String openo);

    @UseDataSource("pdwmfg")
    List<MstFlowSamplingDTO> getSamplingRateDetailList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("openo")String openo);

    @UseDataSource("pdwmfg")
    List<MstFlowPathDTO> getpathDetailList(@Param("prodg")String prodg,@Param("prodspecid")String prodspecid,@Param("openo")String openo,@Param("mainpdid")String mainpdid);

    @UseDataSource("pdwmfg")
    List<MstFlowDybDTO> getDybLotDetailList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowSgsDTO> getSgsEqpList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("openo")String openo,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<MstFlowWipDTO> getSgsWipDetailList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("sgseqp")String sgseqp,@Param("openo")String openo,@Param("neng")String neng,@Param("view")String view);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getTechList();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getProdgList(@Param("tech")String tech);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getEqpgList(@Param("tech")String tech,@Param("prodg")String prodg,@Param("module")String module);





}
