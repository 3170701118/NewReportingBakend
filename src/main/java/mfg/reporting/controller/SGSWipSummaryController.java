package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.dto.SGSWipSummaryDTO;
import mfg.reporting.dto.SGSWipDetailDTO;
import mfg.reporting.service.SGSWipSummaryService;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sgsWipSummary")
@Slf4j
public class SGSWipSummaryController {
    @Resource
    public SGSWipSummaryService sgsWipSummaryService;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 查询SGS Wip Summary资料
     * @param eqpid
     * @param neng
     * @return
     */
    @SystemLog(function_id = "sgsWipSummary",operateType = "select")
    @GetMapping("/getSGSWipSummary")
    public ResponseResult<List<SGSWipSummaryDTO>> getSGSWipSummary(
            @RequestParam(value="eqpid") String eqpid
            ,@RequestParam(value="neng") String neng
    ){
        try{
            List<SGSWipSummaryDTO> list = sgsWipSummaryService.getSGSWipSummary(eqpid,neng);
                return ResponseResult.success("查询成功", list);

        }catch (Exception e){
            log.error("查询SGS Wip 失败",e);
            return  ResponseResult.fail("SGS Wip 失败");
        }
    }

    /**
     * 查询SGS Wip Detail资料
     * @param tech
     * @param sgsgroup
     * @param openo
     * @param eqpid
     * @param neng
     * @param type
     * @return
     */
    @SystemLog(function_id = "sgsWipSummary",operateType = "select")
    @GetMapping("/getSGSWipDetail")
    public ResponseResult<List<SGSWipDetailDTO>> getSGSWipDetail(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="sgsgroup") String sgsgroup
            ,@RequestParam(value="eqpid") String eqpid
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="type") String type
    ){
        try{
            List<SGSWipDetailDTO> list = sgsWipSummaryService.getSGSWipDetail(tech,openo,sgsgroup,eqpid,neng,type);
            return ResponseResult.success("查询成功", list);

        }catch (Exception e){
            log.error("查询SGS Lot List 失败",e);
            return  ResponseResult.fail("SGS Lot List 失败");
        }
    }


    /**
     * 查询SGS Wip Total Summary资料
     * @param eqpid
     * @param neng
     * @return
     */
    @SystemLog(function_id = "sgsWipSummary",operateType = "select")
    @GetMapping("/getSGSWipTotalSummary")
    public ResponseResult<List<SGSWipSummaryDTO>> getSGSWipTotalSummary(
            @RequestParam(value="eqpid") String eqpid
            ,@RequestParam(value="neng") String neng
    ){
        try{
            List<SGSWipSummaryDTO> list = sgsWipSummaryService.getSGSWipTotalSummary(eqpid,neng);
            return ResponseResult.success("查询成功", list);

        }catch (Exception e){
            log.error("查询SGS Wip 失败",e);
            return  ResponseResult.fail("SGS Wip 失败");
        }
    }

    /**
     * 查询SGS Wip Total Detail资料
     * @param tech
     * @param sgsgroup
     * @param openo
     * @param eqpid
     * @param neng
     * @param type
     * @return
     */
    @SystemLog(function_id = "sgsWipSummary",operateType = "select")
    @GetMapping("/getSGSWipTotalDetail")
    public ResponseResult<List<SGSWipDetailDTO>> getSGSWipTotalDetail(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="sgsgroup") String sgsgroup
            ,@RequestParam(value="eqpid") String eqpid
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="type") String type
    ){
        try{
            List<SGSWipDetailDTO> list = sgsWipSummaryService.getSGSWipTotalDetail(tech,openo,sgsgroup,eqpid,neng,type);
            return ResponseResult.success("查询成功", list);

        }catch (Exception e){
            log.error("查询SGS Lot List 失败",e);
            return  ResponseResult.fail("SGS Lot List 失败");
        }
    }


    /**
     * eqpg下拉框
     * @param fab
     * @return
     */
    @SystemLog(function_id = "sgsWipSummary",operateType = "select")
    @GetMapping("/getEqpIdList")
    public ResponseResult<List<Map<String,Object>>> getEqpIdList(
            @RequestParam(value="fab") String fab
    ){
        try{
            List<Map<String,Object>> list = sgsWipSummaryService.getEqpIdList(fab);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询EqpgList失败");
        }
    }


}
