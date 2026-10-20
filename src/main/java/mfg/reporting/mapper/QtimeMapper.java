package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import mfg.reporting.pojo.QtimeOverDetailVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface QtimeMapper {
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getQtimeOverSummary(@Param("nengflag")String nengflag,@Param("module")String module,@Param("tech")String tech);

    @UseDataSource("pdwmfg")
    List<QtimeOverDetailVO> getQtimeOverDetail(@Param("nengflag")String nengflag,@Param("module")String module,@Param("tech")String tech,@Param("startdate")String startdate,@Param("enddate")String enddate,@Param("ifkeyin")String ifkeyin,@Param("targetmodule")String targetmodule);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getTechList();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getEditOption(@Param("type")String type);

    @UseDataSource("pdwmfg")
    void updateQtimeOverInfo(
            @Param("SHIFT_DATE")String SHIFT_DATE
            ,@Param("LOT_ID")String LOT_ID
            ,@Param("CURRENT_OPE_NO")String CURRENT_OPE_NO
            ,@Param("TYPE")String TYPE
            ,@Param("KEYVALUE")String KEYVALUE
            ,@Param("REMARK")String REMARK
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    void updateQtimeOverModule(
            @Param("SHIFT_DATE")String SHIFT_DATE
            ,@Param("LOT_ID")String LOT_ID
            ,@Param("CURRENT_OPE_NO")String CURRENT_OPE_NO
            ,@Param("KEYVALUE")String KEYVALUE
            ,@Param("REMARK")String REMARK
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    void updateQtimeOverReason(
            @Param("SHIFT_DATE")String SHIFT_DATE
            ,@Param("LOT_ID")String LOT_ID
            ,@Param("CURRENT_OPE_NO")String CURRENT_OPE_NO
            ,@Param("KEYVALUE")String KEYVALUE
            ,@Param("REMARK")String REMARK
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    void updateQtimeOverComment(
            @Param("SHIFT_DATE")String SHIFT_DATE
            ,@Param("LOT_ID")String LOT_ID
            ,@Param("CURRENT_OPE_NO")String CURRENT_OPE_NO
            ,@Param("KEYVALUE")String KEYVALUE
            ,@Param("REMARK")String REMARK
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    void updateQtimeMFG(
            @Param("SHIFT_DATE")String SHIFT_DATE
            ,@Param("LOT_ID")String LOT_ID
            ,@Param("CURRENT_OPE_NO")String CURRENT_OPE_NO
            ,@Param("KEYVALUE")String KEYVALUE
            ,@Param("REMARK")String REMARK
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getQtimeOverReason(@Param("type")String type);

    @UseDataSource("pdwmfg")
    void deleteQtimeOverReason(@Param("key") String key,@Param("type") String type);

    @UseDataSource("pdwmfg")
    void insertQtimeOverReason(@Param("key") String key,@Param("type") String type,@Param("updateUser") String updateUser);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getSumReport(@Param("lot_id")String lot_id,@Param("ope_no")String ope_no);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getQtimeDetail(@Param("lotid")String lotid,@Param("tri_openo")String tri_openo,@Param("tar_openo")String tar_openo,@Param("qtime_grage")String qtime_grage,@Param("qtime_spec")String qtime_spec);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getCommByOpe(@Param("lotid")String lotid,@Param("tri_openo")String tri_openo,@Param("tar_openo")String tar_openo,@Param("ope_no")String ope_no);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getCommByLot(@Param("lotid")String lotid,@Param("tri_openo")String tri_openo,@Param("tar_openo")String tar_openo);

    @UseDataSource("a3mfg")
    void updateQtimeOverMemo(
             @Param("lotid")String lotid
            ,@Param("tri_openo")String tri_openo
            ,@Param("tar_openo")String tar_openo
            ,@Param("ope_no")String ope_no
            ,@Param("memo")String memo
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("a3mfg")
    void updateQtimeOverComm(
            @Param("lotid")String lotid
            ,@Param("tri_openo")String tri_openo
            ,@Param("tar_openo")String tar_openo
            ,@Param("comment")String comment
            ,@Param("UPDATE_USER")String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getQtimefLOW(@Param("prodspec_ID")String prodspec_ID,@Param("flow_KEY")String flow_KEY,@Param("tri_openo")String tri_openo,@Param("tar_openo")String tar_openo,@Param("qtime_grage")String qtime_grage,@Param("qtime_spec")String qtime_spec);
}
