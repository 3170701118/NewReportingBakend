package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.CapacityCommentDTO;
import mfg.reporting.dto.CapacityEqpgInfoDTO;
import mfg.reporting.dto.CapacityProdMixDTO;
import mfg.reporting.dto.CommonSelectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonCapacityMapper {

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> getModeTypeList();

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getProductMixInfo(@Param("mode") String mode);

    @UseDataSource("a3mfg")
    void deleteProductMix(@Param("mode") String mode);

    @UseDataSource("a3mfg")
    void insertProductMixList(@Param("list") List<CapacityProdMixDTO> list , @Param("username") String username ,@Param("mode") String mode);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> geteqpgInfo(@Param("mode") String mode);

    @UseDataSource("a3mfg")
    void mergeEqpgInfo(@Param("list") List<CapacityEqpgInfoDTO> list,@Param("mode") String mode,@Param("username") String username);


    @UseDataSource("a3mfg")
    List<Map<String,Object>> geteqpgCapacitySummary(@Param("mode") String mode);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> geteqpGCapacitySummaryByFabSelf(@Param("mode") String mode,@Param("fab")String fab);

    @UseDataSource("a3mfg")
    void updateEqpgFComment(Map<String,Object> data);

    @UseDataSource("a3mfg")
    void updateEqpgSummaryComment(
            @Param("eqpg") String eqpg
            , @Param("eqpgComment") String eqpgComment
            , @Param("eqpgComment2") String eqpgComment2
            , @Param("mode") String mode
            , @Param("username") String username
    );


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getRCConstraints();

    @UseDataSource("a3mfg")
    void insertCapacityRCConstraints(Map<String,Object>data);

    @UseDataSource("a3mfg")
    void insertCapacityRCConstraintsBatch(@Param("list") List<Map<String,Object>> list);

    @UseDataSource("a3mfg")
    void deleteCapacityRCConstraints(@Param("CONSTRAINTS_ID") int CONSTRAINTS_ID);

    @UseDataSource("a3mfg")
    void updateCapacityRCConstraints(Map<String,Object>data);

    @UseDataSource("a3mfg")
    void updateCapacityComment(@Param("list")List<CapacityCommentDTO> list,@Param("source") String source,@Param("username") String username  );


    @UseDataSource("a3mfg")
    void asynceqpgSettingAfterUploadProductMix(@Param("mode")String mode,@Param("username")String username);

    @UseDataSource("a3mfg")
    List<CommonSelectDTO> geteqpgCapacityUploadList(@Param("mode")String mode);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getEqpgCommonCapacityDetails(@Param("mode")String mode,@Param("eqpg")String eqpg);
}
