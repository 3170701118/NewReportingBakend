package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.CommonUtil.UploadUtil;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.pojo.FileManage;
import mfg.reporting.pojo.MenuInfo;
import mfg.reporting.service.HomePageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@RestController
@RequestMapping("/HomePage")
@Slf4j
public class HomePageController {

    @Resource
    public HomePageService homePageService;

    @Resource
    public UploadUtil uploadUtil;
    /**
     * 获取点击率 by route 获取
     */
    @SystemLog(function_id = "homepage_clickRate",operateType = "select")
    @GetMapping("/getClickRateSummary")
    public ResponseResult<List<Map<String,Object>>> getClickRateSummary(
            @RequestParam(value="weekType") String weekType
          , @RequestParam(value="rankType") String rankType){
        try{
            List<Map<String,Object>> list = homePageService.getClickRateSummary(weekType,rankType);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询ClickRateSummary 失败",e);
            return  ResponseResult.fail("查询ClickRateSummary 失败");
        }
    }

    /**
     * 首页 文件管理 上传
     * @param file
     * @param source
     * @param functionId
     * @return
     */
    @PostMapping("/uploadFileOnMfgManage")
    public ResponseResult<Map<String, Object>> uploadFileOnMfgManage(MultipartFile file, String source, String functionId){
       try{
        Map<String, Object> data =   uploadUtil.uploadFileManager(file, functionId,"manage");
        return  ResponseResult.success("上传成功",data);
        }catch (Exception e){
           return  ResponseResult.fail("上传失败");
       }
    }

    @GetMapping("/getFileTableData")
    public ResponseResult<List<FileManage>> getFileTableData(String functionId){
        List<FileManage> data = homePageService.getFileTableData(functionId);
        return  ResponseResult.success("查询成功",data);
    }

    @GetMapping("/getPageInfo")
    public ResponseResult<List<MenuInfo>> getPageInfo(String menuType){
        List<MenuInfo> data = homePageService.getMenuInfolist(menuType);
        return  ResponseResult.success("查询成功",data);
    }
}
