package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.CommonSendEmail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@Mapper
public interface SendEmailMapper {

    @UseDataSource("pdwmfg")
    void callSendEmail(CommonSendEmail data);

    @UseDataSource("pdwmfg")
    void callSendEmailAddCC(CommonSendEmail data);

    @UseDataSource("pdw")
    List<Map<String,Object>> getReworkLotList();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getPA20Recivers(@Param("deptid") String deptid);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMfgRecivers(@Param("deptid") String deptid);

    @UseDataSource("pdwmfg")
    void insertNtoMailLog(@Param("type") String type,@Param("prodg") String prodg,@Param("deptid") String deptid);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMfgToModule();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getOver200ToModule();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getOver200ToModuleDetail(@Param("prodg") String prodg,@Param("deptid") String deptid);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMaintainToModule();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMaintainToModuleDetail(@Param("prodg") String prodg,@Param("deptid") String deptid);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getMaintainEQPGToOPD();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getNotMaintainToOwner();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getAutoInsertProdgList();
}
