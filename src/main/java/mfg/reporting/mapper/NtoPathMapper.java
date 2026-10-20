package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.NTOPathMFGMaintainVO;
import mfg.reporting.pojo.NTOPathProdgDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NtoPathMapper {
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoProdgMaintain();

    @UseDataSource("pdwmfg")
    void deleteNtoProdgMaintain(@Param("prodg") String prodg, @Param("chipbody") String chipbody, @Param("updateUser") String updateUser);

    @UseDataSource("pdwmfg")
    void insertNtoProdgMaintain(@Param("prodg") String prodg,@Param("chipbody") String chipbody,@Param("updateUser") String updateUser);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getProdgList();

    @UseDataSource("pdwmfg")
    List<NTOPathMFGMaintainVO> getNtoMfgPathMaintain(@Param("prodg") String prodg);

    @UseDataSource("pdwmfg")
    void updateNtoPmc(@Param("prodg") String prodg,@Param("chipbody") String chipbody,@Param("fab") String fab,@Param("updateUser") String updateUser);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getEqpList(@Param("eqpg") String eqpg);

    @UseDataSource("pdwmfg")
    void updateNtoMfg(@Param("prodg") String prodg,@Param("chipbody") String chipbody,@Param("eqpg") String eqpg,@Param("eqplist") String eqplist,@Param("updateUser") String updateUser);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoModuleList(@Param("prodg") String prodg);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSdeptList(@Param("prodg") String prodg,@Param("deptid") String deptid);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSummaryModule();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSummaryModuleProdg();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSummaryModuleEqpg(@Param("prodg") String prodg,@Param("deptid") String deptid,@Param("sdept") String sdept);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSummaryProdg();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoProdgList();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSummaryProdgEqpg(@Param("prodg") String prodg);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNtoSummaryProdgTable();

    @UseDataSource("pdwmfg")
    List<NTOPathProdgDetailVO> getNtoProdgDetail(@Param("prodg") String prodg, @Param("chipbody") String chipbody);

    @UseDataSource("pdwmfg")
    void updateInputFlag();

    @UseDataSource("pdwmfg")
    void updateInputFlagOther();
}
