package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.EtchUploadService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/etchUpload")
@Slf4j
public class EtchUploadController {

    @Resource
    public JwtUtils jwtUtils;

    @Resource
    public EtchUploadService euservice;

    //上传
    @RequestMapping("/stdTcUpload")
    @ResponseBody
    public ResponseResult uploadEtchStdTc(MultipartFile file, String source, String functionId) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                euservice.insertEtchStdTc(file);

                log.info("上传人:{}， 上传时间:{},时间==>上传ETCH_STDTC_OPD", jwtUtils.getUsernameFromToken(), System.currentTimeMillis());
                return ResponseResult.success("上传成功");
            } else {
                //未授权
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return ResponseResult.fail("上传失败");
        }
    }

    //上传Input Plan

    //上传
    @RequestMapping("/inputPlanUpload")
    @ResponseBody
    public ResponseResult inputPlanUpload(MultipartFile file, String source, String functionId) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                euservice.insertInputPlanDailyNewForETCH(file);

                log.info("上传人:{}，上传时间:{},时间==>上传ETCH_STDTC_OPD", jwtUtils.getUsernameFromToken(), System.currentTimeMillis());
                return ResponseResult.success("上传成功");
            } else {
                //未授权
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return ResponseResult.fail("上传失败");
        }
    }

}
