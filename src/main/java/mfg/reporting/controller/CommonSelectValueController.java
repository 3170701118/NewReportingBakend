package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.CommonSelectValueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commonSelectValue")
@Slf4j
public class CommonSelectValueController {
    @Resource
    public CommonSelectValueService csv;

    /**
     * 获取 routeName,menuName 的对应关系，show select
     * @return list
     */
    @GetMapping("/getMenuList")
    public ResponseResult<List<Map<String,Object>>> getMenuList(){
        List<Map<String,Object>> list = csv.getMenuList();
        return ResponseResult.success("查询成功",list);
    }

    /**
     *email 与 cname的对应关系
     * @return list
     */
    @GetMapping("/getFunctionOwnerList")
    public ResponseResult<List<Map<String,Object>>> getFunctionOwnerList(@RequestParam(value="query",defaultValue = "") String query){
        List<Map<String,Object>> list = csv.getFunctionOwnerList(query);
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("/getAllFunctionList")
    public ResponseResult<List<Map<String,Object>>> getAllFunctionList(){
        List<Map<String,Object>> list = csv.getAllFunctionList();
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("/getAllSdepts")
    public ResponseResult<List<Map<String,Object>>> getAllSdepts(){
        List<Map<String,Object>> list = csv.getAllSdepts();
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("/getAllDepts")
    public ResponseResult<List<Map<String,Object>>> getAllDepts(){
        List<Map<String,Object>> list = csv.getAllDepts();
        return ResponseResult.success("查询成功",list);
    }


    @GetMapping("/getAllSDeptList")
    public ResponseResult<List<Map<String,Object>>> getAllSDeptList(){
        List<Map<String,Object>> list = csv.getAllSDeptList();
        return ResponseResult.success("查询成功",list);
    }

}
