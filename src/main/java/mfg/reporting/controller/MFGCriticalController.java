package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.service.MFGCriticalService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/MFGCritical")
@Slf4j
public class MFGCriticalController {

    @Resource
    public MFGCriticalService criticalService;

    @Resource
    public JwtUtils jwtUtils;

    @GetMapping("/getTechList")
    public ResponseResult<List<CommonSelectDTO>> getMenuListInfo(@RequestParam(value = "fab") String fab){
        List<CommonSelectDTO> list = criticalService.getTechList(fab);
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("/getStdTechList")
    public ResponseResult<List<CommonSelectDTO>> getStdTechList(){
        List<CommonSelectDTO> list = criticalService.getStdTechList();
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("getRealModuleList")
    public ResponseResult<List<CommonSelectDTO>> getRealModuleList(@RequestParam(value = "fab") String fab){
        List<CommonSelectDTO> list = criticalService.getRealModuleList(fab);
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("getEqpgList")
    public ResponseResult<List<CommonSelectDTO>> getEqpgList(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module
    ){
        List<CommonSelectDTO> list = criticalService.getEqpgList(fab,module);
        return ResponseResult.success("查询成功",list);
    }


    @GetMapping("getLpHealthChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getLpHealthChart(
            @RequestParam(value = "fab") String fab
    ) {
        try {
            List list = criticalService.getLpHealthChart(fab);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("getLpHealthChartSummaryByEQPG")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getLpHealthChartSummaryByEQPG(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module
    ) {
        try {
            List list = criticalService.getLpHealthChartSummaryByEQPG(fab, module);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @GetMapping("getLpHealthChartDetailDaily")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getLpHealthChartDetailDaily(
            @RequestParam(value = "shiftDate") String shiftDate,
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg
    ) {
        try {
            List list = criticalService.getLpHealthChartDetailDaily(shiftDate, fab, module, eqpg);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("getEqpList")
    public ResponseResult<List<CommonSelectDTO>> getEqpList(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg
    ){
        List<CommonSelectDTO> list = criticalService.getEqpList(fab,module,eqpg);
        return ResponseResult.success("查询成功",list);
    }


    @GetMapping("getDYBChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getDYBChart(
            @RequestParam(value = "fab") String fab
    ) {
        try {
            // Placeholder for actual service call
            List list = criticalService.getDYBChart(fab);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("getDybChartSummaryByeqpg")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getDybChartSummaryByeqpg(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module
    ) {
        try {
        List list = criticalService.getDybChartSummaryByeqpg(fab,module);
        return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }



    @GetMapping("getDYBChartDetailDaily")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getDYBChartDetailDaily(
            @RequestParam(value = "shiftDate") String shiftDate,
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg
    ) {
        // Placeholder for actual service call
        List list = criticalService.getDYBChartDetailDaily(shiftDate,fab, module, eqpg);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("getQSRatioChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getQSRatioChart(
            @RequestParam(value = "fab") String fab
    ){
        List list = criticalService.getQSRatioChart(fab);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("getForceRatioChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getForceRatioChart(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "dimension") String dimension,
            @RequestParam(value = "type") String type
    ){
        try{
            List list = criticalService.getForceRatioChart(fab,date,dimension,type);
            return ResponseResult.success("查询成功", list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("getMeasurementForceRatioDetail")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getMeasurementForceRatioDetail(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "dimension") String dimension,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "eqpg") String eqpg
    ){
        try{
            List list = criticalService.getForceRatioDetail(fab,dimension,date,type,eqpg);
            return ResponseResult.success("查询成功", list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }




    @GetMapping("getMeasurementNgRatio")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List> getMeasurementNgRatio(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "date") String date
    ) {
        List list = criticalService.getMeasurementNgRatio(fab, date);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("getMeasurementForceEqpGListByFab")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> getMeasurementForceEqpGListByFab(
            @RequestParam(value = "fab") String fab
    ){
        List<CommonSelectDTO> list = criticalService.getMeasurementForceEqpGListByFab(fab);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("getMeasurementNgEqpGListByFab")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> getMeasurementNgEqpGListByFab(
            @RequestParam(value = "fab") String fab
    ){
        List<CommonSelectDTO> list = criticalService.getMeasurementNgEqpGListByFab(fab);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("/getLpFilterTableList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getLpFilterTableList(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module
    ){
        List<Map<String,Object>> list = criticalService.getLpFilterTableList(fab,module);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("/getDybTargetTableList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getDybTargetTableList(
            @RequestParam(value = "module") String module
    ){
        List<Map<String,Object>> list = criticalService.getDybTargetTableList(module);
        return ResponseResult.success("查询成功", list);
    }

    @PostMapping("/mergeDYBTargetInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> mergeDYBTargetInfo(
            @RequestBody Map<String,Object> data
    ){
        try {
            // DYB Target 上传权限卡控
            if (!jwtUtils.havePermission("MFGCriticalSetDYBTarget")) {
                return ResponseResult.fail("没有权限");
            }
            String mode = data.get("MODE").toString();
            String updateUser = jwtUtils.getUsernameFromToken();
            data.put("UPDATE_USER", updateUser);
            if ("add".equals(mode)) {
                criticalService.addDYBTargetInfo(data);
                return ResponseResult.success("新增成功");
            }

            if("update".equals(mode))
            {
                criticalService.updateDYBTargetInfo(data);
                return ResponseResult.success("更新成功");
            }

            throw new ServiceException("系统错误");

        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/mergeLpFilterInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> mergeLpFilterInfo(
            @RequestBody Map<String,Object> data
    ){
        try {

            // LP健康度 Target 上传权限卡控
            if (!jwtUtils.havePermission("MFGCriticalSetLpHealthTarget")) {
                return ResponseResult.fail("没有权限");
            }

            String mode = data.get("MODE").toString();
            String updateUser = jwtUtils.getUsernameFromToken();
            data.put("UPDATE_USER", updateUser);
            if ("add".equals(mode)) {
                criticalService.addLpFilterInfo(data);
                return ResponseResult.success("新增成功");
            }
            if ("delete".equals(mode)) {
                log.info("删除LP Filter数据：{}",data);

                criticalService.deleteLpFilterInfo(data);
                return ResponseResult.success("更新成功");
            }
            throw new ServiceException("系统错误");
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }


    @GetMapping("/getMeasurementMoveRatioInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementMoveRatioInfo(
            @RequestParam(value = "fab") String fab
    ){
        List<Map<String,Object>> list = criticalService.getMeasurementMoveRatioInfo(fab);
        return ResponseResult.success("查询成功", list);
    }


    @PostMapping("/getMeasurementSRInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementSRInfo(
            @RequestBody Map<String,Object> data
    ){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementSRInfo(data);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("查询MeasurementSRInfo 错误",e);
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/getMeasurementSRTableByTechList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementSRTableByTechList(
            @RequestBody Map<String,Object> data
    ){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementSRTableByTechList(data);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("查询MeasurementSRTableByTechList 错误",e);
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/getMeasurementSRTableByProdList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementSRTableByProdList(
            @RequestBody Map<String,Object> data
    ){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementSRTableByProdList(data);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("查询MeasurementSRTableByProdList 错误",e);
            return ResponseResult.fail(e.getMessage());
        }
    }


    @GetMapping("/getMeasurementSREQPGInfoByFab")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> getMeasurementSREQPGInfoByFab(
            @RequestParam(value = "fab") String fab
    ){
        List<CommonSelectDTO> list = criticalService.getMeasurementSREQPGInfoByFab(fab);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("/getMeasurementSRTechInfoByFab")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> getMeasurementSRTechInfoByFab(
            @RequestParam(value = "fab") String fab
    ){
        List<CommonSelectDTO> list = criticalService.getMeasurementSRTechInfoByFab(fab);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("/getMeasurementSRProdgInfoByFab")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> getMeasurementSRProdgInfoByFab(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "eqpg") String eqpg,
            @RequestParam(value = "tech") String tech
    ){
        List<CommonSelectDTO> list = criticalService.getMeasurementSRProdgInfoByFab(fab,eqpg,tech);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("/getPilotRunFabSummaryChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getPilotRunFabSummaryChart(
            @RequestParam(value = "fab") String fab
    ){
        List<Map<String,Object>> list = criticalService.getPilotRunFabSummaryChart(fab);
        return ResponseResult.success("查询成功", list);
    }



    @GetMapping("/getPilotRunSummaryChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getPilotRunSummaryChart(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "type") int type
    ){
        List<Map<String,Object>> list = criticalService.getPilotRunSummaryChart(fab,module,type);
        return ResponseResult.success("查询成功", list);
    }

    @GetMapping("/getpilotRunSummaryTableList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getpilotRunSummaryTableList(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "type") int type,
            @RequestParam(value = "shiftDate") String shiftDate
    ){
        List<Map<String,Object>> list = criticalService.getpilotRunSummaryTableList(fab,module,type,shiftDate);
        return ResponseResult.success("查询成功", list);
    }

    @PostMapping("/getpilotRunDetailsTableList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getpilotRunDetailsTableList(
            @RequestBody Map<String,Object> data
    ){
        List<Map<String,Object>> list = criticalService.getpilotRunDetailsTableList(data);
        return ResponseResult.success("查询成功", list);
    }

    @PostMapping("/updateLpHealthDetails")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> updateLpHealthDetails(
            @RequestBody Map<String,Object> data
    ){
        try {
            if(!jwtUtils.havePermission("MFGCriticallpHealthTitle")){
                return ResponseResult.fail("没有权限");
            }

            String updateUser = jwtUtils.getUsernameFromToken();

            data.put("updateUser", updateUser);
                criticalService.updateLpHealthDetails(data);
                return ResponseResult.success("新增成功");
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }

    @GetMapping("/getReworkSummaryChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getReworkSummaryChart(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg
             ){
        try {
            List<Map<String, Object>> list = criticalService.getReworkSummaryChart(fab, module, eqpg);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getDYBSummaryChart=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getReworkMoveDetails")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getReworkMoveDetails(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg,
            @RequestParam(value = "shiftDate") String shiftDate
             ){
        try{
            List<Map<String, Object>> list = criticalService.getReworkMoveDetails(fab, module, eqpg ,shiftDate);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getDYBSummaryTableList=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
             }

    @GetMapping("/getReprocessMonitorSummaryChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getReprocessMonitorSummaryChart(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg
             ){
        try {
            List<Map<String, Object>> list = criticalService.getReprocessMonitorSummaryChart(fab, module, eqpg);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getReprocessMonitorSummaryChart => {}",e.getMessage());
            return ResponseResult.fail("查询失败");
        }
             }

    @GetMapping("/getReprocessMonitorDetails")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getReprocessMonitorDetails(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "module") String module,
            @RequestParam(value = "eqpg") String eqpg,
            @RequestParam(value = "shiftDate") String shiftDate
             ){
        try{
            List<Map<String, Object>> list = criticalService.getReprocessMonitorDetails(fab, module, eqpg ,shiftDate);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getReprocessMonitorDetails=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getReworkTargetList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getReworkTargetList(
            @RequestParam(value = "module") String module
    ){
        List<Map<String,Object>> list = criticalService.getReworkTargetList(module);
        return ResponseResult.success("查询成功", list);
    }


    @PostMapping("/updateReworkTargetInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> updateReworkTargetInfo(
            @RequestBody Map<String,Object> data
    ){
        try {

            if(!jwtUtils.havePermission("MFGCriticalreworkTargetTitle")){
                return ResponseResult.fail("没有权限");
            }
            String updateUser = jwtUtils.getUsernameFromToken();
            data.put("updateUser", updateUser);
            criticalService.updateReworkTargetInfo(data);
            return ResponseResult.success("更新成功");
        }catch (Exception e){
            log.error("updateReworkTargetInfo=>{}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/addReworkTargetInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> addReworkTargetInfo(
            @RequestBody Map<String,Object> data
    ){
        try {
            if(!jwtUtils.havePermission("MFGCriticalreworkTargetTitle")){
                return ResponseResult.fail("没有权限");
            }
            String updateUser = jwtUtils.getUsernameFromToken();
            data.put("updateUser", updateUser);
            criticalService.addReworkTargetInfo(data);
            return ResponseResult.success("新增成功");
        }catch (Exception e){
            log.error("addReworkTargetInfo=>{}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @PostMapping("/getMeasurementNGDetails")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementNGDetails(
            @RequestBody Map<String,Object> data
    ){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementNGDetails(data.get("fab").toString(),data.get("date").toString(), data.get("eqpg").toString(), data.get("tech").toString(), data.get("prodg").toString());
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getMeasurementNGDetails=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getMeasurementWATRatioSummary")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementWATRatioSummary(
            @RequestParam(value = "fab") String fab
    ){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementWATRatioSummary(fab);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getMeasurementWATRatioSummary=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getMeasurementWATDetail")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementWATDetail(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "shiftDate") String shiftDate
    ){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementWATDetail(fab, shiftDate);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getMeasurementWATDetail=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getLotPriorityRatioChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getLotPriorityRatioChart(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "shiftDate") String shiftDate,
            @RequestParam(value = "hour") String hour,
            @RequestParam(value = "tech") String tech
    ){
        try {
            List<Map<String, Object>> list = criticalService.getLotPriorityRatioChart(fab, location, shiftDate, hour, tech);
            return ResponseResult.success("查询成功", list);
        } catch (Exception e) {
            log.error("getLotPriorityRatioChart=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }


    @GetMapping("/getLotPriorityRatioTrendChartData")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getLotPriorityRatioTrendChartData(
            @RequestParam(value = "fab") String fab,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "tech") String tech,
            @RequestParam(value = "priority") String priority
    ){
        try {
            List<Map<String, Object>> list = criticalService.getLotPriorityRatioTrendChartData(fab, location, startDate, endDate, tech, priority);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getLotPriorityRatioTrendChartData=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getCrackEqpIdList")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> getCrackEqpIdList(){
        try {
            List<CommonSelectDTO> list = criticalService.getCrackEqpIdList();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getCrackEqpIdList=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getCrackSummaryChart")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getCrackSummaryChart(
            @RequestParam(value = "eqpId") String eqpId,
            @RequestParam(value = "dimension") String dimension
    ){
        try {
            List<Map<String, Object>> list = criticalService.getCrackSummaryChart(eqpId, dimension);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e) {
            log.error("getCrackSummaryChart=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getMeasurementSRTargetInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getMeasurementSRTargetInfo(){
        try {
            List<Map<String, Object>> list = criticalService.getMeasurementSRTargetInfo();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getMeasurementSRTargetInfo=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/updateMeasurementSRTargetInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> updateMeasurementSRTargetInfo(
            @RequestBody Map<String,Object> data
    ){
        try {
            criticalService.updateMeasurementSRTargetInfo(data);
            return ResponseResult.success("更新成功");
        }catch (Exception e){
            log.error("updateMeasurementSRTargetInfo=>{}", e.getMessage());
            return ResponseResult.fail("更新失败");
        }
    }

    @PostMapping("/addMeasurementSRTargetInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> addMeasurementSRTargetInfo(
            @RequestBody Map<String,Object> data
    ){
        try {
            criticalService.addMeasurementSRTargetInfo(data);
            return ResponseResult.success("添加成功");
        }catch (Exception e){
            log.error("addMeasurementSRTargetInfo=>{}", e.getMessage());
            return ResponseResult.fail("添加失败");
        }
    }

    @GetMapping("/getA3SelfMoveRatioInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveRatioInfo(){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveRatioInfo();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveRatioInfo=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getA3SelfMoveAlarmRatioInfo")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveAlarmRatioInfo(){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveAlarmRatioInfo();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveAlarmRatioInfo=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getA3SelfMoveAlarmRatioInfoByEqpG")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveAlarmRatioInfoByEqpG(){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveAlarmRatioInfoByEqpG();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveAlarmRatioInfoByEqpG=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("insertA3SelfMoveAction")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> insertA3SelfMoveAction(
            @RequestBody Map<String,Object> data
    ){
        try{
            if(jwtUtils.havePermission(data.get("functionId").toString())) {
                String userName = jwtUtils.getUsernameFromToken();
                data.put("CREATE_USER", userName);
                data.put("UPDATE_USER", userName);
                criticalService.insertA3SelfMoveAction(data);
                return ResponseResult.success("添加成功");
            }else{
                return ResponseResult.fail("没有权限");
            }
        }catch (Exception e){
            log.error("insertA3SelfMoveAction=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getA3SelfMoveAlarmDetails")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveAlarmDetails(
            @RequestParam(value = "status") String status
    ){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveAlarmDetails(status);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveAlarmDetails=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/updateA3SelfMoveActionStatus")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> updateA3SelfMoveActionStatus(
            @RequestBody Map<String,Object> data
    ){
        try {
            if(jwtUtils.havePermission(data.get("functionId").toString())) {
                String userName = jwtUtils.getUsernameFromToken();
                data.put("UPDATE_USER", userName);
                criticalService.updateA3SelfMoveActionStatus(data);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail("没有权限");
            }
        }catch (Exception e){
            log.error("updateA3SelfMoveActionStatus=>{}", e.getMessage());
            return ResponseResult.fail("更新失败");
        }
    }

    @GetMapping("/getA3SelfMoveRatioInfoByModule")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveRatioInfoByModule(
    ){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveRatioInfoByModule();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveRatioInfoByModule=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getA3SelfMoveRatioInfoByEQPG")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveRatioInfoByEQPG(
            @RequestParam(value = "module") String module
    ){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveRatioInfoByEQPG(module);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveRatioInfoByEQPG=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getA3SelfMoveRatioInfoByEQP")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveRatioInfoByEQP(
            @RequestParam(value = "eqpg") String eqpg
    ){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveRatioInfoByEQP(eqpg);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveRatioInfoByEQP=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
    }
        }

        @GetMapping("/getA3SelfMoveRatioBackupRatio")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getA3SelfMoveRatioBackupRatio(){
        try {
            List<Map<String, Object>> list = criticalService.getA3SelfMoveRatioBackupRatio();
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            log.error("getA3SelfMoveRatioBackupRatio=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getA3SelfMoveRatioSetting")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Map<String,Object>> getA3SelfMoveRatioSetting(){
        try {
            Map<String, Object> map = criticalService.getA3SelfMoveRatioSetting();
            return ResponseResult.success("查询成功", map);
        }catch (Exception e){
            log.error("getA3SelfMoveRatioSetting=>{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/updateA3SelfMoveRatioAlarmSetting")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> updateA3SelfMoveRatioAlarmSetting(
            @RequestBody Map<String,Object> data
    ){
        try {
            if(jwtUtils.havePermission(data.get("functionId").toString())) {
                String userName = jwtUtils.getUsernameFromToken();
                data.put("UPDATE_USER", userName);
                criticalService.updateA3SelfMoveRatioAlarmSetting(data);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail("没有权限");
            }

        }catch (Exception e){
            log.error("updateA3SelfMoveRatioAlarmSetting=>{}", e.getMessage());
            return ResponseResult.fail("更新失败");
        }
    }

    @PostMapping("/updateA3SelfMoveRatioTarget")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> updateA3SelfMoveRatioTarget(
            @RequestBody Map<String,Object> data
    ){
        try {
            if(jwtUtils.havePermission(data.get("functionId").toString())) {
                String userName = jwtUtils.getUsernameFromToken();
                data.put("UPDATE_USER", userName);
                criticalService.updateA3SelfMoveRatioTarget(data);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail("没有权限");
            }

        }catch (Exception e){
            log.error("updateA3SelfMoveRatioTarget=>{}", e.getMessage());
            return ResponseResult.fail("更新失败");
        }
    }


    @PostMapping("/insertA3SelfMoveRatioTarget")
    @SystemLog(function_id ="MFGCriticalTitle",operateType = "SELECT")
    public ResponseResult<Void> insertA3SelfMoveRatioTarget(
            @RequestBody Map<String,Object> data
    ){
        try {
            if(jwtUtils.havePermission(data.get("functionId").toString())) {
                String userName = jwtUtils.getUsernameFromToken();
                data.put("UPDATE_USER", userName);
                criticalService.insertA3SelfMoveRatioTarget(data);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail("没有权限");
            }

        }catch (Exception e){
            log.error("insertA3SelfMoveRatioTarget=>{}", e.getMessage());
            return ResponseResult.fail("更新失败");
        }
    }

    @RequestMapping("/UploadA3MoveEQPGTargetExcel")
    ResponseResult<Map<String, Object>> UploadA3MoveEQPGTargetExcel(MultipartFile file, String functionId) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                criticalService.uploadExcel(file, jwtUtils.getUsernameFromToken());
                return ResponseResult.success("上传成功");
            }
            return ResponseResult.fail("无权限，上传失败");
        } catch (Exception e) {
            return ResponseResult.fail("上传失败:" + e.getMessage());
        }
    }







}