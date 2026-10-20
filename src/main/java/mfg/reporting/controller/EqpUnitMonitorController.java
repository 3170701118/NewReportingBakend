package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.EqpUnitMonitorPO;
import mfg.reporting.service.EqpUnitMonitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eqpUnitMonitor")
@Slf4j
public class EqpUnitMonitorController {
    @Resource
    public EqpUnitMonitorService service;

    @Resource
    public JwtUtils jwtUtils;

   @GetMapping("/getEqpUnitMonitorInfo")
    @SystemLog(function_id = "eqpUnitMonitorTitle",operateType = "select")

    public ResponseResult<List<EqpUnitMonitorPO>> getEqpUnitMonitorInfo(
           //接收 @ResponseBody 传过来的 JSON 数据
           @RequestParam(value="module") String module,
           @RequestParam(value="eqpG") String eqpG,
           @RequestParam(value="unitFlag") String unitFlag,
           @RequestParam(value="unitJudge") String unitJudge
   ){
        try{
            List<EqpUnitMonitorPO> list = service.getEqpUnitMonitorInfo(module,eqpG,unitFlag,unitJudge);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getEQPGroupListByModule")
    @SystemLog(function_id = "eqpUnitMonitorTitle",operateType = "select")
    public ResponseResult<List<CommonSelectDTO>> getEQPGroupListByModule(@RequestParam(value="module") String module){
        try{
            List<CommonSelectDTO> list = service.getEQPGroupListByModule(module);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getHstEQPUnitMonitorInfo")
    @SystemLog(function_id = "eqpUnitMonitorTitle",operateType = "select")
    public ResponseResult<List<EqpUnitMonitorPO>> getHstEQPUnitMonitorInfo(
           //接收 @ResponseBody 传过来的 JSON 数据
           @RequestParam(value="eqpId") String eqpId,
           @RequestParam(value="unitId") String unitId,
           @RequestParam(value="unitFlag") String unitFlag,
           @RequestParam(value="startDate") String startDate,
           @RequestParam(value="endDate") String endDate){
        try{
            List<EqpUnitMonitorPO> list = service.getHstEQPUnitMonitorInfo(eqpId,unitId,unitFlag,startDate,endDate);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/keyInComment")
    @SystemLog(function_id = "eqpUnitMonitorTitle",operateType = "update")
    public ResponseResult<Void> keyInComment(@RequestBody Map<String,String> data){
        try{
            if(jwtUtils.havePermission("eqpUnitMonitorTitle")) {
                String userName = jwtUtils.getUsernameFromToken();
                service.keyInComment(data.get("eqpId"), data.get("unitId"), data.get("occurTime"), data.get("fcomment"), userName);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        }catch (Exception e){
            return  ResponseResult.fail("更新失败");
        }
    }


}
