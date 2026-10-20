package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.OutputReviewService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/OutputReview")
@Slf4j
public class OutputReviewController {

    @Resource
    public OutputReviewService service;

    @Resource
    public JwtUtils jwtUtils;


    @SystemLog(function_id = "outputReviewTitle",operateType = "select")
    @GetMapping("/getOutputSummaryByeqpg")
    public ResponseResult<List<Map<String,Object>>> getOutputSummaryByeqpg(
            @RequestParam("fab") String fab,
            @RequestParam("eqpg") String eqpg,
            @RequestParam("month") String month
    ){
        try{
            List<Map<String,Object>> list = service.getOutputSummaryByeqpg(fab,eqpg,month);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询失败",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "select")
    @GetMapping("/getOutputSummary")
    public ResponseResult<List<Map<String,Object>>> getOutputSummary(
            @RequestParam("fab") String fab,
            @RequestParam("month") String month
    ){
        try{
            List<Map<String,Object>> list = service.getOutputSummary(fab,month);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询失败",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "select")
    @GetMapping("/getMaintaineqpg")
    public ResponseResult<List<Map<String,Object>>> getMaintaineqpg(
            @RequestParam("module") String module,
            @RequestParam("FLAG") String FLAG
    ){
        try{
            List<Map<String,Object>> list = service.getMaintaineqpg(module,FLAG);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询失败",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "insert")
    @PostMapping("/insertMaintaineqpg")
    public ResponseResult<String> insertMaintaineqpg(
            @RequestBody Map<String,Object> data
    ){
        try{
            String userName = jwtUtils.getUsernameFromToken();
            data.put("username",userName);
            service.insertMaintaineqpg(data);
            return  ResponseResult.success("插入成功");
        }catch (Exception e){
            log.error("插入失败",e.getMessage());
            return  ResponseResult.fail("插入失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "update")
    @PostMapping("/updateMaintainEqpg")
    public ResponseResult<String> updateMaintaineqpg(
            @RequestBody Map<String,Object> data
    ){
        try{
            String userName = jwtUtils.getUsernameFromToken();
            data.put("username",userName);

            if(jwtUtils.havePermission(data.get("functionId").toString())) {
                service.updateMaintainEqpg(data);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail("没有权限");
            }
        }catch (Exception e){
            log.error("插入失败",e.getMessage());
            return  ResponseResult.fail("插入失败");
        }
    }

    //getkeyinSummary
    @SystemLog(function_id = "outputReviewTitle",operateType = "select")
    @GetMapping("/getkeyinSummary")
    public ResponseResult<List<Map<String,Object>>> getkeyinSummary(
            @RequestParam("eqpg") String eqpg
            ,@RequestParam("month") String month
    ){
        try{
            List<Map<String,Object>> list = service.getkeyinSummary(eqpg,month);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询失败",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }


    @SystemLog(function_id = "outputReviewTitle",operateType = "delete")
    @PostMapping("/deleteMaintaineqpg")
    public ResponseResult<String> deleteMaintaineqpg(
            @RequestBody Map<String,Object> data
    ){
        try{
            service.deleteMaintaineqpg(data.get("EQP_G").toString(),data.get("MODULE").toString());
            return  ResponseResult.success("删除成功");
        }catch (Exception e){
            log.error("删除失败",e.getMessage());
            return  ResponseResult.fail("删除失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "update")
    @PostMapping("/updateCapData")
    public ResponseResult<String> updateCapData(
            @RequestBody Map<String,Object> map
    ){
        try{

            String userName = jwtUtils.getUsernameFromToken();

            map.put("UPD_USER",userName);
            service.updateCapData(map);
            return  ResponseResult.success("更新成功");
        }catch (Exception e){
            log.error("更新失败",e.getMessage());
            return  ResponseResult.fail("更新失败");
        }
    }

    //getComment
    @SystemLog(function_id = "outputReviewTitle",operateType = "update")
    @GetMapping("/getComment")
    public ResponseResult<Map<String,Object>> getComment(
            @RequestParam("eqpg") String eqpg,
            @RequestParam("curMonth") String curMonth
    ){
        try{
            Map<String,Object> map = service.getComment(eqpg,curMonth);
            return  ResponseResult.success(map);
        }catch (Exception e){
            log.error("getComment查询失败",e.getMessage());
            return  ResponseResult.fail("getComment查询失败");
        }
    }

    //updateComment
    @SystemLog(function_id = "outputReviewTitle",operateType = "update")
    @PostMapping("/updateComment")
    public ResponseResult<String> updateComment(
            @RequestBody Map<String,Object> map
    ){
        try{
            String userName = jwtUtils.getUsernameFromToken();
            map.put("UPD_USER",userName);
            service.updateComment(map);
            return  ResponseResult.success("更新成功");
        }catch (Exception e){
            log.error("更新失败",e.getMessage());
            return  ResponseResult.fail("更新失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "select")
    @GetMapping("/getMakeUpPlanList")
    public ResponseResult<List<Map<String,Object>>> getMakeUpPlanList(
            @RequestParam("month") String month
    ){
        try{
            List<Map<String,Object>> list = service.getMakeUpPlanList(month);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询失败",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "insert")
    @PostMapping("/insertMakeUpplan")
    public ResponseResult<String> insertMakeUpplan(
            MultipartFile file, String functionId
    ){
        try{
            if(jwtUtils.havePermission(functionId)) {
                String userName = jwtUtils.getUsernameFromToken();
                service.insertMakeUpplan(file, userName);
                return ResponseResult.success("插入成功");
            }else{
                return ResponseResult.fail("没有权限");
            }
        }catch (Exception e){
            log.error("插入失败",e.getMessage());
            return  ResponseResult.fail("插入失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "delete")
    @GetMapping("/getModuleeqpgRequstTarget")
    public ResponseResult<List<Map<String,Object>>> getModuleeqpgRequstTarget(
            @RequestParam("EQP_G") String EQP_G,
            @RequestParam("month") String month
    ){
        try{
            List<Map<String,Object>> list = service.getModuleeqpgRequstTarget(EQP_G,month);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询失败",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }

    @SystemLog(function_id = "outputReviewTitle",operateType = "update")
    @PostMapping("/updateModuleeqpgMenuTarget")
    public ResponseResult<String> updateModuleeqpgMenuTarget(
            @RequestBody Map<String,Object> map
    ){
        try{
            String userName = jwtUtils.getUsernameFromToken();
            map.put("UPD_USER",userName);
            service.updateModuleeqpgMenuTarget(map);
            return  ResponseResult.success("更新成功");
        }catch (Exception e){
            log.error("更新失败",e.getMessage());
            return  ResponseResult.fail("更新失败");
        }
    }


}
