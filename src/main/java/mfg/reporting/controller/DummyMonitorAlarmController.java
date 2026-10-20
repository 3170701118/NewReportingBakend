package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.service.DummyMonitorAlarmService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DummyMonitorAlarm")
@Slf4j
public class DummyMonitorAlarmController {

    @Resource
    public DummyMonitorAlarmService service;

    @Resource
    public JwtUtils jwtUtils;

    @RequestMapping("/insertDummyMonitorAlarm")
    @SystemLog(function_id = "dummyMonitorTitle", operateType = "insert")
    public ResponseResult<Void> insertDummyMonitorAlarm(@RequestBody Map<String,Object> data){
        try{
            if (jwtUtils.havePermission("dummyMonitorTitle")) {
            String updateUser = jwtUtils.getUsernameFromToken();
            data.put("updateUser", updateUser);
/*            String fab = data.get("fab").toString();
            String purpose = data.get("purpose").toString();
            String activeFlag = data.get("activeFlag").toString();
            float stdf = Float.parseFloat(data.get("stdf").toString());
            int specialStock = Integer.parseInt(data.get("specialStock").toString());

            String keyComment = data.get("keyComment").toString();*/
            service.insertDummyMonitorAlarm(data);
            return ResponseResult.success("插入成功");
            }
            return ResponseResult.fail("无权限，上传失败");
        }
        catch (ServiceException e){
            return  ResponseResult.fail(e.getMessage());
        }
        catch (Exception e){
            log.error("插入失败:{}",e.getMessage());
            return  ResponseResult.fail("插入失败");
        }
    }


    @RequestMapping("/UploadExcel")
    ResponseResult<Map<String, Object>> UploadExcel(MultipartFile file, String source, String functionId) {
        try {
            if (jwtUtils.havePermission("dummyMonitorTitle")) {
                service.UploadExcel(file, jwtUtils.getUsernameFromToken());
                return ResponseResult.success("上传成功");
            }
            return ResponseResult.fail("无权限，上传失败");
        } catch (Exception e) {
            return ResponseResult.fail("上传失败:" + e.getMessage());
        }
    }

    @RequestMapping("/updateDummyMonitorAlarmActiveFlag")
    @SystemLog(function_id = "dummyMonitorTitle", operateType = "update")
    public ResponseResult<Void> updateDummyMonitorAlarmActiveFlag(@RequestBody Map<String,Object> data){
        try{
            if(jwtUtils.havePermission("dummyMonitorTitle")) {
            String updateUser = jwtUtils.getUsernameFromToken();
            String fab = data.get("fab").toString();
            String purpose = data.get("purpose").toString();
            String activeFlag = data.get("activeFlag").toString();

            String keyComment = data.get("keyComment").toString();
            float stdf = Float.parseFloat(data.get("stdf").toString());
            service.updateDummyMonitorAlarmActiveFlag(fab,purpose,stdf,activeFlag,keyComment,updateUser);
            return ResponseResult.success("更新成功");
            }
            return ResponseResult.fail("无权限，删除失败");
        }catch (Exception e){
            log.error("更新失败:{}",e.getMessage());
            return  ResponseResult.fail("更新失败");
        }
    }

    @RequestMapping("/deleteDummyMonitorAlarm")
    @SystemLog(function_id = "dummyMonitorTitle", operateType = "delete")
    public ResponseResult<Void> deleteDummyMonitorAlarm(String fab, String purpose){
        try{
            if(jwtUtils.havePermission("dummyMonitorTitle")) {
                String updateUser = jwtUtils.getUsernameFromToken();
                service.deleteDummyMonitorAlarm(fab, purpose);
                log.info("操作人：{} , 删除成功:{}", updateUser, fab + '-' + purpose);
                return ResponseResult.success("删除成功");
            }
            return ResponseResult.fail("无权限，删除失败");
        }catch (Exception e){
            log.error("删除失败:{}",e.getMessage());
            return  ResponseResult.fail("删除失败");
        }
    }

    @RequestMapping("/getDummyMonitorAlarm")
    @SystemLog(function_id = "dummyMonitorTitle", operateType = "select")
    public ResponseResult<List<Map<String,Object>>> getDummyMonitorAlarm(String fab, String module, String purpose){
        try{
            List<Map<String,Object>> list = service.getDummyMonitorAlarm(fab,module,purpose);
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            log.error("查询失败:{}",e.getMessage());
            return  ResponseResult.fail("查询失败");
        }
    }

    @RequestMapping("/sendMail")
    public ResponseResult<Void> sendMail(){
        try{
            service.sendMail();
            return ResponseResult.success("发送成功");
        }catch (Exception e){
            log.error("发送失败:{}",e.getMessage());
            return  ResponseResult.fail("发送失败");
        }
    }
}
