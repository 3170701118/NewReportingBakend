package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.PullInWebVO;
import mfg.reporting.pojo.PullInwebPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PullInWebMapper {

    @UseDataSource("pdwmfg")
    List<PullInwebPO> getPullInWebData(@Param("shiftDate") String shiftDate,
                                        @Param("functionName") String functionName
    );


    @UseDataSource("a3mfg")
    List<Map<String,Object>> testuploadPullInWebData(List<PullInWebVO> list, @Param("updateUser")String updateUser ,@Param("source")String source);

    @UseDataSource("pdwmfg")
    void insertPullInWebUpload(List<PullInWebVO> list,  @Param("updateUser")String updateUser,@Param("source")String source);

    @UseDataSource("pdwmfg")
    void insertpullInFlowCTInfo(List<PullInWebVO> list, @Param("updateUser")String updateUser , @Param("source")String source);

    @UseDataSource("pdwmfg")
    void deletePullInLot(@Param("lotIdList") List<String> lotIdList , @Param("functionName") String functionName);

    @UseDataSource("pdwmfg")
    void deletePullInFlowCTInfo(@Param("lotIdList") List<String> lotIdList, @Param("functionName") String functionName);

    @UseDataSource("pdwmfg")
    void setTopLotInfo(@Param("lotIdList") String lotIdList, @Param("updateUser") String updateUser, @Param("functionName") String functionName);


    @UseDataSource("pdwmfg")
    List<Map<String, Object>> getDelayLotSummary(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<Map<String, Object>> getActivetabNamesByFab(@Param("fab") String fab);

    @UseDataSource("pdwmfg")
    List<Map<String, Object>> getAllTabNameByFab(@Param("fab") String fab);

    @UseDataSource("pdw")
    void updateActiveTabInfo(@Param("tabName") String tabName, @Param("fab") String fab, @Param("active") String active, @Param("rank") String rank, @Param("label") String label, @Param("updateUser") String updateUser);

    @UseDataSource("pdwmfg")
    List<Map<String, Object>> getDelayLotDetails (@Param("fab") String fab, @Param("date") String date, @Param("module") String module, @Param("functionName") String functionName);

    @UseDataSource("pdw")
    void updateDelayLotComment(@Param("data")Map<String,Object> data);

    @UseDataSource("pdwmfg")
    void deleteTableDetailsSummary(@Param("source") String source);

    @UseDataSource("pdwmfg")
    void refreshTabDetailsSummary(@Param("source") String source);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getPullinWebMonitorByEQPG(@Param("functionName") String functionName);

}
