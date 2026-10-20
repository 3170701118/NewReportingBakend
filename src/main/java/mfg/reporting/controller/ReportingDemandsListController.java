package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.CommonUtil.UploadUtil;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.ReportingDemandListVO;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import mfg.reporting.Exception.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.pojo.ReportingDemandListPO;
import mfg.reporting.service.ReportingDemandsListService;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author gszhang
 */
@RestController
@RequestMapping("/reportingDemands")
@Slf4j
public class ReportingDemandsListController {

  @Resource
  public ReportingDemandsListService rdlService;

  @Resource
  public UploadUtil uploadUtil;

  @Resource
  public JwtUtils jwtUtils;

  @PostMapping("/InsertDemands")
  public ResponseResult<Map<String, Object>> insertDemands(@RequestBody ReportingDemandListPO rdl,@RequestParam(value = "functionId") String functionId) {
    try {
      if(jwtUtils.havePermission(functionId)){
        rdlService.insertDemand(rdl);
        return ResponseResult.success("插入成功");
      }else{
        return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
      }
    } catch (Exception e) {
      log.error("插入失败", e);
      return ResponseResult.fail("插入失败");
    }
  }

  @PostMapping("/SelectDemandsList")
  @SystemLog(function_id ="demandsList",operateType = "SELECT")
  public ResponseResult<List<ReportingDemandListPO>> selectDemandsList(@RequestBody ReportingDemandListPO rdl) {
    try {
      List<ReportingDemandListPO> data = rdlService.selectDemandsList(rdl);
      return ResponseResult.success(data);
    } catch (Exception e) {
      log.error("查询失败", e);
      return ResponseResult.fail("查询失败");
    }
  }

  @GetMapping("/GetDemandsInfoById")
  public ResponseResult<ReportingDemandListPO> getDemandsInfoById(@RequestParam(value = "demandsId") String demandsId) {
    try {
      ReportingDemandListPO data = rdlService.getDemandsInfoById(demandsId);
      return ResponseResult.success(data);
    } catch (Exception e) {
      log.error("查询失败", e);
      return ResponseResult.fail("查询失败");
    }
  }

  @GetMapping("/DeleteDemandsById")
  public ResponseResult deleteDemandsById(@RequestParam(value = "demandsId") String demandsId) {
    try {
      rdlService.deleteDemandsById(demandsId);
      return ResponseResult.success("删除成功");
    } catch (Exception e) {
      log.error("查询失败", e);
      return ResponseResult.fail("删除失败");
    }
  }


  @PostMapping("/UploadDemandsExcel")
  @ResponseBody
  public ResponseResult uploadDemandsExcel(MultipartFile file, String source, String functionId) {
    //先打印参数
    try {
      if (jwtUtils.havePermission(functionId)) {
        rdlService.insertDemandListExcel(file);
        uploadUtil.uploadFileManager(file, functionId,source);
        return ResponseResult.success("上传成功");
      } else {
        //未授权
        return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
      }
    } catch (Exception e) {
      return ResponseResult.fail("上传失败");
    }
  }


  @PostMapping("/downloadDemandsExcel")
  public void uploadDemandsExcel( HttpServletResponse response) {
      List<ReportingDemandListVO> list = rdlService.selectDemandsListAll();
      try(ByteArrayOutputStream ignored = new ByteArrayOutputStream()) {
        // 清空 response
        response.reset();
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("demandsList.xlsx", StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");


        EasyExcel.write(response.getOutputStream(), ReportingDemandListVO.class)
                .autoCloseStream(Boolean.FALSE)
                .sheet("导出列表")
                .doWrite(list);

    }catch (Exception e) {
        log.info("e:{}",e.toString());
    }
  }

}
