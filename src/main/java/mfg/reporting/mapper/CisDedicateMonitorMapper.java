package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CisDedicateMonitorMapper {

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> selectCISDedicateTableList();

    @UseDataSource("pdwmfg")
    Map<String,Object> getEqpInfoByEqpId(@Param("eqpId") String eqpId);

    @UseDataSource("pdw")
    void insertEqpInfoForCISMonitor(Map<String,Object> data);

    @UseDataSource("pdw")
    void deleteEqpInfoForCISMonitor(@Param("eqpId") String eqpId);


    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getN1A1EqpCISDedicateDetail(@Param("eqpId") String eqpId,@Param("tech") String tech);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getN1A1ChamberCISDedicateDetail(@Param("eqpId") String eqpId,@Param("tech") String tech);

    @UseDataSource("a3pdw")
    List<Map<String,Object>> getN1A3EqpCISDedicateDetail(@Param("eqpId") String eqpId,@Param("tech") String tech);

    @UseDataSource("a3pdw")
    List<Map<String,Object>> getN1A3ChamberCISDedicateDetail(@Param("eqpId") String eqpId,@Param("tech") String tech);

    @UseDataSource("pdwmfg")
    void insertCISDedicateMonitorHis();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getFP_FpolyForL111GFFP10mailN1A1();

    @UseDataSource("a3pdw")
    List<Map<String,Object>> getFP_FpolyForL111GFFP10mailN1A3();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getProdgOPE_UF_Inhibit_mail_N1A1();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getProdgOPE_UF_Inhibit_mail_N1A3();
}
