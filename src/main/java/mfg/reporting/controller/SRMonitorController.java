package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.service.SRMonitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SRMonitor")
@Slf4j
/**
 * Create by gszhang
 * User @田雪琪
 * Create Time 2026/5/23
 */
public class SRMonitorController {

    @Resource
    private SRMonitorService srMonitorService;


    @PostMapping(value = "/getSRMonitorSummary")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getSRMonitorSummary(@RequestBody List<String> eqpgList){
        try {
            List<Map<String,Object>> list = srMonitorService.getSRMonitorSummary(eqpgList);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询SRMonitorSummary失败",e);
            return ResponseResult.fail("查询SRMonitorSummary失败");
        }
    }

    @PostMapping(value = "/getSRMonitorEQPG")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<CommonSelectDTO>> getSRMonitorEQPG(){
        try {
            List<CommonSelectDTO> list = srMonitorService.getSRMonitorEQPG();
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询SRMonitorEQPG失败",e);
            return ResponseResult.fail("查询SRMonitorEQPG失败");
        }
    }

    @GetMapping(value = "/getSRMonitorChartByEQPG")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getSRMonitorChartByEQPG(@RequestParam String eqpg){
        try {
            List<Map<String,Object>> list = srMonitorService.getSRMonitorChartByEQPG(eqpg);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询SRMonitorChartByEQPG失败",e);
            return ResponseResult.fail("查询SRMonitorChartByEQPG失败");
        }
    }

    @GetMapping(value = "/getSRMonitorDetailBySuffix")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getSRMonitorDetailBySuffix(@RequestParam String eqpg){
        try {
            List<Map<String,Object>> list = srMonitorService.getSRMonitorDetailBySuffix(eqpg);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询SRMonitorDetailBySuffix失败",e);
            return ResponseResult.fail("查询SRMonitorDetailBySuffix失败");
        }
    }

    @GetMapping(value = "/getSRMOnitorDetailByOPENO")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getSRMOnitorDetailByOPENO(@RequestParam String eqpg, @RequestParam String prodspecId, @RequestParam String fab){
        try {
            List<Map<String,Object>> list = srMonitorService.getSRMOnitorDetailByOPENO(eqpg,prodspecId,fab);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询SRMOnitorDetailByOPENO失败",e);
            return ResponseResult.fail("查询SRMOnitorDetailByOPENO失败");
        }
    }

    @GetMapping(value = "/getSRMOnitorAlarmInfo")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getSRMOnitorAlarmInfo(){
        try {
            List<Map<String,Object>> list = srMonitorService.getSRMOnitorAlarmInfo();
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询SRMOnitorAlarmInfo失败",e);
            return ResponseResult.fail("查询SRMOnitorAlarmInfo失败");
        }
    }

    @GetMapping(value = "/getPSRCheckRateInfo")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getPSRCheckRateInfo(){
        try {
            List<Map<String,Object>> list = srMonitorService.getPSRCheckRateInfo();
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询PSRCheckRateInfo失败",e);
            return ResponseResult.fail("查询PSRCheckRateInfo失败");
        }
    }


    @GetMapping(value = "/SRMonitorAlarmList")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<Void> SRMonitorAlarmList(){
        try {
            srMonitorService.SRMonitorAlarmList();
            return ResponseResult.success("发送成功");
        }catch (Exception e){
            log.error("发送SRMonitorAlarmList失败",e);
            return ResponseResult.fail("发送SRMonitorAlarmList失败");
        }
    }

    @GetMapping(value = "/SRMonitorAlarmDeptNullsList")
    @SystemLog(function_id = "SRMonitor",operateType = "select")
    public ResponseResult<Void> SRMonitorAlarmDeptNullsList(){
        try {
            srMonitorService.SRMonitorAlarmDeptNullsList();
            return ResponseResult.success("发送成功成功");
        }catch (Exception e){
            log.error("发送失败",e);
            return ResponseResult.fail("发送失败");
        }
    }


}
