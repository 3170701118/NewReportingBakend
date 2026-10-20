package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.pojo.DownSamplingPEJurgePO;
import mfg.reporting.service.DownSamplingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@RestController
@RequestMapping("/downSampling")
@Slf4j
public class DownSamplingController {
    @Resource
    public DownSamplingService service;

    @GetMapping("/getMainTainEqpGroup")
    public ResponseResult<List<CommonSelectDTO>> getMainTainEqpGroup(){
        try{
            List<CommonSelectDTO> list = service.getMainTainEqpGroup();
            return ResponseResult.success("查询成功",list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }

    @GetMapping("/getPEJurgeList")
    public ResponseResult<List<DownSamplingPEJurgePO>> getPEJurgeList(@RequestParam(value="eqpG") String eqpG){
        try{
            List<DownSamplingPEJurgePO> list = service.getPEJurgeList(eqpG);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }

    @PostMapping("/insertPEJurgeRecord")
    public ResponseResult<Void> insertPEJurgeRecord(@RequestBody DownSamplingPEJurgePO srJurgePO){
        try{
             service.insertPEJurgeRecord(srJurgePO);
            return ResponseResult.success("插入成功");
        }catch (Exception e){
            return  ResponseResult.fail("插入失败"+e.getMessage());
        }
    }

    @GetMapping("/getPEJurgeRecordHs")
    public ResponseResult<List<DownSamplingPEJurgePO>> getPEJurgeRecordHs(@RequestParam(value="eqpG") String eqpG){
        try{
            List<DownSamplingPEJurgePO> list = service.getPEJurgeRecordHs(eqpG);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }

    @GetMapping("/getDownSamplingTargetSrList")
    public ResponseResult<List<Map<String,Object>>> getDownSamplingTargetSrList(@RequestParam(value="tech") String tech,
                                                                                @RequestParam(value="opeNo") String opeNo){
        try{
            List<Map<String,Object>> list = service.getDownSamplingTargetSrList(tech,opeNo);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }

    /**
     * 批量 Jurge Result File
     * @param file
     * @param functionId
     * @return
     */
    @PostMapping("/uploadJurgeResultFile")
    @ResponseBody
    public ResponseResult<Void> uploadRevenueTemplate(MultipartFile file, String functionId) {

        try{
            service.uploadRevenueTemplate(file);
            return ResponseResult.success("上传成功");
        }catch (Exception e){
            return  ResponseResult.fail("上传失败"+e.getMessage());
        }
    }

    @PostMapping("/uploadStdSRTarget")
    @ResponseBody
    public ResponseResult<Void> uploadStdSRTarget(MultipartFile file, String functionId) {

        try{
            service.uploadStdSRTarget(file);
            return ResponseResult.success("上传成功");
        }catch (Exception e){
            return  ResponseResult.fail("上传失败"+e.getMessage());
        }
    }

    @PostMapping("/getDownSamplingMoveDetails")
    public ResponseResult<List<Map<String,Object>>> getDownSamplingMoveDetails(@RequestBody DownSamplingPEJurgePO po){
        try{
            List<Map<String,Object>> list = service.getDownSamplingMoveDetails(po);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }


}
