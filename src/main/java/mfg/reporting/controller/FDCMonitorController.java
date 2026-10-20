package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.FDCMonitorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@RestController
@RequestMapping("/FDCMonitor")
@Slf4j
public class FDCMonitorController {

    @Resource
    public FDCMonitorService service;

    @Resource
    public JwtUtils jwtUtils;


    @GetMapping("/getTFEqpgFDCSummary")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getTFEqpgFDCSummary(
            @RequestParam(value = "startDate", defaultValue = "") String startDate,
            @RequestParam(value = "endDate" ,defaultValue = "") String endDate
    ){
        try{
            List<Map<String,Object>> list = service.getTFEqpgFDCSummary(startDate,endDate);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getTFEqpgRecipeFDCSummary")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")

    public ResponseResult<List<Map<String,Object>>> getTFEqpgRecipeFDCSummary(
            @RequestParam(value="eqpg") String eqpg,
            @RequestParam(value="date") String date,
            @RequestParam(value="specialFlag",defaultValue = "")String specialFlag
    ){
        try{
            List<Map<String,Object>> list = service.getTFEqpgRecipeFDCSummary(eqpg,date,specialFlag);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }



    @GetMapping("/getTFToolRecipeFDCSUmmary")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getTFToolRecipeFDCSUmmary(
            @RequestParam(value="eqpg") String eqpg,
            @RequestParam(value="date") String date,
            @RequestParam(value="specialFlag",defaultValue = "")String specialFlag,
            @RequestParam(value="recipe")String recipe
    ){
        try{
            List<Map<String,Object>> list = service.getTFToolRecipeFDCSUmmary(eqpg,date,specialFlag,recipe);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }


    @GetMapping("/getClnRecipeChartMonitor")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getClnRecipeChartMonitor(
            @RequestParam(value="eqpg") String eqpg,
            @RequestParam(value="date") String date,
            @RequestParam(value="toolName" ,required = false) String toolName,
            @RequestParam(value="recipe") String recipe,
            @RequestParam(value="specialFlag" , defaultValue = "") String specialFlag
    ){
        try{
            List<Map<String,Object>> list = service.getClnRecipeChartMonitor(eqpg,date,toolName,recipe,specialFlag);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }


    @GetMapping("/getTFClnSpecialFlagMaintainInfo")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getTFClnSpecialFlagMaintainInfo(
    ){
        try{
            List<Map<String,Object>> list = service.getTFClnSpecialFlagMaintainInfo();
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

@GetMapping("/getTFClnFilterDataMaintainInfo")
@SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")
public ResponseResult<List<Map<String,Object>>> getTFClnFilterDataMaintainInfo(
){
    try{
        List<Map<String,Object>> list = service.getTFClnFilterDataMaintainInfo();
        return ResponseResult.success("查询成功",list);
    }catch (Exception e){
        return  ResponseResult.fail("查询失败");
    }
}

@PostMapping("/uploadSpecialFlagInfo")
@SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "UPLOAD")
public ResponseResult<List<Map<String,Object>>> mergeTFClnSpecialFlagMaintainInfo(
        MultipartFile file, String source, String functionId
){
    try{
        if(jwtUtils.havePermission(functionId)) {
            String username = jwtUtils.getUsernameFromToken();
            service.mergeTFClnSpecialFlagMaintainInfo(file, username);
            return ResponseResult.success("插入成功");
        }

        return ResponseResult.fail("无权限，上传失败");
    }catch (Exception e){
        return  ResponseResult.fail("插入失败");
    }
}


    @PostMapping("/insertTFClnFilterDataMaintainInfo")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "insert")
    public ResponseResult<List<Map<String,Object>>> insertTFClnFilterDataMaintainInfo(
            @RequestBody Map<String,Object> data
    ){
        try{
            if(jwtUtils.havePermission(data.get("FUNCTION_ID").toString())) {
                String username = jwtUtils.getUsernameFromToken();
                data.put("UPDATE_USER", username);
                service.insertTFClnFilterDataMaintainInfo(data);
                return ResponseResult.success("插入成功");
            }
            return ResponseResult.fail("无权限，上传失败");
        }catch (Exception e){
            return  ResponseResult.fail("插入失败");
        }
    }


    @PostMapping("/deleteTFClnSpecialFlagMaintainInfo")

    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> deleteTFClnSpecialFlagMaintainInfo(
            @RequestBody Map<String,Object> data
    ){
        ResponseResult<List<Map<String, Object>>> result;
        try{
            if(jwtUtils.havePermission(data.get("FUNCTION_ID").toString())) {
                String username = jwtUtils.getUsernameFromToken();
                data.put("UPDATE_USER", username);
                service.deleteTFClnSpecialFlagMaintainInfo(data);
                return ResponseResult.success("插入成功");
            }
            return ResponseResult.fail("无权限，上传失败");
        }catch (Exception e){
                return  ResponseResult.fail("插入失败");
        }
    }


    @PostMapping("/delteTFClnFilterDataMaintainInfo")
    @SystemLog(function_id = "TFFDCClnMonitorTitle",operateType = "delete")
    public ResponseResult<List<Map<String,Object>>> delteTFClnFilterDataMaintainInfo(
            @RequestBody Map<String,Object> data
    ){
        try{
            if(jwtUtils.havePermission(data.get("FUNCTION_ID").toString())) {
            String username = jwtUtils.getUsernameFromToken();
            data.put("UPDATE_USER",username);
            service.delteTFClnFilterDataMaintainInfo(data);
            return ResponseResult.success("插入成功");
          }
            return ResponseResult.fail("无权限，上传失败");
        }catch (Exception e){
            return  ResponseResult.fail("插入失败");
        }
    }


}
