package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.CVDCleanMonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/CVDCleanMonitor")
@Slf4j
public class CVDCleanMonitorController {

    @Resource
    private CVDCleanMonitorService service;


    @GetMapping("/insertFDCMoveHistory")
    public ResponseResult<Void> insertFDCMoveHistory(
            @RequestParam(value = "fab") String fab
    ){
        try{
             service.getFDCMoveHistory(fab);
            return  ResponseResult.success("插入成功");
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }


}
