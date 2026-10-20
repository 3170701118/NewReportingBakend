package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.EqpRecoverService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/EqpRecover")
@Slf4j
public class EqpRecoverController {

    @Resource
    public EqpRecoverService service;

    @Resource
    public JwtUtils jwtUtils;

    @RequestMapping("/getFcstRecoverChart")
    public ResponseResult<List<Map<String,Object>>>  getFcstRecoverChart(
            @RequestParam(value="fab",required = true) String fab
            ,@RequestParam(value="module",required = true) String module)
    {
        try{
            return ResponseResult.success("查询成功",service.getFcstRecoverChart(fab, module));
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getRecoverRatioByModule")
    public ResponseResult<List<Map<String,Object>>>  getRecoverRatioByModule()
    {
        try{
            return ResponseResult.success("查询成功",service.getRecoverRatioByModule());
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getRecoverRatioByMFGModule")
    public ResponseResult<List<Map<String,Object>>>  getRecoverRatioByMFGModule()
    {
        try{
            return ResponseResult.success("查询成功",service.getRecoverRatioByMFGModule());
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getRecoverRatioByEQPG")
    public ResponseResult<List<Map<String,Object>>>  getRecoverRatioByEQPG(
            @RequestParam(value="module",required = true) String module
    )
    {
        try{
            return ResponseResult.success("查询成功",service.getRecoverRatioByEQPG(module));
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getRecoverTableDetails")
    public ResponseResult<List<Map<String,Object>>>  getRecoverTableDetails(
            @RequestParam(value="module",required = true) String module)
    {
        try{
            return ResponseResult.success("查询成功",service.getRecoverTableDetails(module));
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/updateACTRecoverTime")
    public ResponseResult<Void>  updateACTRecoverTime(
            @RequestParam(value="eqpId",required = true) String eqpId
            ,@RequestParam(value="actReturnTime",required = false) String actReturnTime
            ,@RequestParam(value="functionId",required = true) String functionId
            ,@RequestParam(value="fcomment",required = false) String fcomment
    )
    {
        try{
            if(!jwtUtils.havePermission(functionId)){
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
            String updateUser = jwtUtils.getUsernameFromToken();
            service.updateACTRecoverTime(eqpId,actReturnTime,updateUser,fcomment);
            return ResponseResult.success("更新成功");
        }catch (Exception e){
            log.error("更新失败:{}",e.getMessage());
            return  ResponseResult.fail("更新失败："+e.getMessage());
        }
    }

    @RequestMapping("/getProcessRate")
    public ResponseResult<List<Map<String,Object>>>  getProcessRate()
    {
        try{
            return ResponseResult.success("查询成功",service.getProcessRate());
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/updateProcessRate")
    public ResponseResult<Void>  updateProcessRate(
            @RequestParam(value="key1",required = true) String key1
            ,@RequestParam(value="key2",required = true) String key2
            ,@RequestParam(value="key3",required = true) String key3
    )
        {
            try{
                service.updateProcessRate(key1,key2,key3);
                return ResponseResult.success("更新成功");
            }catch (Exception e){
                log.error("更新失败:{}",e.getMessage());
                return  ResponseResult.fail("更新失败："+e.getMessage());
            }
        }

        @RequestMapping("/getFcstRecoverChartByPri")
    public ResponseResult<List<Map<String,Object>>>  getFcstRecoverChartByPri(
            @RequestParam(value="module",required = true) String module)
    {
        try{
            return ResponseResult.success("查询成功",service.getFcstRecoverChartByPri(module));
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getExportData")
    public ResponseResult<Map<String,Object>>  getExportData()
    {
        try{
            return ResponseResult.success("查询成功",service.getExportData());
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getExportBaseDataByModule")
    public ResponseResult<Map<String,Object>>  getExportBaseDataByModule(
            @RequestParam(value="pri",required = true) String pri,
            @RequestParam(value="curDate",required = true) String curDate
    )
    {
        try{
            return ResponseResult.success("查询成功",service.getExportBaseDataByModule(pri,curDate));
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败："+e.getMessage());
        }
    }

    @RequestMapping("/getExportBaseDataByEQPG")
    public ResponseResult<Map<String,Object>>  getExportBaseDataByEQPG(
            @RequestParam(value="pri",required = true) String pri,
            @RequestParam(value="curDate",required = true) String curDate,
            @RequestParam(value="module",required = true) String module
    )
        {
            try{
                return ResponseResult.success("查询成功",service.getExportBaseDataByEQPG(pri,curDate,module));
            }catch (Exception e){
                log.error("查询失败:{}",e.getMessage());
                return  ResponseResult.fail("查询失败："+e.getMessage());
            }
        }


        @RequestMapping("/getExportBaseDataByEqpId")
    public ResponseResult<Map<String,Object>>  getExportBaseDataByEqpId(
                @RequestParam(value="pri",required = true) String pri,
                @RequestParam(value="curDate",required = true) String curDate,
                @RequestParam(value="eqpg",required = true) String eqpg
        )
            {
                try{
                    return ResponseResult.success("查询成功",service.getExportBaseDataByEqpId(pri,curDate,eqpg));
                }catch (Exception e){
                    log.error("查询失败:{}",e.getMessage());
                    return  ResponseResult.fail("查询失败："+e.getMessage());
                }
            }





}
