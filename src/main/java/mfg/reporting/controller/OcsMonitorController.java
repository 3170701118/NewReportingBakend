package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.service.OcsMonitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: gszhang
 * @Date: 2026/05/14
 */
@RestController
@RequestMapping("/OcsMonitor")
@Slf4j
public class OcsMonitorController {

    @Resource
    public OcsMonitorService ocsMonitorService;

    @RequestMapping("/getOcsMonitorTableSummary")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorTableSummary(

            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "shiftDate" ,defaultValue = "") String shiftDate
            ,@RequestParam(value = "daynight" ,defaultValue = "") String daynight
    ){
        try{
            List<Map<String,Object>> list = ocsMonitorService.getOcsMonitorTableSummary(fab,shiftDate,daynight);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询NtoProdgMaintain失败",e);
            return  ResponseResult.fail("NtoProdgMaintain失败");
        }
    }

    @RequestMapping("/getOcsMonitorPlanDetails")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorPlanDetails(
            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "shiftDate" ,defaultValue = "") String shiftDate
            ,@RequestParam(value = "daynight" ,defaultValue = "") String daynight
            ,@RequestParam(value = "module" ,defaultValue = "") String module
            ,@RequestParam(value = "type" ,defaultValue = "") String type
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getOcsMonitorPlanDetails(fab,shiftDate,daynight,module,type);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询OcsMonitorPlanDetails失败",e);
            return  ResponseResult.fail("OcsMonitorPlanDetails失败");
        }
    }

    @RequestMapping("/getOcsMonitorStatusDetails")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorStatusDetails(
            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "shiftDate" ,defaultValue = "") String shiftDate
            ,@RequestParam(value = "daynight" ,defaultValue = "") String daynight
            ,@RequestParam(value = "module" ,defaultValue = "") String module
            ,@RequestParam(value = "type" ,defaultValue = "") String type
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getOcsMonitorStatusDetails(fab,shiftDate,daynight,module,type);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询OcsMonitorStatusDetails失败",e);
            return  ResponseResult.fail("查询OcsMonitorStatusDetails失败");
        }
    }

    @RequestMapping("/getOcsMonitorNGChartByEQPG")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorNGChartByEQPG(
            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "shiftDate" ,defaultValue = "") String shiftDate
            ,@RequestParam(value = "daynight" ,defaultValue = "") String daynight
            ,@RequestParam(value = "module" ,defaultValue = "") String module
            ,@RequestParam(value = "testFor" ,defaultValue = "") String testFor
    )
        {
            try{
                List<Map<String,Object>> list =
                        ocsMonitorService.getOcsMonitorNGChartByEQPG(fab,shiftDate,daynight,module,testFor);
                return  ResponseResult.success(list);
            }catch (Exception e){
                log.error("查询OcsMonitorNGChartByEQPG失败",e);
                return  ResponseResult.fail("查询OcsMonitorNGChartByEQPG失败");
            }
        }

        @PostMapping("/getOcsMonitorNGChartNew")
        @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
        public ResponseResult<List<Map<String,Object>>> getOcsMonitorNGChartNew(
                @RequestBody Map<String,Object> data
        )
        {
            try{
                List<Map<String,Object>> list =
                        ocsMonitorService.getOCSMonitorNGChartNew(data);
                return  ResponseResult.success(list);
            }catch (Exception e){
                log.error("查询OcsMonitorNGChartNew失败",e);
                return  ResponseResult.fail("查询OcsMonitorNGChartNew失败");
            }
        }


        @RequestMapping("/getOcsMonitorNGChartByeqpId")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorNGChartByeqpId(
            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "shiftDate" ,defaultValue = "") String shiftDate
            ,@RequestParam(value = "daynight" ,defaultValue = "") String daynight
            ,@RequestParam(value = "testFor" ,defaultValue = "") String testFor
            ,@RequestParam(value = "eqpg" ,defaultValue = "") String eqpg
    )
        {
            try{
                List<Map<String,Object>> list =
                        ocsMonitorService.getOcsMonitorNGChartByeqpId(fab,shiftDate,daynight,testFor,eqpg);
                return  ResponseResult.success(list);
            }catch (Exception e){
                log.error("查询OcsMonitorNGChartByeqpId失败",e);
                return  ResponseResult.fail("查询OcsMonitorNGChartByeqpId失败");
            }
        }

        @RequestMapping("/getOcsMonitorNGChartByDummy")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getOcsMonitorNGChartByDummy(
            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "shiftDate" ,defaultValue = "") String shiftDate
            ,@RequestParam(value = "daynight" ,defaultValue = "") String daynight
            ,@RequestParam(value = "testFor" ,defaultValue = "") String testFor
            ,@RequestParam(value = "eqpId" ,defaultValue = "") String eqpId
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getOcsMonitorNGChartByDummy(fab,shiftDate,daynight,testFor,eqpId);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询OcsMonitorNGChartByDummy失败",e);
            return  ResponseResult.fail("查询OcsMonitorNGChartByDummy失败");
        }
    }

    @PostMapping("/getNgTrendChartSummary")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getNgTrendChartSummary(
            @RequestBody Map<String,Object> data
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getNgTrendChartSummary(data);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询NgTrendChartSummary失败",e);
            return  ResponseResult.fail("查询NgTrendChartSummary失败");
        }
    }

    @RequestMapping("/getpurposeList")
    public ResponseResult<List<CommonSelectDTO>> getpurposeList(
            @RequestParam(value = "fab" ,defaultValue = "") String fab

    ){
        try{
            List<CommonSelectDTO> list =
                    ocsMonitorService.getpurposeList(fab);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("getpurposeList失败",e);
            return  ResponseResult.fail("查询getpurposeList失败");
        }
    }

    @RequestMapping("/geteqpgList")
    public ResponseResult<List<CommonSelectDTO>> geteqpgList(
            @RequestParam(value = "fab" ,defaultValue = "") String fab,
            @RequestParam(value = "module" ,defaultValue = "") String module
    ){
        try{
            List<CommonSelectDTO> list =
                    ocsMonitorService.geteqpgList(fab,module);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询geteqpgList失败",e);
            return  ResponseResult.fail("查询geteqpgList失败");
        }
    }

    @PostMapping("/getNgDetailsByLot")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getNgDetailsByLot(
            @RequestBody Map<String,Object> data
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getNgDetailsByLot(data);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询getNgDetailsByLot失败",e);
            return  ResponseResult.fail("查询getNgDetailsByLot失败");
        }
    }


    @RequestMapping("/getCompeteRatioDaily")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getCompeteRatioDaily(
            @RequestParam(value = "fab" ,defaultValue = "") String fab
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getCompeteRatioDaily(fab);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询getCompeteRatioDaily失败",e);
            return  ResponseResult.fail("查询getCompeteRatioDaily失败");
        }
    }


    @PostMapping("/getStayTimetrendChart")
    @SystemLog(function_id = "OcsMonitorTitle",operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getStayTimetrendChart(
            @RequestBody Map<String,Object> data
    ){
        try{
            List<Map<String,Object>> list =
                    ocsMonitorService.getStayTimetrendChart(data);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询getStayTimetrendChart失败",e);
            return  ResponseResult.fail("查询getStayTimetrendChart失败");
        }
    }






}
