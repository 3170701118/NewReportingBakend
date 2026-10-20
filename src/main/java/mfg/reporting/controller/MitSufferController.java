package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.MitSufferService;
import mfg.reporting.service.NtoPathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/MitSuffer")
@Slf4j
public class MitSufferController {
    @Resource
    public MitSufferService mitSufferService;

    /***
     * Summary页面
     * @return
     */
    @SystemLog(function_id = "mitSufferCT",operateType = "select")
    @GetMapping("/getMitSummary")
    public ResponseResult<List<Map<String,Object>>> getMitSummary(@RequestParam(value = "fab") String fab){
        try{
            List<Map<String,Object>> list = mitSufferService.getMitSummary(fab);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询MitSummary失败",e);
            return  ResponseResult.fail("MitSummary失败");
        }
    }

    /***
     * Summary页面不区分Product Status
     * @return
     */
    @SystemLog(function_id = "mitSufferCT",operateType = "select")
    @GetMapping("/getMitSummaryNon")
    public ResponseResult<List<Map<String,Object>>> getMitSummaryNon(@RequestParam(value = "fab") String fab){
        try{
            List<Map<String,Object>> list = mitSufferService.getMitSummaryNon(fab);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询MitSummaryNon失败",e);
            return  ResponseResult.fail("MitSummaryNon失败");
        }
    }

    /***
     * 取日期
     * @return
     */
    @SystemLog(function_id = "mitSufferCT",operateType = "select")
    @GetMapping("/getShiftDate")
    public ResponseResult<List<Map<String,Object>>> getShiftDate(){
        try{
            List<Map<String,Object>> list = mitSufferService.getShiftDate();
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询getShiftDate失败",e);
            return  ResponseResult.fail("getShiftDate失败");
        }
    }

    @SystemLog(function_id = "mitSufferCT",operateType = "select")
    @GetMapping("/getMitAllSummary")
    public ResponseResult<List<Map<String,Object>>> getMitAllSummary(@RequestParam(value = "fab") String fab){
        try{
            List<Map<String,Object>> list = mitSufferService.getMitAllSummary(fab);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询MitAllSummary失败",e);
            return  ResponseResult.fail("MitAllSummary失败");
        }
    }


}
