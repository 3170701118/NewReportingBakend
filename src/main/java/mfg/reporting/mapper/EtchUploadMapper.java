package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.EtchMaintainInputPlanDTO;
import mfg.reporting.dto.EtchMaintainStdTcDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EtchUploadMapper {

    @UseDataSource("pdw")
    void deleteEtchStdTcAll();

    @UseDataSource("pdw")
    void insertEtchStdTcBatch(@Param("list")List<EtchMaintainStdTcDTO> list);

    @UseDataSource("pdw")
    void deleteInputPlanDailyNewForETCHAll();

    @UseDataSource("pdw")
    void insertInputPlanDailyNewForETCHBatch(@Param("list")List<EtchMaintainInputPlanDTO> list);
}
