package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.DummyUploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DummyMonitorAlarmMapper {

    /**
     * 插入数据
     * @param data
     */
    @UseDataSource("a3mfg")
    void insertDummyMonitorAlarm(Map<String,Object>data);

    /**
     * 批量插入数据
     */
    @UseDataSource("a3mfg")
    void insertDummyMonitorAlarmList(List<DummyUploadVO> list);

    /**
     * 更新
     */
    @UseDataSource("a3mfg")
    void updateDummyMonitorAlarmActiveFlag(@Param("fab")String fab,@Param("purpose")String purpose,@Param("stdf")float stdf,@Param("activeFlag") String activeFlag,@Param("keyComment")String keyComment,@Param("updateUser") String updateUser);

    /**
     * 删除
     */
    @UseDataSource("a3mfg")
    void deleteDummyMonitorAlarm(@Param("fab")String fab,@Param("purpose")String purpose);

    /**
     * 查询
     */
    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDummyMonitorAlarm(@Param("fab")String fab,@Param("module")String module,@Param("purpose")String purpose);


    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDummyMonitorAlarmList(List<DummyUploadVO> list);

    @UseDataSource("a3mfg")
    List<Map<String,Object>> getDummyMailAlarmList();

}
