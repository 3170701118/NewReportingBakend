package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.RdsMonitorWebService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rdsMonitor")
@Slf4j
public class RdsMonitorWebController {
    @Resource
    public RdsMonitorWebService rdsMonitorWebService;

    @Resource
    public JwtUtils jwtUtils;

    @RequestMapping("/getRdsHisSummaryChartData")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsHisSummaryChartData(
          @RequestBody Map<String,Object> data
    ){
        try{
            String fab = (String) data.get("fab");
            String closeWipColumn = (String) data.get("closeWipColumn");
            String module = (String) data.get("module");
            String groupType = (String) data.get("groupType");
            return ResponseResult.success(rdsMonitorWebService.getRdsHisSummaryChartData(fab,closeWipColumn,module,groupType));
        } catch (Exception e) {
            log.error("getRdsHisSummaryChartData error:", e);
            return ResponseResult.fail("getRdsHisSummaryChartData error:" + e.getMessage());
        }
    }


    @RequestMapping("/getRdsRealSummaryTableListGroupModule")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsRealSummaryTableListGroupModule(String fab){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsRealSummaryTableListGroupModule(fab));
        } catch (Exception e) {
            log.error("getRdsRealSummaryTableListGroupModule error:", e);
            return ResponseResult.fail("getRdsRealSummaryTableListGroupModule error:" + e.getMessage());
        }

    }

    @PostMapping("/getRdsRealDetailsTableList")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsRealDetailsTableList(
          @RequestBody Map<String,Object> data
    ){
        try{
            String fab = (String) data.get("fab");
            String module = (String) data.get("module");
            String eqpId = (String) data.get("eqpId");
            String remainRdsmin =  (String) data.get("remainRdsmin") ;
            String remainRdsmax =  (String) data.get("remainRdsmax");
            String testfor = (String) data.get("testfor");
            return ResponseResult.success(rdsMonitorWebService.getRdsRealDetailsTableList(fab,module,eqpId,remainRdsmin,remainRdsmax,testfor));
        } catch (Exception e) {
            log.error("getRdsRealDetailsTableList error:", e);
            return ResponseResult.fail("getRdsRealDetailsTableList error:" + e.getMessage());
        }
    }

    @PostMapping("/getRdsHisDetailsTableList")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsHisDetailsTableList(
          @RequestBody Map<String,Object> data
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsHisDetailsTableList(data));
        } catch (Exception e) {
            log.error("getRdsHisDetailsTableList error:", e);
            return ResponseResult.fail("getRdsHisDetailsTableList error:" + e.getMessage());
        }
    }

    @PostMapping("/getRdsHitRatioDetail")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsHitRatioDetail(
            @RequestBody Map<String,Object> data
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsHitRatioDetail(data));
        } catch (Exception e) {
            log.error("getRdsHitRatioDetail error:", e);
            return ResponseResult.fail("getRdsHitRatioDetail error:" + e.getMessage());
        }
    }




    @GetMapping("/getOcsMonitorPlanSetting")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorPlanSetting(
            @RequestParam(value="fab") String fab
            , @RequestParam(value="module") String module
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getOcsMonitorPlanSetting(fab,module));
        } catch (Exception e) {
            log.error("getOcsMonitorPlanSetting error:", e);
            return ResponseResult.fail("getOcsMonitorPlanSetting error:" + e.getMessage());
        }
    }

    @GetMapping("/getRDSManualCountTrendChart")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRDSManualCountTrendChart(
            @RequestParam(value="fab") String fab
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRDSManualCountTrendChart(fab));
        } catch (Exception e) {
            log.error("getRDSManualCountTrendChart error:", e);
            return ResponseResult.fail("getRDSManualCountTrendChart error:" + e.getMessage());
        }
    }

    @GetMapping("/getRdsHitRatioSummaryChart")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsHitRatioSummaryChart(
            @RequestParam(value="fab") String fab
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsHitRatioSummaryChart(fab));
        } catch (Exception e) {
            log.error("getRdsHitRatioSummaryChart error:", e);
            return ResponseResult.fail("getRdsHitRatioSummaryChart error:" + e.getMessage());
        }
    }

    @GetMapping("/getFeedBackRatioSummaryChart")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getFeedBackRatioSummaryChart(
            @RequestParam(value="fab") String fab
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getFeedBackRatioSummaryChart(fab));
        } catch (Exception e) {
            log.error("getFeedBackRatioSummaryChart error:", e);
            return ResponseResult.fail("getFeedBackRatioSummaryChart error:" + e.getMessage());
        }
    }


    @GetMapping("/getRdsManualCountDetail")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsManualCountDetail(
            @RequestParam(value="shiftDate") String shiftDate
            , @RequestParam(value="fab") String fab
            , @RequestParam(value="module") String module
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsManualCountDetail(shiftDate,fab,module));
        } catch (Exception e) {
            log.error("getRdsManualCountDetail error:", e);
            return ResponseResult.fail("getRdsManualCountDetail error:" + e.getMessage());
        }
    }

    @GetMapping("/getRdsEQPGBackupJobGroup")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsEQPGBackupJobGroup(){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsEQPGBackupJobGroup());
        } catch (Exception e) {
            log.error("getRdsEQPGBackupJobGroup error:", e);
            return ResponseResult.fail("getRdsEQPGBackupJobGroup error:" + e.getMessage());
        }
    }

    @PostMapping("/insertRdsEQPGBackupJobGroup")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "insert")
    public ResponseResult<String> insertRdsEQPGBackupJobGroup(
          @RequestBody Map<String,Object> data
    ){
        try{
            if(!jwtUtils.havePermission("RDS_EQPG_MAINTAIN")){
                return ResponseResult.fail("没有权限");
            }

            String updateUser = jwtUtils.getUsernameFromToken();
            data.put("updateUser",updateUser);
            rdsMonitorWebService.insertRdsEQPGBackupJobGroup(data);
            return ResponseResult.success("success");
        } catch (Exception e) {
            log.error("insertRdsEQPGBackupJobGroup error:", e);
            return ResponseResult.fail("insertRdsEQPGBackupJobGroup error:" + e.getMessage());
        }
    }

    @PostMapping("/deleteRdsEQPGBackupJobGroup")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "delete")
    public ResponseResult<String> deleteRdsEQPGBackupJobGroup(
          @RequestBody Map<String,Object> data
    ){
        try{
            rdsMonitorWebService.deleteRdsEQPGBackupJobGroup(data);
            return ResponseResult.success("success");
        } catch (Exception e) {
            log.error("deleteRdsEQPGBackupJobGroup error:", e);
            return ResponseResult.fail("deleteRdsEQPGBackupJobGroup error:" + e.getMessage());
        }
    }


    @PostMapping("/insertRdsOverTimeReason")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "insert")
    public ResponseResult<String> insertRdsOverTimeReason(
            @RequestBody Map<String,Object> data
    ){
        try{
            if(jwtUtils.havePermission("RDSMonitorWebOverTimeDetails")) {
                String updateUser = jwtUtils.getUsernameFromToken();
                data.put("updateUser", updateUser);
                rdsMonitorWebService.insertRdsOverTimeReason(data);
                return ResponseResult.success("success");
            }
            return ResponseResult.fail("没有权限");
        } catch (Exception e) {
            log.error("insertRdsOverTimeReason error:", e);
            return ResponseResult.fail("insertRdsOverTimeReason error:" + e.getMessage());
        }
    }

    @GetMapping("/getMoinitorPlanDetail")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getMoinitorPlanDetail(
            @RequestParam(value="fab") String fab,
            @RequestParam(value="module") String module
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getMoinitorPlanDetail(fab,module));
        } catch (Exception e) {
            log.error("getMoinitorPlanDetail error:", e);
            return ResponseResult.fail("getMoinitorPlanDetail error:" + e.getMessage());
        }
    }

    @GetMapping("/getRdsHitRatioPre7DDetail")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRdsHitRatioPre7DDetail(
            @RequestParam(value="fab") String fab
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRdsHitRatioPre7DDetail(fab));
        } catch (Exception e) {
            log.error("getRdsHitRatioPre7DDetail error:", e);
            return ResponseResult.fail("getRdsHitRatioPre7DDetail error:" + e.getMessage());
        }
    }

    @GetMapping("/getRDSAbnormalRecipeSummary")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRDSAbnormalRecipeSummary(
            @RequestParam(value="fab") String fab
    ){
        try{
            return ResponseResult.success(rdsMonitorWebService.getRDSAbnormalRecipeSummary(fab));
        } catch (Exception e) {
            log.error("getRDSAbnormalRecipeSummary error:", e);
            return ResponseResult.fail("getRDSAbnormalRecipeSummary error:" + e.getMessage());
        }
    }

    @GetMapping("/getRDSAbnormalRecipeDetails")
    @SystemLog(function_id = "RDSMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRDSAbnormalRecipeDetails(
            @RequestParam(value="shiftDate") String shiftDate
            , @RequestParam(value="fab") String fab
            , @RequestParam(value="module") String module
    ) {
        try {
            return ResponseResult.success(rdsMonitorWebService.getRDSAbnormalRecipeDetails(shiftDate, fab, module));
        } catch (Exception e) {
            log.error("getRDSAbnormalRecipeDetails error:", e);
            return ResponseResult.fail("getRDSAbnormalRecipeDetails error:" + e.getMessage());
        }

    }



}
