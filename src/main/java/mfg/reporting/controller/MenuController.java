package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.pojo.MenuInfo;
import mfg.reporting.dto.MenuInfoDTO;
import mfg.reporting.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Resource
    public MenuService menuService;

    @SystemLog(function_id = "RtSys_Reporting_MenuList",operateType = "select")
    @GetMapping("/getMenuListInfo")
    public ResponseResult<List<MenuInfoDTO>> getMenuListInfo(){
        List<MenuInfoDTO> list = menuService.getMenuListInfo();
        return ResponseResult.success("查询成功",list);
    }

    @SystemLog(function_id = "RtSys_Reporting_MenuList",operateType = "select")
    @GetMapping("/getMenuInfoDetails")
    public ResponseResult<MenuInfo>  getMenuInfoDetails(@RequestParam(value="menuId") String menuId){
        MenuInfo menuInfo = menuService.getMenuInfoDetails(menuId);
        return ResponseResult.success("查询成功",menuInfo);
    }

    @SystemLog(function_id = "RtSys_Reporting_MenuList",operateType = "insert")
    @PostMapping("/insertMenuInfo")
    public ResponseResult<Void>  insertMenuInfo(@RequestBody MenuInfo menuInfo){
        try{
            menuService.insertMenuInfo(menuInfo);
            return ResponseResult.success("插入成功");
        } catch (ServiceException e){
            return ResponseResult.fail(e.getCode(),e.getMessage());
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }

    @SystemLog(function_id = "RtSys_Reporting_MenuList",operateType = "update")
    @PostMapping("/updateMenuInfo")
    public ResponseResult<Void>  updateMenuInfo(@RequestBody MenuInfo menuInfo){
        try{
            menuService.updateMenuInfo(menuInfo);
            return ResponseResult.success("更新成功");
        }catch (ServiceException e){
            return ResponseResult.fail(e.getCode(),e.getMessage());
        }catch (Exception e){
            return ResponseResult.fail(e.getMessage());
        }
    }

    @SystemLog(function_id = "RtSys_Reporting_MenuList",operateType = "delete")
    @DeleteMapping("/deleteMenuInfo")
    public ResponseResult<Void> deleteMenuInfo(@RequestParam(value="menuId") String menuId){
        try{
            menuService.deleteMenuInfo(menuId);
            return ResponseResult.success("删除成功");
        }catch (ServiceException e){
            return ResponseResult.fail(e.getCode(),e.getMessage());
        }catch (Exception e) {
            log.error(e.getMessage());
             return ResponseResult.fail(e.getMessage());
        }
    }
}
