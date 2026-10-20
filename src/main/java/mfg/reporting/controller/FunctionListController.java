package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.FunctionList;
import mfg.reporting.service.FunctionListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pageFunction")
@Slf4j
public class FunctionListController {
    @Resource
    private FunctionListService fls;

    @Resource
    public JwtUtils jwtUtils;

    @SystemLog(function_id = "RtSys_Reporting_ownerList",operateType = "select")
    @PostMapping("/searchFunctionList")
    public ResponseResult<List<FunctionList>> searchFunctionList(@RequestBody FunctionList fl){
        try{
            List<FunctionList> list = fls.SearchFunctionList(fl);
            return ResponseResult.success("查询成功",list);
            }catch (Exception e){
            return  ResponseResult.fail("查询失败");
        }
    }

    @DeleteMapping("/deleteFunctionItem")
    public ResponseResult<Void> deleteFunctionItem(
            @RequestParam(value="functionId") String functionId
            ,@RequestParam(value="permissionFunctionId") String permissionFunctionId){
        try{
            if(jwtUtils.havePermission(permissionFunctionId)){
                fls.deleteFunctionItem(functionId);
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
