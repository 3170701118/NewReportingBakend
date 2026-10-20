package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.pojo.NTOPathMFGMaintainVO;
import mfg.reporting.pojo.NTOPathProdgDetailVO;
import mfg.reporting.service.NtoPathService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/NtoPath")
@Slf4j
public class NtoPathController {
    @Resource
    public NtoPathService ntoPathService;

    @Resource
    public JwtUtils jwtUtils;

    @Resource
    public SendEmailController sendEmailController;


    /***
     * 查询产品维护
     * @return
     */
    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoProdgMaintain")
    public ResponseResult<List<Map<String,Object>>> getNtoProdgMaintain(){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoProdgMaintain();
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询NtoProdgMaintain失败",e);
            return  ResponseResult.fail("NtoProdgMaintain失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "delete")
    @PostMapping("/deleteNtoProdgMaintain")
    public ResponseResult<Map<String, Object>> deleteNtoProdgMaintain(
            @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID

    ) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                ntoPathService.deleteNtoProdgMaintain(prodg,chipbody,UPDATE_USER);
                return ResponseResult.success("删除成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("删除失败", e);
            return ResponseResult.fail("删除失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "insert")
    @PostMapping("/insertNtoProdgMaintain")
    public ResponseResult<Map<String, Object>> insertNtoProdgMaintain(
            @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                ntoPathService.insertNtoProdgMaintain(prodg,chipbody,UPDATE_USER);
                //更新input flag
                ntoPathService.updateInputFlag();
                ntoPathService.updateInputFlagOther();
                return ResponseResult.success("插入成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("插入失败", e);
            return ResponseResult.fail("插入失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getProdgList")
    public ResponseResult<List<Map<String,Object>>> getProdgList(){
        try{
            List<Map<String,Object>> list = ntoPathService.getProdgList();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询ProdgList失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoMfgPathMaintain")
    public ResponseResult<List<NTOPathMFGMaintainVO>> getNtoMfgPathMaintain(@RequestParam(value = "prodg") String prodg){
        try{
            List<NTOPathMFGMaintainVO> list = ntoPathService.getNtoMfgPathMaintain(prodg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoMfgPathMaintain失败");
        }
    }

    @PostMapping("/downloadNTOMFGMaintain")
    public void downloadNTOMFGMaintain(
            HttpServletResponse response
            ,@RequestParam(value = "prodg") String prodg
    ) {
        List<NTOPathMFGMaintainVO> list = ntoPathService.getNtoMfgPathMaintain(prodg);
        try(ByteArrayOutputStream ignored = new ByteArrayOutputStream()) {
            // 清空 response
            response.reset();
            response.setCharacterEncoding("UTF-8");

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("NtoMfgPathMaintain.xlsx", StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");


            EasyExcel.write(response.getOutputStream(), NTOPathMFGMaintainVO.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("导出列表")
                    .doWrite(list);

        }catch (Exception e) {
            log.info("e:{}",e.toString());
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "update")
    @PostMapping("/updateNtoPmc")
    public ResponseResult<Map<String, Object>> updateNtoPmc(
            @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
            ,@RequestParam(value = "fab") String fab
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                ntoPathService.updateNtoPmc(prodg,chipbody,fab,UPDATE_USER);
                return ResponseResult.success("修改成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("插入失败", e);
            return ResponseResult.fail("修改失败");
        }
    }


    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getEqpList")
    public ResponseResult<List<Map<String,Object>>> getEqpList(@RequestParam(value = "eqpg") String eqpg){
        try{
            List<Map<String,Object>> list = ntoPathService.getEqpList(eqpg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getEqpList失败", e);
            return  ResponseResult.fail("查询EqpList失败");
        }
    }


    @SystemLog(function_id = "ntoPathControl",operateType = "update")
    @PostMapping("/updateNtoMfg")
    public ResponseResult<Map<String, Object>> updateNtoMfg(
            @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
            ,@RequestParam(value = "eqpg") String eqpg
            ,@RequestParam(value = "eqplist") String eqplist
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                ntoPathService.updateNtoMfg(prodg,chipbody,eqpg,eqplist,UPDATE_USER);
                return ResponseResult.success("修改成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("修改失败", e);
            return ResponseResult.fail("修改失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoModuleList")
    public ResponseResult<List<Map<String,Object>>> getNtoModuleList(@RequestParam(value = "prodg") String prodg){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoModuleList(prodg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoModuleList失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSdeptList")
    public ResponseResult<List<Map<String,Object>>> getNtoSdeptList(
            @RequestParam(value = "prodg") String prodg,
            @RequestParam(value = "deptid") String deptid){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSdeptList(prodg,deptid);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSdeptList失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSummaryModule")
    public ResponseResult<List<Map<String,Object>>> getNtoSummaryModule(){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSummaryModule();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSummaryModule失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSummaryModuleProdg")
    public ResponseResult<List<Map<String,Object>>> getNtoSummaryModuleProdg(){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSummaryModuleProdg();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSummaryModuleProdg失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSummaryModuleEqpg")
    public ResponseResult<List<Map<String,Object>>> getNtoSummaryModuleEqpg(
            @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "deptid") String deptid
            ,@RequestParam(value = "sdept") String sdept
    ){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSummaryModuleEqpg(prodg,deptid,sdept);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSummaryModuleProdg失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSummaryProdg")
    public ResponseResult<List<Map<String,Object>>> getNtoSummaryProdg(){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSummaryProdg();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSummaryProdg失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoProdgList")
    public ResponseResult<List<Map<String,Object>>> getNtoProdgList(){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoProdgList();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoProdgList失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSummaryProdgEqpg")
    public ResponseResult<List<Map<String,Object>>> getNtoSummaryProdgEqpg(@RequestParam(value = "prodg") String prodg){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSummaryProdgEqpg(prodg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSummaryModuleProdg失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoSummaryProdgTable")
    public ResponseResult<List<Map<String,Object>>> getNtoSummaryProdgTable(){
        try{
            List<Map<String,Object>> list = ntoPathService.getNtoSummaryProdgTable();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoSummaryProdgTable失败");
        }
    }

    @SystemLog(function_id = "ntoPathControl",operateType = "select")
    @GetMapping("/getNtoProdgDetail")
    public ResponseResult<List<NTOPathProdgDetailVO>> getNtoProdgDetail(
            @RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
    ){
        try{
            List<NTOPathProdgDetailVO> list = ntoPathService.getNtoProdgDetail(prodg,chipbody);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询NtoProdgDetail失败");
        }
    }

    @PostMapping("/downloadNTOProdgDetail")
    public void downloadNTOProdgDetail(
            HttpServletResponse response
            ,@RequestParam(value = "prodg") String prodg
            ,@RequestParam(value = "chipbody") String chipbody
    ) {
        List<NTOPathProdgDetailVO> list = ntoPathService.getNtoProdgDetail(prodg,chipbody);
        try(ByteArrayOutputStream ignored = new ByteArrayOutputStream()) {
            // 清空 response
            response.reset();
            response.setCharacterEncoding("UTF-8");

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("NtoProdgDetail.xlsx", StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");


            EasyExcel.write(response.getOutputStream(), NTOPathProdgDetailVO.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("导出列表")
                    .doWrite(list);

        }catch (Exception e) {
            log.info("e:{}",e.toString());
        }
    }


}
