package mfg.reporting.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.service.QtimeService;
import org.springframework.web.bind.annotation.*;
import mfg.reporting.pojo.QtimeOverDetailVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Qtime")
@Slf4j
public class QtimeController {
    @Resource
    public QtimeService qtimeService;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 查询首页QtimeOver资料
     * @param nengflag
     * @return
     */
    @SystemLog(function_id = "Homepage_QtimeOver",operateType = "select")
    @GetMapping("/getQtimeOverSummary")
    public ResponseResult<List<Map<String,Object>>> getQtimeOverSummary(
            @RequestParam(value="nengflag") String nengflag
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="tech") String tech
    ){
        try{
            List<Map<String,Object>> list = qtimeService.getQtimeOverSummary(nengflag,module,tech);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询QtimeOver 失败",e);
            return  ResponseResult.fail("QtimeOver 失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getQtimeOverDetail")
    public ResponseResult<List<QtimeOverDetailVO>> getQtimeOverDetail(
            @RequestParam(value="nengflag") String nengflag
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="tech") String tech
            ,@RequestParam(value="startdate") String startdate
            ,@RequestParam(value="enddate") String enddate
            ,@RequestParam(value="ifkeyin") String ifkeyin
            ,@RequestParam(value="targetmodule") String targetmodule
    ){
        try{

            List<QtimeOverDetailVO> list = qtimeService.getQtimeOverDetail(nengflag,module,tech,startdate,enddate,ifkeyin,targetmodule);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询QtimeOverDetail 失败",e);
            return  ResponseResult.fail("QtimeOverDetail 失败");
        }
    }





    /**
     * 编辑原因选项
     * @param type
     * @return
     */
    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getEditOption")
    public ResponseResult<List<Map<String,Object>>> getEditOption(@RequestParam(value="type") String type){
        try{
            List<Map<String,Object>> list = qtimeService.getEditOption(type);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询EditOption失败");
        }
    }

    /**
     * tech下拉框
     * @return
     */
    @SystemLog(function_id = "Homepage_QtimeOver",operateType = "select")
    @GetMapping("/getTechList")
    public ResponseResult<List<Map<String,Object>>> getTechList(){
        try{
            List<Map<String,Object>> list = qtimeService.getTechList();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询TechList失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "update")
    @PostMapping("/updateQtimeOverInfo")
    public ResponseResult<Map<String, Object>> updateQtimeOverInfo(
            @RequestParam(value = "SHIFT_DATE") String SHIFT_DATE
            ,@RequestParam(value = "LOT_ID") String LOT_ID
            ,@RequestParam(value = "CURRENT_OPE_NO") String CURRENT_OPE_NO
            ,@RequestParam(value = "TYPE") String TYPE
            ,@RequestParam(value = "KEYVALUE") String KEYVALUE
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String REMARK=jwtUtils.getUserInfoFromToken().getSdeptCname();
                String CNAME=jwtUtils.getUserInfoFromToken().getCname();
                REMARK = (REMARK != null && !REMARK.trim().isEmpty())
                        ? REMARK.trim() + "/" + CNAME
                        : CNAME;
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                qtimeService.updateQtimeOverInfo(SHIFT_DATE,LOT_ID,CURRENT_OPE_NO,TYPE,KEYVALUE,REMARK,UPDATE_USER);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }

    /**
     * 导出excel
     * @param response
     * @param nengflag
     * @param module
     * @param tech
     * @param startdate
     * @param enddate
     * @param ifkeyin
     */
    @PostMapping("/downloadQtimeOverExcel")
    public void downloadQtimeOverExcel(
            HttpServletResponse response
            ,@RequestParam(value="nengflag") String nengflag
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="tech") String tech
            ,@RequestParam(value="startdate") String startdate
            ,@RequestParam(value="enddate") String enddate
            ,@RequestParam(value="ifkeyin") String ifkeyin
            ,@RequestParam(value="targetmodule") String targetmodule
    ) {
        List<QtimeOverDetailVO> list = qtimeService.getQtimeOverDetail(nengflag,module,tech,startdate,enddate,ifkeyin,targetmodule);
        try(ByteArrayOutputStream ignored = new ByteArrayOutputStream()) {
            // 清空 response
            response.reset();
            response.setCharacterEncoding("UTF-8");

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("QtimeOver.xlsx", StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");


            EasyExcel.write(response.getOutputStream(), QtimeOverDetailVO.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("导出列表")
                    .doWrite(list);

        }catch (Exception e) {
            log.info("e:{}",e.toString());
        }
    }

    /**
     *查询维护原因
     * @param type
     * @return
     */
    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getQtimeOverReason")
    public ResponseResult<List<Map<String,Object>>> getQtimeOverReason(
            @RequestParam(value="type") String type
    ){
        try{
            List<Map<String,Object>> list = qtimeService.getQtimeOverReason(type);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询QtimeOverReason 失败",e);
            return  ResponseResult.fail("QtimeOverReason 失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "delete")
    @PostMapping("/deleteQtimeOverReason")
    public ResponseResult<Map<String, Object>> deleteQtimeOverReason(
            @RequestParam(value = "key") String key
            ,@RequestParam(value = "type") String type
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID

    ) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
            qtimeService.deleteQtimeOverReason(key,type);
            return ResponseResult.success("删除成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("删除失败", e);
            return ResponseResult.fail("删除失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "insert")
    @PostMapping("/insertQtimeOverReason")
    public ResponseResult<Map<String, Object>> insertQtimeOverReason(
             @RequestParam(value = "key") String key
            ,@RequestParam(value = "type") String type
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                qtimeService.insertQtimeOverReason(key,type,UPDATE_USER);
                return ResponseResult.success("插入成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("插入失败", e);
            return ResponseResult.fail("插入失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getSumReport")
    public ResponseResult<List<Map<String,Object>>> getSumReport(
             @RequestParam(value="lot_id") String lot_id
            ,@RequestParam(value="ope_no") String ope_no
    ){
        try{
            lot_id = lot_id.replace(".", "");
            List<Map<String,Object>> list = qtimeService.getSumReport(lot_id,ope_no);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询SumReport失败",e);
            return  ResponseResult.fail("SumReport失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getQtimeDetail")
    public ResponseResult<List<Map<String,Object>>> getQtimeDetail(
             @RequestParam(value="lotid") String lotid
            ,@RequestParam(value="tri_openo") String tri_openo
            ,@RequestParam(value="tar_openo") String tar_openo
            ,@RequestParam(value="qtime_grage") String qtime_grage
            ,@RequestParam(value="qtime_spec") String qtime_spec
    ){
        try{
            lotid = lotid.replace(".", "");
            List<Map<String,Object>> list = qtimeService.getQtimeDetail(lotid,tri_openo,tar_openo,qtime_grage,qtime_spec);
            list = list.stream()
                    .map(map -> {
                        Map<String, Object> newMap = new HashMap<>(map);
                        newMap.remove("DIFF");
                        newMap.remove("MEMO");
                        newMap.remove("COMM");
                        return newMap;
                    })
                    .collect(Collectors.toList());
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询QtimeDetail失败",e);
            return  ResponseResult.fail("QtimeDetail失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getQtimeDetailPivot")
    public ResponseResult<List<Map<String,Object>>> getQtimeDetailPivot(
            @RequestParam(value="lotid") String lotid
            ,@RequestParam(value="tri_openo") String tri_openo
            ,@RequestParam(value="tar_openo") String tar_openo
            ,@RequestParam(value="qtime_grage") String qtime_grage
            ,@RequestParam(value="qtime_spec") String qtime_spec
    ){
        try{
            lotid = lotid.replace(".", "");
            List<Map<String,Object>> list = qtimeService.getQtimeDetail(lotid,tri_openo,tar_openo,qtime_grage,qtime_spec);
            // 处理数据
            List<Map<String, Object>> processedData = processQTimeData(list);
            if (!processedData.isEmpty()) {
                processedData.remove(0);
            }
            return  ResponseResult.success(processedData);
        }catch (Exception e){
            log.error("查询QtimeDetail失败",e);
            return  ResponseResult.fail("QtimeDetail失败");
        }
    }

    //list相关转化操作
    public List<Map<String, Object>> processQTimeData(List<Map<String, Object>> originalList) {
        if (originalList == null || originalList.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. 字段重命名和选择需要的字段
        List<Map<String, Object>> renamedList = originalList.stream()
                .map(originalMap -> {
                    Map<String, Object> newMap = new LinkedHashMap<>();
                    newMap.put("OPER_NO", originalMap.get("OPER_NO"));
                    newMap.put("COMPLETE_TIME", originalMap.get("CLAIM_TIME"));
                    newMap.put("DIFF", originalMap.get("DIFF"));
                    newMap.put("STD_CT", originalMap.get("CT"));  // 重命名CT为STD_CT
                    newMap.put("ACT_CT", originalMap.get("CYCLE_TIME"));  // 重命名CYCLE_TIME为ACT_CT
                    newMap.put("RUN_TIME", originalMap.get("RUN_TIME"));
                    newMap.put("HOLD_TIME", originalMap.get("HOLD_TIME"));
                    newMap.put("WAIT_TIME", originalMap.get("WAIT_TIME"));
                    newMap.put("BANK_TIME", originalMap.get("BANK_TIME"));
                    newMap.put("MEMO", originalMap.get("MEMO"));
                    newMap.put("COMM", originalMap.get("COMM"));
                    return newMap;
                })
                .collect(Collectors.toList());

        // 2. 去重（基于所有字段）
        List<Map<String, Object>> distinctList = renamedList.stream()
                .distinct()
                .collect(Collectors.toList());

        // 3. 行转列 - 按OPER_NO分组
        Map<String, List<Map<String, Object>>> groupedByOperNo = distinctList.stream()
                .collect(Collectors.groupingBy(
                        map -> (String) map.get("OPER_NO"),
                        LinkedHashMap::new,  // 保持顺序
                        Collectors.toList()
                ));

        // 4. 创建结果列表（行转列后的格式）
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 添加标题行（第一列名称为OPER_NO）
        Map<String, Object> headerRow = new LinkedHashMap<>();
        headerRow.put("OPER_NO", "OPER_NO");
        for (String operNo : groupedByOperNo.keySet()) {
            headerRow.put(operNo, operNo);
        }
        resultList.add(headerRow);

        // 添加数据行（每个指标一行）
        addDataRows(resultList, groupedByOperNo, "COMPLETE_TIME");
        addDataRows(resultList, groupedByOperNo, "DIFF");
        addDataRows(resultList, groupedByOperNo, "STD_CT");
        addDataRows(resultList, groupedByOperNo, "ACT_CT");
        addDataRows(resultList, groupedByOperNo, "RUN_TIME");
        addDataRows(resultList, groupedByOperNo, "HOLD_TIME");
        addDataRows(resultList, groupedByOperNo, "WAIT_TIME");
        addDataRows(resultList, groupedByOperNo, "BANK_TIME");
        addDataRows(resultList, groupedByOperNo, "MEMO");
        addDataRows(resultList, groupedByOperNo, "COMM");

        return resultList;
    }

    private void addDataRows(List<Map<String, Object>> resultList,
                             Map<String, List<Map<String, Object>>> groupedData,
                             String fieldName) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("OPER_NO", fieldName);  // 第一列为指标名称

        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedData.entrySet()) {
            String operNo = entry.getKey();
            List<Map<String, Object>> records = entry.getValue();

            // 假设每个OPER_NO只有一条记录（如果有多条，这里取第一条）
            if (!records.isEmpty()) {
                row.put(operNo, records.get(0).get(fieldName));
            } else {
                row.put(operNo, null);
            }
        }

        resultList.add(row);
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getCommByOpe")
    public ResponseResult<List<Map<String,Object>>> getCommByOpe(
            @RequestParam(value="lotid") String lotid
            ,@RequestParam(value="tri_openo") String tri_openo
            ,@RequestParam(value="tar_openo") String tar_openo
            ,@RequestParam(value="ope_no") String ope_no
    ){
        try{
            lotid = lotid.replace(".", "");
            List<Map<String,Object>> list = qtimeService.getCommByOpe(lotid,tri_openo,tar_openo,ope_no);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询CommByOpe失败",e);
            return  ResponseResult.fail("CommByOpe失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getCommByLot")
    public ResponseResult<List<Map<String,Object>>> getCommByLot(
            @RequestParam(value="lotid") String lotid
            ,@RequestParam(value="tri_openo") String tri_openo
            ,@RequestParam(value="tar_openo") String tar_openo
    ){
        try{
            lotid = lotid.replace(".", "");
            List<Map<String,Object>> list = qtimeService.getCommByLot(lotid,tri_openo,tar_openo);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询CommByLot失败",e);
            return  ResponseResult.fail("CommByLot失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "update")
    @PostMapping("/updateQtimeOverMemo")
    public ResponseResult<Map<String, Object>> updateQtimeOverMemo(
             @RequestParam(value = "lotid") String lotid
            ,@RequestParam(value = "tri_openo") String tri_openo
            ,@RequestParam(value = "tar_openo") String tar_openo
            ,@RequestParam(value = "ope_no") String ope_no
            ,@RequestParam(value = "memo") String memo
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                lotid = lotid.replace(".", "");
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                qtimeService.updateQtimeOverMemo(lotid,tri_openo,tar_openo,ope_no,memo,UPDATE_USER);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }

    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "update")
    @PostMapping("/updateQtimeOverComm")
    public ResponseResult<Map<String, Object>> updateQtimeOverComm(
            @RequestParam(value = "lotid") String lotid
            ,@RequestParam(value = "tri_openo") String tri_openo
            ,@RequestParam(value = "tar_openo") String tar_openo
            ,@RequestParam(value = "comment") String comment
            ,@RequestParam(value = "FUNCTIONID") String FUNCTIONID) {
        try {
            if(jwtUtils.havePermission(FUNCTIONID)){
                lotid = lotid.replace(".", "");
                String UPDATE_USER=jwtUtils.getUsernameFromToken();
                qtimeService.updateQtimeOverComm(lotid,tri_openo,tar_openo,comment,UPDATE_USER);
                return ResponseResult.success("更新成功");
            }else{
                return ResponseResult.fail(ResponseCode.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            return ResponseResult.fail("更新失败");
        }
    }


    @SystemLog(function_id = "qtimeOverDetailWeb",operateType = "select")
    @GetMapping("/getQtimefLOW")
    public ResponseResult<List<Map<String,Object>>> getQtimefLOW(
             @RequestParam(value="prodspec_ID") String prodspec_ID
            ,@RequestParam(value="flow_KEY") String flow_KEY
            ,@RequestParam(value="tri_openo") String tri_openo
            ,@RequestParam(value="tar_openo") String tar_openo
            ,@RequestParam(value="qtime_grage") String qtime_grage
            ,@RequestParam(value="qtime_spec") String qtime_spec
    ){
        try{
            List<Map<String,Object>> list = qtimeService.getQtimefLOW(prodspec_ID,flow_KEY,tri_openo,tar_openo,qtime_grage,qtime_spec);
            return  ResponseResult.success(list);
        }catch (Exception e){
            log.error("查询QtimefLOW失败",e);
            return  ResponseResult.fail("QtimefLOW失败");
        }
    }





}
