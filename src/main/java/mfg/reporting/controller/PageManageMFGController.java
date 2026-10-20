package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.PageManageMFGService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pageManage")
@Slf4j
public class PageManageMFGController {

    @Resource
    public PageManageMFGService pageManageMFGService;

    @Resource
    public JwtUtils jwtUtils;

    @GetMapping("/getPageManageMFGList")
    public ResponseResult<List<Map<String,Object>>> getPageManageMFGList(@RequestParam(value="module") String module , @RequestParam(value="parentMenuId") String parentMenuId){
        List<Map<String,Object>> list = pageManageMFGService.getPageManageMFGList(module,parentMenuId);
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("updateMenuName")
    public ResponseResult<Void> updateMenuName(@RequestParam(value="menuId") String menuId, @RequestParam(value="menuName") String menuName) {
       if(jwtUtils.havePermission("PageManageMFG")) {
           pageManageMFGService.updateMenuName(menuId, menuName);
       }else{
           return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
        }
        return ResponseResult.success("更新成功");
    }

    @PostMapping("/addPageManageLink")
    @SystemLog(function_id = "PageManageMFG", operateType = "insert")
    public ResponseResult<Void> insertPageManageLink(@RequestBody Map<String, String> data) {
        String updateUser = jwtUtils.getUsernameFromToken();

        data.put("UPDATE_USER", updateUser);
        if(jwtUtils.havePermission("PageManageMFG")) {
        pageManageMFGService.insertPageManageLink(data);
        }else{
            return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
        }
        return ResponseResult.success("插入成功");
    }

    @PostMapping("/updatePageManageLink")
    @SystemLog(function_id = "PageManageMFG", operateType = "update")
    public ResponseResult<Void> updatePageManageLink(@RequestBody Map<String, String> data) {
        String updateUser = jwtUtils.getUsernameFromToken();
        data.put("UPDATE_USER", updateUser);
        if(jwtUtils.havePermission("PageManageMFG")) {
            pageManageMFGService.updatePageManageLink(data);
        }else{
            return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
        }
        return ResponseResult.success("更新成功");
    }

    @GetMapping("/deletePageManageLink")
    @SystemLog(function_id = "PageManageMFG", operateType = "delete")
    public ResponseResult<Void> deletePageManageLink(@RequestParam(value="menuId") String menuId){
        if(jwtUtils.havePermission("PageManageMFG")) {
            pageManageMFGService.deletePageManageLink(menuId);
        }else{
            return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
        }
        return ResponseResult.success("删除成功");
    }
}
