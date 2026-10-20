package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.WorkSysService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhangshuxun
 */
@RestController
@RequestMapping("/workSys")
@Slf4j
public class WorkSysController {

    @Resource
    public WorkSysService workSysService;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 查询各课出缺勤summary状况
     * @param ym
     * @param sdeptname
     * @param aclass
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getWorkModuleSummary")
    public ResponseResult<List<Map<String,Object>>> getWorkModuleSummary(
              @RequestParam(value="ym") String ym
            , @RequestParam(value="deptid") String deptid
            , @RequestParam(value="sdeptname") String sdeptname
            , @RequestParam(value="aclass") String aclass

            ){
        try{
            List<Map<String,Object>> list = workSysService.getWorkModuleSummary(ym,deptid,sdeptname,aclass);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询WorkModuleSummary 失败",e);
            return  ResponseResult.fail("查询WorkModuleSummary 失败");
        }
    }


    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getOpFabSum")
    public ResponseResult<List<Map<String,Object>>> getOpFabSum(
              @RequestParam(value="ym") String ym
            , @RequestParam(value="deptid") String deptid
            , @RequestParam(value="sdeptname") String sdeptname

    ){
        try{
            List<Map<String,Object>> list = workSysService.getOpFabSum(ym,deptid,sdeptname);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getOpFabSum失败",e);
            return  ResponseResult.fail("查询getOpFabSum失败");
        }
    }

    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getOpSummary")
    public ResponseResult<List<Map<String,Object>>> getOpSummary(
            @RequestParam(value="ym") String ym
    ){
        try{
            List<Map<String,Object>> list = workSysService.getOpSummary(ym);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getOpSummary失败",e);
            return  ResponseResult.fail("查询getOpSummary失败");
        }
    }

