package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.ClickLogReader;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.FileManage;
import mfg.reporting.pojo.FunctionList;
import mfg.reporting.pojo.MenuInfo;
import mfg.reporting.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * 提供对外的一致接口，如点击率，所有的 prod,tech等公共信息；
 * @author create by zgs  on 2024-11-03
 */
@RestController
@RequestMapping("/Common")
@Slf4j
public class CommonController {

    @Resource
    public CommonService commonService;



    @Autowired
    public ClickLogReader clickLogReader;

    @Resource
    public JwtUtils jwtUtils;
    /**
     * 下载模板
     * @param response 浏览器
     * @param functionId 文件管理 传参
     */
    @GetMapping("/downloadTemplate")
    public void  downloadTemplate(HttpServletResponse response, @RequestParam(value="functionId") String functionId ,@RequestParam(value="fileName")String fileName) {
        FileManage fileManage = commonService.getDownLoadTemplate(functionId,fileName);

        String path = fileManage.getFilePath();
        //path 为相对路径，需要转换为绝对路径

        File file = new File(path);
        if (!file.exists()) {
            throw new ServiceException("当前下载的文件不存在，请检查路径是否正确");
        }

        // 清空 response
        response.reset();
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");


        // 将文件读到输入流中
        try (InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()))) {

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int len;

            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while((len = is.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 下载模板
     * @param response 浏览器
     * @param functionId 文件管理 传参
     */
    @GetMapping("/downloadManage")
    public void  downloadManage(HttpServletResponse response, @RequestParam(value="functionId") String functionId ,@RequestParam(value="fileName")String fileName) {
        FileManage fileManage = commonService.getDownLoadManage(functionId,fileName);

        String path = fileManage.getFilePath();
        //path 为相对路径，需要转换为绝对路径

        File file = new File(path);
        if (!file.exists()) {
            throw new ServiceException("当前下载的文件不存在，请检查路径是否正确");
        }

        // 清空 response
        response.reset();
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");


        // 将文件读到输入流中
        try (InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()))) {

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int len;

            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while((len = is.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deleteFile")
    public ResponseResult<Void>  deleteFileByFunctionIdAndFileName(@RequestParam(value="functionId") String functionId ,@RequestParam(value="fileName")String fileName){
        commonService.deleteFileByFunctionIdAndFileName(functionId,fileName);
        return ResponseResult.success("删除成功");
    }
    /**
     * 根据routeName 抓取owner列表
     * @return list
     */
    @GetMapping("/getFunctionListByRouteName")
    public ResponseResult<List<FunctionList>> getFunctionListByRouteName(){
        try{
            String menuId = jwtUtils.getCurrentRouteName();
            List<FunctionList> data = commonService.GetFunctionListByRouteName(menuId);
            return  ResponseResult.success(data);
        }catch(Exception e){
            log.error("获取页面的owner 列表失败",e);
            return  ResponseResult.fail("查询失败");
        }
    }





    /**
     * 根据 获取页面Owner 列表 for page show
     * @param functionId 列表id
     * @return list
     */
    @GetMapping("/GetFunctionListByFunctionId")
    public ResponseResult<FunctionList> getFunctionListByFunctionId(@RequestParam(value="functionId") String functionId){
        try{
            FunctionList data = commonService.getFunctionListByFunctionId(functionId);
            return  ResponseResult.success(data);
        }catch(Exception e){
            log.error("获取页面的owner 列表失败",e);
            return  ResponseResult.fail("查询失败");
        }
    }

    /**
     * 根据 获取页面Owner 列表 for form show
     * @param functionId 列表id
     * @return list
     */
    @GetMapping("/getFunctionInfoDetailsByFunctionId")
    public ResponseResult<FunctionList> getFunctionInfoDetailsByFunctionId(@RequestParam(value="functionId") String functionId){
        try{
            FunctionList data = commonService.getFunctionInfoDetailsByFunctionId(functionId);
            return  ResponseResult.success(data);
        }catch(Exception e){
            log.error("获取页面的owner 列表失败",e);
            return  ResponseResult.fail("查询失败");
        }
    }

    /**
     * 插入每个functionList中的 owner/developer/tips等数据
     * @param functionList 数据内容
     * @return 返回是否成功
     */
    @PostMapping("/InsertFunctionList")
    public ResponseResult<Void> insertFunctionList(@RequestBody FunctionList functionList,@RequestParam("functionId") String functionId){
        try{
            if(!jwtUtils.havePermission(functionId)){
                return  ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
            functionList.setUpdateUser(jwtUtils.getUsernameFromToken());
             commonService.insertFunctionList(functionList);
            return  ResponseResult.success("插入成功");
        }catch(Exception e){
            log.error("获取页面的owner 列表失败",e);
            return  ResponseResult.fail("查询失败");
        }
    }

    /**
     * FOR 公共存储点击率Log;
     * @return
     */
    @PostMapping("/insertClickRateLog")
    public ResponseResult<Void> insertClickRateLog(){
        try{
            clickLogReader.getClickLog();
            return  ResponseResult.success("插入成功");
        }catch(Exception e){
            log.error("clickRate插入失败",e);
            return  ResponseResult.fail("插入失败");
        }
    }


    @PostMapping("/getMenuList")
    public ResponseResult<List<MenuInfo>> getMenuList(@RequestBody Map<String, String> map){
        try{
            List<MenuInfo> data = commonService.getMenuList(map);
            return  ResponseResult.success(data);
        }catch(Exception e){
            log.error("获取页面的目录 列表失败",e);
            return  ResponseResult.fail("查询目录失败");
        }
    }








}
