package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.MFGPermissionTableDTO;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.MFGPermission;
import mfg.reporting.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
@Slf4j
public class permissionController {

    @Resource
    public PermissionService permissionService;

    @Resource
    public JwtUtils jwtUtils;

    @SystemLog(function_id = "RtSys_Reporting_Permission",operateType = "select")
    @PostMapping("/searchTableList")
    public ResponseResult<List<MFGPermissionTableDTO>> searchTableList(@RequestBody Map<String,String> data){
        List<MFGPermissionTableDTO> list = permissionService.searchTableList(data);
        return ResponseResult.success("查询成功",list);
    }

    @SystemLog(function_id = "RtSys_Reporting_Permission",operateType = "insert")
    @PostMapping("/InsertPermissionItem")
    public ResponseResult<Void> insertPermissionItem(@RequestBody MFGPermission data,@RequestParam(value="functionId")String functionId){
         try{
        if(jwtUtils.havePermission(functionId)){
            data.setUpdateUser(jwtUtils.getUsernameFromToken());
            permissionService.insertPermissionItem(data);
            return ResponseResult.success("插入成功");
        }else {
            return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
         }
        }catch (Exception e) {
             log.error("权限数据插入失败", e);
             return ResponseResult.fail("插入失败");
        }
    }
    @SystemLog(function_id = "RtSys_Reporting_Permission",operateType = "update")
    @PostMapping("/updatePermissionItem")
    public ResponseResult<Void> updatePermissionItem (@RequestBody MFGPermission data,@RequestParam(value="functionId")String functionId){
        try{
            if(jwtUtils.havePermission(functionId)){
                data.setUpdateUser(jwtUtils.getUsernameFromToken());
                permissionService.updatePermissionItem(data);
                return ResponseResult.success("插入成功");
            }else {
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        }catch (Exception e) {
            log.error("权限数据插入失败", e);
            return ResponseResult.fail("插入失败");
        }
    }

    @SystemLog(function_id = "RtSys_Reporting_Permission",operateType = "delete")
    @DeleteMapping("/deletePermissionItem")
    public ResponseResult<Void> deletePermissionItem(@RequestParam(value="permissionId") String permissionId,@RequestParam(value="functionId")String functionId){
        try{
            if(jwtUtils.havePermission(permissionId)){
                permissionService.deletePermissionItem(functionId);
                return  ResponseResult.success("删除成功");
            }else{
                return  ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        }catch(Exception e){
            log.error("删除失败",e);
            return  ResponseResult.fail("删除失败");
        }
    }
}
