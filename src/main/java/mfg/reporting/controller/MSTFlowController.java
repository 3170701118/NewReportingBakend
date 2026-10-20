package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.dto.MstFlowSummaryDTO;
import mfg.reporting.dto.MstFlowWipDTO;
import mfg.reporting.dto.MstFlowLotOpeHsDTO;
import mfg.reporting.dto.MstFlowMoveByProdgDTO;
import mfg.reporting.dto.MstFlowMoveByEqpgDTO;
import mfg.reporting.dto.MstFlowQtimeDTO;
import mfg.reporting.dto.MstFlowSamplingDTO;
import mfg.reporting.dto.MstFlowPathDTO;
import mfg.reporting.dto.MstFlowDybDTO;
import mfg.reporting.dto.MstFlowSgsDTO;
import mfg.reporting.service.MSTFlowService;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mstFlow")
@Slf4j
public class MSTFlowController {
    @Resource
    public MSTFlowService mstFlowService;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 查询MSTFlow首页资料
     * @param tech
     * @param prodg
     * @param module
     * @param eqpg
     * @param neng
     * @param view
     * @return
     */
    @SystemLog(function_id = "NEXMSTFLOW",operateType = "select")
    @GetMapping("/getMstFlowSummary")
    public ResponseResult<List<MstFlowSummaryDTO>> getMstFlowSummary(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="eqpg") String eqpg
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowSummaryDTO> list = mstFlowService.getMstFlowSummary(tech,prodg,module,eqpg,neng,view);
            // 检查列表是否非空且第一行的UPDATE_TIME为空
            if (!list.isEmpty() && list.get(0).getUpdateTime() == null) {
                return ResponseResult.fail(ResponseCode.MSTFLOW_NOT_UPDATE);
            } else if (list.isEmpty()) {
                return ResponseResult.fail(ResponseCode.MSTFLOW_NO_FLOW);
            } else {
                return ResponseResult.success("查询成功", list);
            }
        }catch (Exception e){
            log.error("查询MSTFlow 失败",e);
            return  ResponseResult.fail("MSTFlow 失败");
        }
    }

    /**
     * 查询WIP Deatil资料
     * @param tech
     * @param prodg
     * @param state
     * @param neng
     * @param view
     * @return
     */
    @SystemLog(function_id = "msfFlowWipStatusDetail",operateType = "select")
    @GetMapping("/getWipDetailList")
    public ResponseResult<List<MstFlowWipDTO>> getWipDetailList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="state") String state
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowWipDTO> list = mstFlowService.getWipDetailList(tech,prodg,state,openo,neng,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询MSTFlow WIP失败",e);
            return  ResponseResult.fail("MSTFlow WIP失败");
        }
    }

    /**
     * 查询BackupWIP Deatil资料
     * @param tech
     * @param prodg
     * @param state
     * @param neng
     * @param view
     * @return
     */
    @SystemLog(function_id = "msfFlowWipStatusDetail",operateType = "select")
    @GetMapping("/getBackupWipDetailList")
    public ResponseResult<List<MstFlowWipDTO>> getBackupWipDetailList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="state") String state
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowWipDTO> list = mstFlowService.getBackupWipDetailList(tech,prodg,state,openo,neng,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询MSTFlow WIP失败",e);
            return  ResponseResult.fail("MSTFlow WIP失败");
        }
    }

    /**
     * 查询Move By Prodg Deatil资料
     * @param tech
     * @param prodg
     * @param date
     * @param openo
     * @param neng
     * @param view
     * @return
     */
    @SystemLog(function_id = "mstFlowMoveDetail",operateType = "select")
    @GetMapping("/getMoveByProdgList")
    public ResponseResult<List<MstFlowMoveByProdgDTO>> getMoveByProdgList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="date") String date
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowMoveByProdgDTO> list = mstFlowService.getMoveByProdgList(tech,prodg,date,openo,neng,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询MSTFlow Move失败",e);
            return  ResponseResult.fail("MSTFlow Move失败");
        }
    }

    /**
     * 查询Move By Eqpg Deatil资料
     * @param tech
     * @param prodg
     * @param date
     * @param openo
     * @param neng
     * @param view
     * @return
     */
    @SystemLog(function_id = "mstFlowMoveDetail",operateType = "select")
    @GetMapping("/getMoveByEqpgList")
    public ResponseResult<List<MstFlowMoveByEqpgDTO>> getMoveByEqpgList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="date") String date
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowMoveByEqpgDTO> list = mstFlowService.getMoveByEqpgList(tech,prodg,date,openo,neng,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询MSTFlow Move失败",e);
            return  ResponseResult.fail("MSTFlow Move失败");
        }
    }

    /**
     * 查询LOT OPEHS资料
     * @param lotid
     * @param prodspecid
     * @return
     */
    @SystemLog(function_id = "mstFlowLotHistoryDetail",operateType = "select")
    @GetMapping("/getLotOpeHSList")
    public ResponseResult<List<MstFlowLotOpeHsDTO>> getLotOpeHSList(
            @RequestParam(value="lotid") String lotid
            ,@RequestParam(value="prodspecid") String prodspecid
    ){
        try{
            List<MstFlowLotOpeHsDTO> list = mstFlowService.getLotOpeHSList(lotid,prodspecid);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询LotOpeHS失败",e);
            return  ResponseResult.fail("LotOpeHS失败");
        }
    }

    /**
     * 查询QTIME资料
     * @param tech
     * @param prodg
     * @param openo
     * @return
     */
    @SystemLog(function_id = "mstFlowQTimeDetail",operateType = "select")
    @GetMapping("/getQtimeDetailList")
    public ResponseResult<List<MstFlowQtimeDTO>> getQtimeDetailList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="openo") String openo
    ){
        try{
            List<MstFlowQtimeDTO> list = mstFlowService.getQtimeDetailList(tech,prodg,openo);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询Qtime失败",e);
            return  ResponseResult.fail("Qtime失败");
        }
    }

    /**
     * 查询Sampling资料
     * @param tech
     * @param prodg
     * @param openo
     * @return
     */
    @SystemLog(function_id = "mstFlowSamplingRateDetail",operateType = "select")
    @GetMapping("/getSamplingRateDetailList")
    public ResponseResult<List<MstFlowSamplingDTO>> getSamplingRateDetailList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="openo") String openo
    ){
        try{
            List<MstFlowSamplingDTO> list = mstFlowService.getSamplingRateDetailList(tech,prodg,openo);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询Sampling失败",e);
            return  ResponseResult.fail("Sampling失败");
        }
    }

    /**
     * 查询Path资料
     * @param prodg
     * @param prodspecid
     * @param openo
     * @param mainpdid
     * @return
     */
    @SystemLog(function_id = "mstFlowpathDetail",operateType = "select")
    @GetMapping("/getpathDetailList")
    public ResponseResult<List<MstFlowPathDTO>> getpathDetailList(
            @RequestParam(value="prodg") String prodg
            ,@RequestParam(value="prodspecid") String prodspecid
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="mainpdid") String mainpdid
    ){
        try{
            List<MstFlowPathDTO> list = mstFlowService.getpathDetailList(prodg,prodspecid,openo,mainpdid);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询Path失败",e);
            return  ResponseResult.fail("Path失败");
        }
    }

    /**
     * 查询DYB资料
     * @param tech
     * @param prodg
     * @return
     */
    @SystemLog(function_id = "mstFlowDybLotDetail",operateType = "select")
    @GetMapping("/getDybLotDetailList")
    public ResponseResult<List<MstFlowDybDTO>> getDybLotDetailList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowDybDTO> list = mstFlowService.getDybLotDetailList(tech,prodg,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询DYB失败",e);
            return  ResponseResult.fail("DYB失败");
        }
    }

    /**
     * 查询SGS EQP Deatil资料
     * @param tech
     * @param prodg
     * @param neng
     * @param view
     * @return
     */
    @SystemLog(function_id = "msfFlowSgsEqpDetail",operateType = "select")
    @GetMapping("/getSgsEqpList")
    public ResponseResult<List<MstFlowSgsDTO>> getSgsEqpList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowSgsDTO> list = mstFlowService.getSgsEqpList(tech,prodg,openo,neng,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询MSTFlow SGS EQP失败",e);
            return  ResponseResult.fail("MSTFlow SGS EQP失败");
        }
    }

    /**
     * 查询SGS WIP Deatil资料
     * @param tech
     * @param prodg
     * @param neng
     * @param view
     * @param sgseqp
     * @return
     */
    @SystemLog(function_id = "msfFlowWipStatusDetail",operateType = "select")
    @GetMapping("/getSgsWipDetailList")
    public ResponseResult<List<MstFlowWipDTO>> getSgsWipDetailList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="sgseqp") String sgseqp
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="neng") String neng
            ,@RequestParam(value="view") String view
    ){
        try{
            List<MstFlowWipDTO> list = mstFlowService.getSgsWipDetailList(tech,prodg,sgseqp,openo,neng,view);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询MSTFlow SGS WIP失败",e);
            return  ResponseResult.fail("MSTFlow SGS WIP失败");
        }
    }


    /**
     * tech下拉框
     * @return
     */
    @SystemLog(function_id = "NEXMSTFLOW",operateType = "select")
    @GetMapping("/getTechList")
    public ResponseResult<List<Map<String,Object>>> getTechList(){
        try{
            List<Map<String,Object>> list = mstFlowService.getTechList();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询TechList失败");
        }
    }

    /**
     * Prodg下拉框
     * @param tech
     * @return
     */
    @SystemLog(function_id = "NEXMSTFLOW",operateType = "select")
    @GetMapping("/getProdgList")
    public ResponseResult<List<Map<String,Object>>> getProdgList(
            @RequestParam(value="tech") String tech
    ){
        try{
            List<Map<String,Object>> list = mstFlowService.getProdgList(tech);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询ProdgList失败");
        }
    }

    /**
     * eqpg下拉框
     * @param tech
     * @param prodg
     * @param module
     * @return
     */
    @SystemLog(function_id = "NEXMSTFLOW",operateType = "select")
    @GetMapping("/getEqpgList")
    public ResponseResult<List<Map<String,Object>>> getEqpgList(
            @RequestParam(value="tech") String tech
            ,@RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "module") String module
    ){
        try{
            List<Map<String,Object>> list = mstFlowService.getEqpgList(tech,prodg,module);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询EqpgList失败");
        }
    }


}
