package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.pojo.WatHoldLot;
import mfg.reporting.pojo.WatHoldLotChart;
import mfg.reporting.pojo.WatHoldLotSummary;
import mfg.reporting.service.WatHoldLotService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@RestController
@RequestMapping("/watHoldLot")
@Slf4j
public class WatHoldLotController {

    @Resource
    public WatHoldLotService watHoldLotService;


    @GetMapping("/getWatHoldLotList")
    public ResponseResult<List<WatHoldLot>> getWatHoldLotList(String module,String type){
        try {
            List<WatHoldLot> list = watHoldLotService.getWatHoldLotList(module,type);
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            return ResponseResult.fail("查询失败:"+e.getMessage());
        }
    }

    @PostMapping("/mergeHoldLotDetailsInfo")
    public ResponseResult<Void> mergeHoldLotDetailsInfo(@RequestBody Map<String, Object> map){
        try {
            watHoldLotService.mergeWatHoldLot(map);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            return ResponseResult.fail("更新失败:"+e.getMessage());
        }
    }

    @GetMapping("/getWatHoldLotSummary")
    public ResponseResult<List<WatHoldLotSummary>> getWatHoldLotSummary(){
        try {
            List<WatHoldLotSummary> list = watHoldLotService.getWatHoldLotSummary();
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            return ResponseResult.fail("查询失败:"+e.getMessage());
        }
    }

    @GetMapping("/getWatHoldLotSummaryChart")
    public ResponseResult<List<WatHoldLotChart>> getWatHoldLotSummaryChart(){
        try {
            List<WatHoldLotChart> list = watHoldLotService.getWatHoldLotSummaryChart();
            return ResponseResult.success("查询成功",list);
        } catch (Exception e) {
            return ResponseResult.fail("查询失败:"+e.getMessage());
        }
    }


    @GetMapping("/downloadDetailsExcel")
    public void downloadDetailsExcel( HttpServletResponse response,String module,String type) {
        List<WatHoldLot> list = watHoldLotService.getWatHoldLotList(module,type);
        try(ByteArrayOutputStream ignored = new ByteArrayOutputStream()) {
            // 清空 response
            response.reset();
            response.setCharacterEncoding("UTF-8");

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("demandsList.xlsx", StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");


            EasyExcel.write(response.getOutputStream(), WatHoldLot.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("导出列表")
                    .doWrite(list);

        }catch (Exception e) {
            log.info("e:{}",e.toString());
        }
    }
}
