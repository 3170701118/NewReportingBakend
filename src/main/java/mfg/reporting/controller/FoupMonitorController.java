package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.FoupMonitorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/foupMonitor")
@Slf4j
public class FoupMonitorController {

    @Resource
    public FoupMonitorService foupMonitorService; // FoupMonitorService should be defined in your project


    @RequestMapping("/getEmptyFoupDailyData")
    @SystemLog(function_id = "foupMonitorWebTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getEmptyFoupDailyData(@RequestParam(value = "fab")String fab){
        try{
            List<Map<String,Object>> list = foupMonitorService.getEmptyFoupDailyData(fab);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

}
