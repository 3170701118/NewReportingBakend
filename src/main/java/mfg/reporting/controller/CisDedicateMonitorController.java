package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.CisDedicateMonitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cisDedicateMonitor")
@Slf4j
public class CisDedicateMonitorController {

    @Resource
    public CisDedicateMonitorService service;

    @Resource
    public JwtUtils jwtUtils;

    @PostMapping("/selectCISDedicateTableList")
    @SystemLog(function_id = "CISDedicateMonitor",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> selectCISDedicateTableList(){
        try{
            List<Map<String,Object>> list = service.selectCISDedicateTableList();
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/addNewCISDedicateMonitorEqp")
    @SystemLog(function_id = "CISDedicateMonitor",operateType = "add")
    public ResponseResult<Void> addNewCISDedicateMonitorEqp( @RequestParam(value="eqpId") String eqpId){
        try{
            if(jwtUtils.havePermission("CISDedicateMonitor")){
                service.addNewCISDedicateMonitorEqp(eqpId);
                return ResponseResult.success("新增成功");
            }
            return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
        }catch (Exception e){
            return  ResponseResult.fail("新增失败");
        }
    }

    @GetMapping("/deleteEqpInfoForCISMonitor")
    @SystemLog(function_id = "CISDedicateMonitor",operateType = "delete")
    public ResponseResult<Void> deleteEqpInfoForCISMonitor( @RequestParam(value="eqpId") String eqpId){
        try{
            if(jwtUtils.havePermission("CISDedicateMonitor")) {
                service.deleteEqpInfoForCISMonitor(eqpId);
                return ResponseResult.success("删除成功");
            }
            return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
        }catch (Exception e){
            return  ResponseResult.fail("删除失败");
        }
    }

    @GetMapping("/getCISDedicateDetailsByTech")
    @SystemLog(function_id = "CISDedicateMonitor",operateType = "delete")
    public ResponseResult<List<Map<String,Object>>> getCISDedicateDetailsByTech( @RequestParam(value="eqpId") String eqpId,@RequestParam(value="tech") String tech){
        try{
            List<Map<String,Object>> list =  service.GetCISDedicateDetailsByTech(tech,eqpId);
                return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/CISMonitorMailAlarm")
    public ResponseResult<Void> CISMonitorMailAlarm(){
        try{
            service.CISMonitorMailAlarm();
            return  ResponseResult.success("发送成功");
        }catch (Exception e){
            return  ResponseResult.fail("发送失败");
        }
    }

    @GetMapping("/mailFP_FpolyForL111GFFP10")
    public  ResponseResult<Void> mailFP_FpolyForL111GFFP10(@RequestParam("fab") String fab){
        if("".equals(fab)){
            return ResponseResult.fail("参数接收失败");
        }

        try{
            service.mailFP_FpolyForL111GFFP10(fab);
            return  ResponseResult.success("发送成功");
        }catch (Exception e){
            return  ResponseResult.fail("发送失败");
        }

    }

    @GetMapping("/mailProdgOPE_UF_Inhibit")
    public  ResponseResult<Void> mailProdgOPE_UF_Inhibit(@RequestParam("fab") String fab){
        if("".equals(fab)){
            return ResponseResult.fail("参数接收失败");
        }

        try{
            service.mailProdgOPE_UF_Inhibit(fab);
            return  ResponseResult.success("发送成功");
        }catch (Exception e){
            return  ResponseResult.fail("发送失败");
        }

    }





}
