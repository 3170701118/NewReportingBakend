package mfg.reporting.controller;

import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.pojo.PullInwebPO;
import mfg.reporting.service.PullInWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pullInWeb")
public class PullInWebController {
    private static final Logger log = LoggerFactory.getLogger(PullInWebController.class);
    @Resource
    private PullInWebService service;

    @Resource
    private JwtUtils jwtUtils;

    @PostMapping("/getPullInWebData")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    @ResponseBody
    public ResponseResult<List<PullInwebPO>> getPullInWebData(
             String shiftDate
             ,String functionName
    ) {
        try {
            return ResponseResult.success("查询成功", service.getPullInWebData(shiftDate,functionName));
        } catch (Exception e) {
            log.error("获取PullInWeb数据失败", e);
            return null;
        }
    }

    @PostMapping("/UploadPullInLotExcel")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    ResponseResult<Map<String, Object>> UploadPullInLotExcel(MultipartFile file, String source, String functionId) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                service.uploadPullInWebExcel(file, jwtUtils.getUsernameFromToken(),source);
                return ResponseResult.success("上传成功");
            }
            return ResponseResult.fail("无权限，上传失败");
        } catch (Exception e) {
            return ResponseResult.fail("上传失败:"+e.getMessage());
        }
    }

    @PostMapping("/deletePullInLot")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    ResponseResult<Void> deletePullInLot(String functionId,String lotList,String functionName) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                log.info("lotList:{},username:{}",lotList,jwtUtils.getUsernameFromToken());

                service.deletePullInLot(lotList,functionName);
                return ResponseResult.success("删除成功");
            }
            return ResponseResult.fail("无权限，删除失败");
        } catch (Exception e) {
            return ResponseResult.fail("删除失败");
        }
    }

    //setTopLotInfo
    @PostMapping("/setTopLotInfo")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    ResponseResult<Void> setTopLotInfo(String functionId,String lotList ,String functionName) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                log.info("lotList:{},username:{}",lotList,jwtUtils.getUsernameFromToken());

                service.setTopLotInfo(lotList, jwtUtils.getUsernameFromToken(),functionName);
                return ResponseResult.success("设置成功");
            }
            return ResponseResult.fail("无权限，设置失败");
        } catch (Exception e) {
            return ResponseResult.fail("设置失败");
        }
    }

    @GetMapping("/getDelayLotSummary")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<List<Map<String, Object>>>getDelayLotSummary(String fab) {
        try {
            return ResponseResult.success("查询成功", service.getDelayLotSummary(fab));
        } catch (Exception e) {
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getActivetabNamesByFab")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getActivetabNamesByFab(String fab) {
        try {
                return ResponseResult.success("查询成功", service.getActivetabNamesByFab(fab));
        } catch (Exception e) {
            return ResponseResult.fail("查询失败");
        }
    }

    @GetMapping("/getAllTabNameByFab")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getAllTabNameByFab(String fab) {
        try {
                return ResponseResult.success("查询成功", service.getAllTabNameByFab(fab));
        } catch (Exception e) {
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/updateActiveTabInfo")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<Void> updateActiveTabInfo(String functionId,String tabName,String fab,String active,String rank,String label) {
        try {
            if (jwtUtils.havePermission(functionId)) {
                log.info("tabName:{},fab:{},active:{},rank:{},label:{},username:{}",tabName,fab,active,rank,label,jwtUtils.getUsernameFromToken());

                service.updateActiveTabInfo(tabName, fab, active, rank, label, jwtUtils.getUsernameFromToken());

                return ResponseResult.success("更新成功");
            }
            return ResponseResult.fail("无权限，更新失败");
        }catch (Exception e){
            return ResponseResult.fail("更新失败");
        }
    }

    @GetMapping("/getDelayLotDetails")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getDelayLotDetails(String fab ,String date,String module,String functionName) {
        try {
            return ResponseResult.success("查询成功", service.getDelayLotDetails(fab,date,module,functionName));
        } catch (Exception e) {
            return ResponseResult.fail("查询失败");
        }
    }

    @PostMapping("/updateDelayLotComment")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<Void> updateDelayLotComment(String functionId,@RequestBody Map<String,Object> data) {
        try {
            if (jwtUtils.havePermission(functionId)) {

                String UPDATE_USER=jwtUtils.getUsernameFromToken();

                data.put("UPDATE_USER",UPDATE_USER);

                log.info("data:{},username:{}",data,UPDATE_USER);

                service.updateDelayLotComment(data);

                return ResponseResult.success("更新成功");
            }
            return ResponseResult.fail("无权限，更新失败");
        }catch (Exception e){
            return ResponseResult.fail("更新失败");
        }
    }

    @GetMapping("/getPullinWebMonitorByEQPG")
    @SystemLog(function_id = "pullinWebTitle",operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getPullinWebMonitorByEQPG(String functionName) {
        try {
            return ResponseResult.success("查询成功", service.getPullinWebMonitorByEQPG(functionName));
        } catch (Exception e) {
            return ResponseResult.fail("查询失败");
        }
    }


}
