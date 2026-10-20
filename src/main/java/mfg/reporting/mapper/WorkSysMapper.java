package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkSysMapper {

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getOpFabSum(@Param("ym") String ym, @Param("deptid")String deptid, @Param("sdeptname")String sdeptname);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getOpSummary(@Param("ym") String ym);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getWorkModuleSummary(@Param("ym") String ym, @Param("deptid")String deptid, @Param("sdeptname")String sdeptname, @Param("aclass")String aclass);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getAbnormalDetail(@Param("ym") String ym, @Param("deptid")String deptid, @Param("sdeptname")String sdeptname, @Param("aclass")String aclass);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getLeaveInfo(@Param("deptid")String deptid,@Param("sdeptname")String sdeptname);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getWorkModule(@Param("ym") String ym, @Param("sdeptname")String sdeptname, @Param("aclass")String aclass);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getOpDefaultFab(@Param("deptid") String deptid, @Param("sdeptname")String sdeptname, @Param("aclass")String aclass);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getWorkModuleOver(@Param("ym") String ym, @Param("sdeptname")String sdeptname, @Param("aclass")String aclass);

    @UseDataSource("pdwmfg")
    void updateOpStatus(
            @Param(value = "YM") String YM
            ,@Param(value = "ACLASS") String ACLASS
            ,@Param(value = "EMPID") String EMPID
            ,@Param(value = "UPDATE_STATUS") String UPDATE_STATUS
            ,@Param(value = "FAB_TYPE") String FAB_TYPE
            ,@Param(value = "UPDATE_USER") String UPDATE_USER
            );

    @UseDataSource("pdwmfg")
    void updateOpDefault(
            @Param(value = "DEPT_ID") String DEPT_ID
            ,@Param(value = "SDEPT_CNAME") String SDEPT_CNAME
            ,@Param(value = "EMP_ID") String EMP_ID
            ,@Param(value = "CNAME") String CNAME
            ,@Param(value = "FAB_TYPE") String FAB_TYPE
            ,@Param(value = "UPDATE_USER") String UPDATE_USER
    );

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getYmAclass();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getClassByShiftDate(@Param("shift_date") String shift_date);


    /**
     * 默认岗位维护module list
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getModuleList(@Param("deptid") String deptid);

    /**
     * 获取月份List
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getYMList();


    /**
     * 出缺勤系统制造部副理级以上及管理员权限
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getModuleLevel(@Param("deptid") String deptid);

    /**
     * 出缺勤系统制造部生产课权限
     * @param sdept_cname
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getModuleProduce(@Param("deptid") String deptid,@Param("sdept_cname") String sdept_cname);

    /**出缺勤系统其他权限
     *
     * @return
     */
    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getModuleOther();

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getLeaveDetail(@Param("shift_date") String shift_date,@Param("deptid") String deptid,@Param("sdept_cname") String sdept_cname);

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getDoorDataByEmpid(@Param("empid") String empid,@Param("ym") String ym,@Param("shift") int shift);


}
