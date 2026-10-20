package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.CommonUtil.UploadUtil;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.dto.RevenueChartDTO;
import mfg.reporting.dto.RevenueDTO;
import mfg.reporting.dto.RevenueDetailsLotDTO;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.RevenueService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@RestController
@RequestMapping("/revenue")
@Slf4j
public class RevenueController {

    @Resource
    public RevenueService revenueService;

    @Resource
    public JwtUtils jwtUtils;

    @Resource
    public UploadUtil uploadUtil;

    /**
     *
     * @return List
     */
    @PostMapping("/getRevenueDetails")
    @SystemLog(function_id = "revenueTitle",operateType = "select")
    public ResponseResult<List<RevenueDTO>> getRevenueDetails(@RequestBody Map<String,Object> data){
        try {
            List<RevenueDTO> list = revenueService.getRevenueDetails(data);
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            log.error("查询失败：{}",e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getRevenueChart")
    @SystemLog(function_id = "revenueTitle",operateType = "select")
    public ResponseResult<List<RevenueChartDTO>> getRevenueChart(){
        try {
            List<RevenueChartDTO> list = revenueService.getRevenueChart();
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            log.error("查询失败: {}",e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/getWtHoldWipDetailByProdg1")
    @SystemLog(function_id = "revenueTitle",operateType = "select")
    public ResponseResult<List<RevenueDetailsLotDTO>> getWtHoldWipDetailByProdg1(@RequestBody RevenueDTO revenueDTO){
        try {
            List<RevenueDetailsLotDTO> list = revenueService.getWtHoldWipDetailByProdg1(revenueDTO.getProdg1(),revenueDTO.getLine());
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            log.error("查询失败 : {}",e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/getRevenueDiffLotDetail")
    @SystemLog(function_id = "revenueTitle",operateType = "select")
    public ResponseResult<List<RevenueDetailsLotDTO>> getRevenueDiffLotDetail(@RequestBody RevenueDTO revenueDTO){
        try {
            List<RevenueDetailsLotDTO> list = revenueService.getRevenueDiffLotDetail(revenueDTO.getProdg1(),revenueDTO.getLine());
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            log.error("查询失败  :  {}",e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }


    @PostMapping("/UploadRevenueTemplate")
    @ResponseBody
    public ResponseResult<Void> uploadRevenueTemplate(MultipartFile file, String functionId) {
        //先打印参数
        try {
            if (jwtUtils.havePermission(functionId)) {
                 revenueService.UploadRevenueTemplate(file);
                 // uploadUtil.uploadFileManager(file, functionId,"upload");
                return ResponseResult.success("上传成功");
            } else {
                //未授权
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return ResponseResult.fail(e.getMessage());
        }


    }


    @GetMapping("/getSelectionInfo")
    @SystemLog(function_id = "revenueTitle",operateType = "select")
    public ResponseResult<Map<String,List<CommonSelectDTO>>> getSelectionInfo(){
        try {
            return ResponseResult.success("查询成功",revenueService.getSelectionInfo());
        } catch (Exception e) {
            log.error("查询失败 ： {}",e.getMessage());
            return ResponseResult.fail("查询失败");
        }
    }
}
