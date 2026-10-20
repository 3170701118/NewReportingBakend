package mfg.reporting.controller;


import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.N2PurgeInfoHourlyPO;
import mfg.reporting.service.N2PurgeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/n2purge")
@Slf4j
public class N2PurgeController {
    @Resource
    public N2PurgeService n2PurgeService;

    @Resource
    public JwtUtils jwtUtils;


    @GetMapping("/getN2PurgeInfoHourly")
    @SystemLog(function_id = "N2PurgePage",operateType = "select")
    public ResponseResult<List<N2PurgeInfoHourlyPO>> getN2PurgeInfoHourly(
            @RequestParam(required = false, defaultValue = "N1A1" ,value = "location")
            String location
    ){
        List<N2PurgeInfoHourlyPO> list = n2PurgeService.getN2PurgeInfoHourly(location);
        return ResponseResult.success("查询成功",list);
    }

    @GetMapping("/getN2PurgeInfoHourly2")
    @SystemLog(function_id = "N2PurgePage",operateType = "select")
    public ResponseResult<List<N2PurgeInfoHourlyPO>> getN2PurgeInfoHourly2(
            @RequestParam(required = false, defaultValue = "N1A1" ,value = "location")
            String location
    ){
        List<N2PurgeInfoHourlyPO> list = n2PurgeService.getN2PurgeInfoHourly2(location);
        return ResponseResult.success("查询成功",list);
    }


    @GetMapping("/getN2PurgetSummaryInfo")
    @SystemLog(function_id = "N2PurgePage",operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getN2PurgetSummaryInfo(@RequestParam(required = false, defaultValue = "N1A1" ,value = "location") String location){
        List<Map<String, Object>> list = n2PurgeService.getN2PurgetSummaryInfo(location);
        return ResponseResult.success("查询成功",list);
    }



    @PostMapping("/updateFcstDoneTimeByLotId")
    @SystemLog(function_id = "N2PurgePage",operateType = "update")
    public ResponseResult<Void> updateFcstDoneTimeByLotId(@RequestBody Map<String,String> data){
        try{
            if(jwtUtils.havePermission(data.get("functionId"))){
                String updateUser = jwtUtils.getUsernameFromToken();
                n2PurgeService.updateFcstDoneTimeByLotId(data.get("lotId"),data.get("fcstDoneTime"),updateUser);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        }catch (Exception e){
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }


    @PostMapping("/downLoadExcel")
    public void downLoadExcel( HttpServletResponse response ,@RequestBody Map<String,String> data){
        List<N2PurgeInfoHourlyPO> list = n2PurgeService.getN2PurgeInfoHourly(data.get("location"));
        try(ByteArrayOutputStream ignored = new ByteArrayOutputStream()) {
            // 清空 response
            response.reset();
            response.setCharacterEncoding("UTF-8");

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("demandsList.xlsx", StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");


            EasyExcel.write(response.getOutputStream(), N2PurgeInfoHourlyPO.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("导出列表")
                    .doWrite(list);

        }catch (Exception e) {
            log.info("e:{}",e.toString());
        }
    }



}
