package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.pojo.HoldLotToSubDeptPO;
import mfg.reporting.pojo.HoldLotToSubDeptRulePO;
import mfg.reporting.pojo.HoldLotToSubDeptSpecialRulePO;
import mfg.reporting.service.HoldLotToSubDeptService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/HoldLotToSubDept")
@Slf4j
public class HoldLotToSubDeptController {
    @Resource
    public HoldLotToSubDeptService holdLotToSubDeptService;

    //@SystemLog(function_id = "HoldLotToSubDeptFunction",operateType = "select")
    @GetMapping("/getHoldLotToSubDeptInfo")
    public ResponseResult<List<HoldLotToSubDeptPO>> searchHoldLotToSubDeptInfo(
            @RequestParam(value="lotId") String lotId
            , @RequestParam(value="opeNo",defaultValue = "") String opeNo
    ) {
        try {
            List<HoldLotToSubDeptPO> list = holdLotToSubDeptService.searchHoldLotToSubDeptInfo(lotId, opeNo);
            return ResponseResult.success("查询成功", list);
        }catch (ServiceException e){
            log.error("查询失败: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }catch (Exception e){
            log.error("查询失败", e);
            return ResponseResult.fail("查询失败");
        }
    }
    @GetMapping("/getHoldLotToSubDeptRuleInfo")
    public ResponseResult<List<HoldLotToSubDeptRulePO>> getHoldLotToSubDeptRuleInfo(
            @RequestParam(value="moduleOwner" ,defaultValue = "") String moduleOwner
            , @RequestParam(value="eqpG",defaultValue = "") String eqpG
            , @RequestParam(value="module",defaultValue = "") String module
            , @RequestParam(value="updateFlag",defaultValue = "all") String updateFlag
    ) {
        List<HoldLotToSubDeptRulePO> list = holdLotToSubDeptService.searchHoldLotToSubDeptRuleInfo(moduleOwner,eqpG,module,updateFlag);
        return ResponseResult.success("查询成功",list);
    }

    @PostMapping("/updateHoldLotToSubDeptRuleInfo")
    public ResponseResult<Void> updateHoldLotToSubDeptRuleInfo(@RequestBody HoldLotToSubDeptRulePO holdLotToSubDeptRulePO){

        //特殊处理权限，必须是同课别 间接人员；--@TODO 前端处理；
        holdLotToSubDeptService.updateHoldLotToSubDeptRuleInfo(holdLotToSubDeptRulePO);
        return ResponseResult.success("更新成功");
    }

    @PostMapping("/insertHoldLotToSubDeptRuleInfo")
    public ResponseResult<Void> insertHoldLotToSubDeptRuleInfo(@RequestBody HoldLotToSubDeptRulePO holdLotToSubDeptRulePO){

        //特殊处理权限，必须是同课别 间接人员；--@TODO 前端处理；
        holdLotToSubDeptService.insertHoldLotToSubDeptRuleInfo(holdLotToSubDeptRulePO);
        return ResponseResult.success("插入成功");
    }

    @GetMapping("/getSpecialRuleList")
    public ResponseResult<List<HoldLotToSubDeptSpecialRulePO>> getSpecialRuleList(){
        List<HoldLotToSubDeptSpecialRulePO> list = holdLotToSubDeptService.getSpecialRuleList();
        return ResponseResult.success("查询成功",list);
    }

    @PostMapping("/insertSpecialRule")
    public ResponseResult<Void> insertSpecialRule(@RequestBody HoldLotToSubDeptSpecialRulePO po){
        holdLotToSubDeptService.insertSpecialRule(po);
        return ResponseResult.success("插入成功");
    }

    @PostMapping("/importDataFromExcel")
    public ResponseResult<Void> insertStaticRuleFromExcel(MultipartFile file){
        try {
            List<HoldLotToSubDeptRulePO> list = EasyExcel.read(file.getInputStream())
                    .head(HoldLotToSubDeptRulePO.class)
                    .sheet()
                    .doReadSync();
            holdLotToSubDeptService.insertHoldLotToSubDeptRuleList(list);

        } catch (Exception e) {
          //  throw e;
        }
        return ResponseResult.success("插入成功");
    }

}