    /**
     * 查询异常人员详情
     * @param ym
     * @param sdeptname
     * @param aclass
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getAbnormalDetail")
    public ResponseResult<List<Map<String,Object>>> getAbnormalDetail(
              @RequestParam(value="ym") String ym
            , @RequestParam(value="deptid") String deptid
            , @RequestParam(value="sdeptname") String sdeptname
            , @RequestParam(value="aclass") String aclass
    ){
        try{
            List<Map<String,Object>> list = workSysService.getAbnormalDetail(ym,deptid,sdeptname,aclass);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询AbnormalDetail 失败",e);
            return  ResponseResult.fail("查询AbnormalDetail 失败");
        }
    }

    /**
     * 查询请假人员资料
     * @param sdeptname
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getLeaveInfo")
    public ResponseResult<List<Map<String,Object>>> getLeaveInfo(
             @RequestParam(value="deptid") String deptid
            ,@RequestParam(value="sdeptname") String sdeptname
    ){
        try{
            List<Map<String,Object>> list = workSysService.getLeaveInfo(deptid,sdeptname);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询LeaveInfo 失败",e);
            return  ResponseResult.fail("查询LeaveInfo 失败");
        }
    }

    /**
     * 查询各课当班详情
     * @param ym
     * @param sdeptname
     * @param aclass
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getWorkModule")
    public ResponseResult<List<Map<String,Object>>> getWorkModule(
            @RequestParam(value="ym") String ym
            , @RequestParam(value="sdeptname") String sdeptname
            , @RequestParam(value="aclass") String aclass

    ){
        try{
            List<Map<String,Object>> list = workSysService.getWorkModule(ym,sdeptname,aclass);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询WorkModule 失败",e);
            return  ResponseResult.fail("查询WorkModule 失败");
        }
    }

    /**
     * 查询各课加班详情
     * @param ym
     * @param sdeptname
     * @param aclass
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getWorkModuleOver")
    public ResponseResult<List<Map<String,Object>>> getWorkModuleOver(
            @RequestParam(value="ym") String ym
            , @RequestParam(value="sdeptname") String sdeptname
            , @RequestParam(value="aclass") String aclass

    ){
        try{
            List<Map<String,Object>> list = workSysService.getWorkModuleOver(ym,sdeptname,aclass);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询WorkModuleOver 失败",e);
            return  ResponseResult.fail("查询WorkModuleOver 失败");
        }
    }


    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getOpDefaultFab")
    public ResponseResult<List<Map<String,Object>>> getOpDefaultFab(
              @RequestParam(value="deptid") String deptid
            , @RequestParam(value="sdeptname") String sdeptname
            , @RequestParam(value="aclass") String aclass

    ){
        try{
            List<Map<String,Object>> list = workSysService.getOpDefaultFab(deptid,sdeptname,aclass);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getOpDefaultFab失败",e);
            return  ResponseResult.fail("查询getOpDefaultFab失败");
        }
    }


    /**
     * 修改人员状态
     * @param YM
     * @param ACLASS
     * @param EMPID
     * @param UPDATE_STATUS
     * @param FUNCTIONID
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "update")
    @PostMapping("/updateOpStatus")
    public ResponseResult<Map<String, Object>> updateOpStatus(
            @RequestParam(value = "YM") String YM
            ,@RequestParam(value = "ACLASS") String ACLASS
            ,@RequestParam(value = "EMPID") String EMPID
            ,@RequestParam(value = "UPDATE_STATUS") String UPDATE_STATUS
            ,@RequestParam(value = "FAB_TYPE") String FAB_TYPE
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                workSysService.updateOpStatus(YM,ACLASS,EMPID,UPDATE_STATUS,FAB_TYPE,UPDATE_USER);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }

    /**
     * 修改人员默认岗位
     * @param DEPT_ID
     * @param SDEPT_CNAME
     * @param EMP_ID
     * @param CNAME
     * @param FAB_TYPE
     * @param FUNCTIONID
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "update")
    @PostMapping("/updateOpDefault")
    public ResponseResult<Map<String, Object>> updateOpDefault(
             @RequestParam(value = "DEPT_ID") String DEPT_ID
            ,@RequestParam(value = "SDEPT_CNAME") String SDEPT_CNAME
            ,@RequestParam(value = "EMP_ID") String EMP_ID
            ,@RequestParam(value = "CNAME") String CNAME
            ,@RequestParam(value = "FAB_TYPE") String FAB_TYPE
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                workSysService.updateOpDefault(DEPT_ID,SDEPT_CNAME,EMP_ID,CNAME,FAB_TYPE,UPDATE_USER);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }

    /**
     * 获取当前的日期班别
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getYmAclass")
    public ResponseResult<List<Map<String,Object>>> getYmAclass(){
        try{
            List<Map<String,Object>> list = workSysService.getYmAclass();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询YmAclass 失败",e);
            return  ResponseResult.fail("查询YmAclass失败");
        }
    }

    /**
     * 根据日期获取对应的班别
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getClassByShiftDate")
    public ResponseResult<List<Map<String,Object>>> getClassByShiftDate(@RequestParam(value = "shift_date") String shift_date){
        try{
            shift_date=shift_date.replace("-", "");
            List<Map<String,Object>> list = workSysService.getClassByShiftDate(shift_date);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询ClassByShiftDate 失败",e);
            return  ResponseResult.fail("查询ClassByShiftDate失败");
        }
    }

    /**
     * 根据不同权限获取课别下拉框
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getModuleByPermission")
    public ResponseResult<List<Map<String,Object>>> getModuleByPermission(@RequestParam(value="deptid") String deptid){
        try{
            log.info("查询getModuleByPermission开始:");
            List<Map<String,Object>> list = workSysService.getModuleByPermission(deptid);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询ModuleByPermission 失败",e);
            return  ResponseResult.fail("查询ModuleByPermission失败");
        }
    }

    /**
     * 默认岗位维护module list
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getModuleList")
    public ResponseResult<List<Map<String,Object>>> getModuleList(@RequestParam(value="deptid") String deptid){
        try{
            List<Map<String,Object>> list = workSysService.getModuleList(deptid);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getModuleList失败",e);
            return  ResponseResult.fail("查询getModuleList失败");
        }
    }

    /**
     * 默认岗位维护module list
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getYMList")
    public ResponseResult<List<Map<String,Object>>> getYMList(){
        try{
            List<Map<String,Object>> list = workSysService.getYMList();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getYMList失败",e);
            return  ResponseResult.fail("查询getYMList失败");
        }
    }


    /**
     * 查询请假人员详情
     * @param shift_date
     * @param sdept_cname
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getLeaveDetail")
    public ResponseResult<List<Map<String,Object>>> getLeaveDetail(
             @RequestParam(value="shift_date") String shift_date
            ,@RequestParam(value="deptid") String deptid
            ,@RequestParam(value="sdept_cname") String sdept_cname
    ){
        try{
            List<Map<String,Object>> list = workSysService.getLeaveDetail(shift_date,deptid,sdept_cname);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询LeaveDetail 失败",e);
            return  ResponseResult.fail("查询LeaveDetail 失败");
        }
    }


    /**
     * 获取个人打卡记录
     * @param empid
     * @param ym
     * @return
     */
    @SystemLog(function_id = "RtOPControl",operateType = "select")
    @GetMapping("/getDoorDataByEmpid")
    public ResponseResult<List<Map<String,Object>>> getDoorDataByEmpid(
            @RequestParam(value="empid") String empid
            ,@RequestParam(value="ym") String ym
            ,@RequestParam(value="aclass") String aclass
    ){
        try{
            int shift=0;
            if(aclass.contains("夜")){
                shift=1;
            }
            List<Map<String,Object>> list = workSysService.getDoorDataByEmpid(empid,ym,shift);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询DoorDataByEmpid 失败",e);
            return  ResponseResult.fail("查询DoorDataByEmpid 失败");
        }
    }
}
