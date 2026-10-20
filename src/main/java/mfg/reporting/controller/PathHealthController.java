package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;

import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.service.PathHealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/PathHealth")
@Slf4j
public class PathHealthController {
    @Resource
    public PathHealthService pathHealthService;


    /**
     * 查询群组下机台
     * @param fab
     * @param module
     * @param eqpg
     * @return
     */
    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getEqp")
    public ResponseResult<List<Map<String,Object>>> getEqp(
             @RequestParam(value="fab") String fab
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="eqpg") String eqpg
    ){
        try{
            List<Map<String,Object>> list = pathHealthService.getEqp(fab,module,eqpg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getEqp失败",e);
            return  ResponseResult.fail("getEqp失败");
        }
    }

    /**
     * 查询module下群组
     * @param fab
     * @param module
     * @return
     */
    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getEqpg")
    public ResponseResult<List<Map<String,Object>>> getEqpg(
            @RequestParam(value="fab") String fab
            ,@RequestParam(value="module") String module
    ){
        try{
            List<Map<String,Object>> list = pathHealthService.getEqpg(fab,module);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getEqpg失败",e);
            return  ResponseResult.fail("getEqpg失败");
        }
    }

    /**
     * 查询群组下产品
     * @param module
     * @param eqpg
     * @return
     */
    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getEqpProdg")
    public ResponseResult<List<Map<String,Object>>> getEqpProdg(
             @RequestParam(value="fab") String fab
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="eqpg") String eqpg
    ){
        try{
            List<Map<String,Object>> list = pathHealthService.getEqpProdg(fab,module,eqpg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getEqpProdg失败",e);
            return  ResponseResult.fail("getEqpProdg失败");
        }
    }

    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getTech")
    public ResponseResult<List<Map<String,Object>>> getTech(){
        try{
            List<Map<String,Object>> list = pathHealthService.getTech();
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getTech失败",e);
            return  ResponseResult.fail("getTech失败");
        }
    }

    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getSuffix")
    public ResponseResult<List<Map<String,Object>>> getSuffix(
            @RequestParam(value="fab") String fab
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="eqpg") String eqpg
            ,@RequestParam(value="prodg") String prodg
    ){
        try{
            List<Map<String,Object>> list = pathHealthService.getSuffix(fab,module,eqpg,prodg);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getSuffix失败",e);
            return  ResponseResult.fail("getSuffix失败");
        }
    }

    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getOpeno")
    public ResponseResult<List<Map<String,Object>>> getOpeno(
            @RequestParam(value="fab") String fab
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="eqpg") String eqpg
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="prodgspecid") String prodgspecid
    ){
        try{
            List<Map<String,Object>> list = pathHealthService.getOpeno(fab,module,eqpg,prodg,prodgspecid);
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getOpeno失败",e);
            return  ResponseResult.fail("getOpeno失败");
        }
    }


    @SystemLog(function_id = "pathHealthWeb",operateType = "select")
    @GetMapping("/getPathDetail")
    public ResponseResult<List<Map<String,Object>>> getPathDetail(
             @RequestParam(value="fab") String fab
            ,@RequestParam(value="module") String module
            ,@RequestParam(value="eqpg") String eqpg
            ,@RequestParam(value="prodg") String prodg
            ,@RequestParam(value="prodgspecid") String prodgspecid
            ,@RequestParam(value="openo") String openo
            ,@RequestParam(value="recipe") String recipe
            ,@RequestParam(value="tech") String tech
    ){
        try{
            List<Map<String,Object>> listeqp = pathHealthService.getEqp(fab,module,eqpg);
            String[] array = listeqp.stream()
                    .map(map -> {
                        Object val = map.get("COMMON_ID");
                        return val == null ? "" : val.toString();
                    })
                    .toArray(String[]::new);
            List<Map<String,Object>> list=pathHealthService.getPathDetail(fab,module,eqpg,prodg,prodgspecid,openo,recipe,array,tech);
            if(list.size()<=1){
                list.clear();
            }
            if(list.size()>1){
                List<Map<String, Object>> targetList = list.stream()
                        .map(this::flatDotMap)
                        .collect(Collectors.toList());
                targetList=calcCntField(targetList);
                return  ResponseResult.success("查询成功",targetList);

            }
            return  ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询getPathDetail失败",e);
            return  ResponseResult.fail("getPathDetail失败");
        }
    }

    public List<Map<String, Object>> calcCntField(List<Map<String, Object>> targetList) {
        for (Map<String, Object> row : targetList) {
            int cnt = 0;
            // 遍历该行所有字段
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Object valObj = entry.getValue();
                if (!(valObj instanceof String)) {
                    continue;
                }
                String val = ((String) valObj).trim();
                if (val.isEmpty()) {
                    continue;
                }

                boolean isOk = "OK".equals(val);
                boolean isMfgRule = val.contains("MFG")
                        && !val.contains("RDS")
                        && !val.contains("RMS")
                        && !val.contains("APC");

                if (isOk || isMfgRule) {
                    cnt++;
                }
            }
            // 设置CNT
            row.put("CNT", cnt);
        }
        return targetList;
    }

    private Map<String, Object> flatDotMap(Map<String, Object> source) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String parentKey = entry.getKey();
            Object value = entry.getValue();

            // 如果值是内层Map，拼接 key = 父.子
            if (value instanceof Map) {
                Map<String, Object> childMap = (Map<String, Object>) value;
                for (Map.Entry<String, Object> child : childMap.entrySet()) {
                    String newKey = parentKey + "." + child.getKey();
                    result.put(newKey, child.getValue());
                }
            } else {
                // 普通字段直接保留
                result.put(parentKey, value);
            }
        }
        return result;
    }



}
