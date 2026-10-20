package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.QloopMonitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/QloopMonitor")
@Slf4j
public class QloopMonitorController {

    @Resource
    public QloopMonitorService service;

    @Resource
    public JwtUtils jwtUtils;

    @RequestMapping("/getControlSummaryData")
    public  ResponseResult<List<Map<String,Object>>> getControlSummaryData(String fab, String nengFlag){
        try {
            List<Map<String,Object>> result = service.getControlSummaryData(fab,nengFlag);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("获取QloopMonitor数据失败", e);
            return ResponseResult.fail("获取QloopMonitor数据失败:"+e.getMessage());
        }
    }


    @RequestMapping("/getPullSummaryData")
    public  ResponseResult<List<Map<String,Object>>> getPullSummaryData(String fab, String nengFlag){
        try {
            List<Map<String,Object>> result = service.getPullSummaryData(fab,nengFlag);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("获取QloopMonitor数据失败", e);
            return ResponseResult.fail("获取QloopMonitor数据失败:"+e.getMessage());
        }
    }


    @RequestMapping("/getPushSummaryData")
    public  ResponseResult<List<Map<String,Object>>> getPushSummaryData(String fab, String nengFlag){
        try {
            List<Map<String,Object>> result = service.getPushSummaryData(fab,nengFlag);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("获取QloopMonitor数据失败", e);
            return ResponseResult.fail("获取QloopMonitor数据失败:"+e.getMessage());
        }
    }

    @PostMapping("/getAbnormalLotDetail")
    public  ResponseResult<List<Map<String,Object>>> getAbnormalLotDetail(@RequestBody Map<String,Object> data){
        try {
            List<Map<String,Object>> result = service.getAbnormalLotDetail(data);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("获取AbnormalLotDetail数据失败", e);
            return ResponseResult.fail("获取AbnormalLotDetail数据失败:"+e.getMessage());
        }
    }



    @PostMapping("/getControlQloopList")
    public  ResponseResult<List<Map<String,Object>>> getControlQloopList(@RequestBody Map<String,Object> data){
        try {
            List<Map<String,Object>> result = service.getControlQloopList(data);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("获取QloopMonitor数据失败", e);
            return ResponseResult.fail("获取QloopMonitor数据失败:"+e.getMessage());
        }
    }

    @PostMapping("/getLotListByQloopList")
    public  ResponseResult<List<Map<String,Object>>> getLotListByQloopList(@RequestBody Map<String,Object> data){
        try {
            List<Map<String,Object>> result = service.getLotListByQloopList(data);
            return ResponseResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("获取QloopMonitor数据失败", e);
            return ResponseResult.fail("获取QloopMonitor数据失败:"+e.getMessage());
        }
    }

    @PostMapping("/updateQtimeAbnormal")
    public ResponseResult<Map<String, Object>> updateQtimeAbnormal(
             @RequestParam(value = "location") String location
            ,@RequestParam(value = "lot_id") String lot_id
            ,@RequestParam(value = "PRODSPEC_ID") String PRODSPEC_ID
            ,@RequestParam(value = "ope_no") String ope_no
            ,@RequestParam(value = "type") String type
            ,@RequestParam(value = "reason") String reason
            ,@RequestParam(value = "empid") String empid
            ,@RequestParam(value = "nengFlag") String nengFlag
            ) {
        try {
            String UPDATE_USER=jwtUtils.getUsernameFromToken();
            service.updateQtimeAbnormal(location, lot_id, PRODSPEC_ID, ope_no, type, reason, empid, UPDATE_USER,nengFlag);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }


}
