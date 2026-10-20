package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.MakeUpPlanVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OutputReviewMapper {

    @UseDataSource("a3mfg")
    public List<Map<String,Object>> getOutputSummaryByeqpg(@Param("fab")String fab,@Param("eqpg")String eqpg,@Param("month")String month);

    @UseDataSource("a3mfg")
    public List<Map<String,Object>> getOutputSummary(@Param("fab") String fab,@Param("month")String month);

    @UseDataSource("a3mfg")
    public List<Map<String,Object>> getMaintaineqpg(@Param("module") String module,@Param("FLAG")String FLAG);

    @UseDataSource("a3mfg")
    public List<Map<String,Object>> testMaintaineqpg(@Param("MODULE")String MODULE,@Param("EQP_G") String EQP_G);

    @UseDataSource("a3mfg")
    void insertMaintaineqpg(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void updateMaintainEqpg(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void deleteMaintaineqpg(@Param("EQP_G") String EQP_G, @Param("MODULE") String MODULE);


    @UseDataSource("a3mfg")
    public List<Map<String,Object>> getkeyinSummary(@Param("eqpg")String eqpg,@Param("month")String month);

    @UseDataSource("a3mfg")
    void updateCapQTY(Map<String,Object> map);

    @UseDataSource("a3mfg")
    void updateReqQTY(Map<String,Object> map);

    @UseDataSource("a3mfg")
    Map<String,Object> getComment(@Param("eqpg")String eqpg,@Param("curMonth") String curMonth);

    @UseDataSource("a3mfg")
    void updateComment(Map<String,Object> map);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getMakeUpPlanList(@Param("month")String month);

    @UseDataSource("a3mfg")
    void insertMakeUpplan(@Param("list") List<MakeUpPlanVO> list , @Param("username")String username);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getModuleeqpgRequstTarget(@Param("EQP_G")String EQP_G,@Param("month")String month);

    @UseDataSource("a3mfg")
    void updateModuleeqpgMenuTarget(Map<String,Object> map);
}
