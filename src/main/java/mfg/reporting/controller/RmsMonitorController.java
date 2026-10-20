package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.pojo.RMSMonitorDetailsPO;
import mfg.reporting.service.RmsMonitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/RmsMonitor")
@Slf4j
public class RmsMonitorController {

    @Resource
    RmsMonitorService service;

    @GetMapping("/getRmsMonitorData")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<Map<String, Object>> selectRmsMonitorData() {
        try {
            Map<String, Object> result = service.selectRmsMonitorData();
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/getRmsSummaryData")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> selectRmsSummaryData(
            @RequestBody Map<String, Object> data
    ) {
        try {
            List<String> productStatus = (List<String>) data.get("productStatus");
            String fab =  !"".equals(data.get("fab"))?data.get("fab").toString():"N1A1";
            List<Map<String, Object>> result = service.selectRmsSummaryData(productStatus ,fab);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/getRmsSummaryTableData")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getRmsSummaryTableData(
            @RequestBody Map<String, Object> data
    ) {
        try {
            String module = (String) data.get("module");
            List<String> eqpg = (List<String>) data.get("eqpg");
            if (module == null || module.isEmpty()) {
                return ResponseResult.fail("系统错误,MODULE不能为空");
            }
            String fab =  !"".equals(data.get("fab"))?data.get("fab").toString():"N1A1";
            List<Map<String, Object>> result = service.getRmsSummaryTableData(module,eqpg,fab);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/getRmsDetailTableData")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<RMSMonitorDetailsPO>> getRmsDetailTableData(
            @RequestBody Map<String, Object> data
    ) {
        try {
            String tech = (String) data.get("tech");
            String module = (String) data.get("module");
            String type = (String) data.get("type");
            List<String> productStatus = (List<String>) data.get("productStatus");
            List<String> eqpg = (List<String>) data.get("eqpg");
            String fab =  !"".equals(data.get("fab"))?data.get("fab").toString():"N1A1";
            List<RMSMonitorDetailsPO> result = service.getRmsDetailTableData(fab,tech,type, module, productStatus,eqpg);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }

    }

    @GetMapping("/geteqpGByModule")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<CommonSelectDTO>> geteqpGByModule(
            @RequestParam(value = "module")  String module ,
            @RequestParam(value = "fab")  String fab
    ) {
        try {
            List<CommonSelectDTO> result = service.geteqpGByModule(module ,fab);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }

    }

    @GetMapping("/getRmsDetailByEQPRecipe")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsDetailByEQPRecipe(
            @RequestParam(value = "eqpg")  String eqpg,
            @RequestParam(value = "fab")  String fab,
            @RequestParam(value = "eqpId") String eqpId,
            @RequestParam(value = "recipe") String recipe,
            @RequestParam(value = "status") String status
    ){
        try {
            List<Map<String,Object>> result = service.getRmsDetailByEQPRecipe(fab,eqpg,eqpId,recipe,status);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("getSumamryTrendChart")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getSumamryTrendChart(
            @RequestBody Map<String, Object> data
    ){
        try {
            String fab =  !"".equals(data.get("fab"))?data.get("fab").toString():"N1A1";
            String tech =  !"".equals(data.get("tech"))?data.get("tech").toString():"ALL";
            String module = !"".equals(data.get("module"))?data.get("module").toString():"ALL";
            List<String> productStatus = (List<String>) data.get("productStatus");

            List<Map<String,Object>> result = service.getSumamryTrendChart(fab,tech,module,productStatus);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getTrendChartTech")
    public ResponseResult<List<CommonSelectDTO>> getTrendChartTech(){
        try {
            List<CommonSelectDTO> techList = service.getTrendChartTech();
            return ResponseResult.success("查询成功", techList);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getRmsModuleSummaryInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsModuleSummaryInfo(
            @RequestParam(value = "module")  String module,
            @RequestParam(value = "fab")  String fab
    ){
        try {
            List<Map<String,Object>> result = service.getRmsModuleSummaryInfo(module,fab);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getRmsNGSummaryInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsNGSummaryInfo(
            @RequestParam(value = "fab")  String fab
    ){
        try {
            List<Map<String,Object>> result = service.getRmsNGSummaryInfo(fab);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getRmsNGDetailsInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsNGDetailsInfo(
            @RequestParam(value = "fab")  String fab,
            @RequestParam(value = "module")  String module,
            @RequestParam(value = "shiftDate")  String shiftDate
    ){
        try {
            List<Map<String,Object>> result = service.getRmsNGDetailsInfo(fab,module,shiftDate);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getRmsNgWeekGroupInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsNgWeekGroupInfo(
            @RequestParam(value = "fab")  String fab,
            @RequestParam(value = "module")  String module
    ){
        try {
            List<Map<String,Object>> result = service.getRmsNgWeekGroupInfo(fab,module);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getRmsRecipeSummaryGroupTotalInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsRecipeSummaryGroupTotalInfo(
            @RequestParam(value = "fab")  String fab
    ){
        try {
            List<Map<String,Object>> result = service.getRmsRecipeSummaryGroupTotalInfo(fab);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }


    @GetMapping("getRmsRecipeSummaryInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsRecipeSummaryInfo(
            @RequestParam(value = "fab")  String fab,
            @RequestParam(value = "module")  String module
    ){
        try {
            List<Map<String,Object>> result = service.getRmsRecipeSummaryInfo(fab,module);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("getRmsRecipeDetailsInfo")
    @SystemLog(function_id = "RmsMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getRmsRecipeDetailsInfo(
            @RequestParam(value = "fab")  String fab,
            @RequestParam(value = "module")  String module,
            @RequestParam(value = "shiftDate")  String shiftDate
    ){
        try {
            List<Map<String,Object>> result = service.getRmsRecipeDetailsInfo(fab,module,shiftDate);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询失败：{}", e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }



}
