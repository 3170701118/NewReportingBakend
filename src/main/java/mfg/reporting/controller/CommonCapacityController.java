package mfg.reporting.controller;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.CommonUtil.SystemLog;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.service.CommonCapacityService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CommonCapacity")
@Slf4j
public class CommonCapacityController {

    @Resource
    private CommonCapacityService service;

    @Resource
    private JwtUtils jwtUtils;


    @RequestMapping("/getModeTypeList")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "select")
    public ResponseResult<List<CommonSelectDTO>> getModeTypeList() {
        try {
            return ResponseResult.success("查询成功", service.getModeTypeList());
        } catch (Exception e) {
            log.error("Error occurred while getting mode type list: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @RequestMapping("/getProductMixInfo")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getProductMixInfo(
            @RequestParam(value = "mode") String mode
    ) {
        try {
            return ResponseResult.success("查询成功", service.getProductMixInfo(mode));
        } catch (Exception e) {
            log.error("Error occurred while getting product mix info: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/updateProductMix")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "update")
    public ResponseResult<Void> updateProductMix(MultipartFile file, String source, String functionId) {
        try {
            String username = jwtUtils.getUsernameFromToken(); // Fixed method call to get username from token
            log.info("Username: {},functionId:{}", username, functionId);
            service.updateProductMix(file, username, source);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("Error occurred while updating product mix: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/getEqpgInfo")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getEqpgInfo(
            @RequestParam(value = "mode") String mode
    ) {
        try {
            return ResponseResult.success("查询成功", service.getEqpgInfo(mode));
        } catch (Exception e) {
            log.error("Error occurred while getting eqpg info: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/updateEqpgInfo")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "UPLOAD")
    public ResponseResult<Void> updateEqpgInfo(
            MultipartFile file,
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "functionId", required = false) String functionId
    ) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            log.info("Username: {}", username);
            service.updateEqpgInfo(file, source, username);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("Error occurred while updating eqpg info: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/updateEQPGComment")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "UPLOAD")
    public ResponseResult<Void> updateEQPGComment(
            MultipartFile file,
            @RequestParam(value = "source", required = false) String  source
    ) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            log.info("Username: {}", username);
            service.updateEQPGComment(file,source, username);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("Error occurred while updating eqpg info: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }





    @RequestMapping("/getEqpgCapacitySummary")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getEqpgCapacitySummary(
            @RequestParam(value = "mode") String mode
    ) {
        try {
            return ResponseResult.success("查询成功", service.getEqpgCapacitySummary(mode));
        } catch (Exception e) {
            log.error("Error occurred while getting eqpg capacity summary: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @RequestMapping("/geteqpGCapacitySummaryByFabSelf")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> geteqpGCapacitySummaryByFabSelf(
            @RequestParam(value = "mode") String mode,
            @RequestParam(value = "fab") String fab
    ) {
        try {
            return ResponseResult.success("查询成功", service.geteqpGCapacitySummaryByFabSelf(mode,fab));
        } catch (Exception e) {
            log.error("Error occurred while getting eqpg capacity summary: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }




    /**
     * 更新EQPG F_COMMENT
     */
    @RequestMapping("/updateEqpgFComment")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "update")
    public ResponseResult<Void> updateEqpgFComment(
            @RequestBody Map<String, Object> data
    ) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            data.put("username", username);
            service.updateEqpgFComment(data);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("Error occurred while updating eqpg F_COMMENT: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    /**
     * 更新 eqpg F_COMMENT
     */
    @RequestMapping("/updateEqpgSummaryComment")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "update")
    public ResponseResult<Void> updateEqpgSummaryComment(
            @RequestParam(value = "eqpg") String eqpg,
            @RequestParam(value = "eqpgComment") String eqpgComment,
            @RequestParam(value = "eqpgComment2") String eqpgComment2,
            @RequestParam(value = "mode", required = false) String mode
    ) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            log.info("Username: {}, eqpg: {}, eqpgComment: {},eqpgComment2:{},mode:{}", username, eqpg, eqpgComment, eqpgComment2, mode);
            service.updateEqpgSummaryComment(eqpg, eqpgComment, eqpgComment2, mode, username);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("Error occurred while updating eqpg F_COMMENT: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @RequestMapping("/getRCConstraints")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "select")
    public ResponseResult<List<Map<String, Object>>> getRCConstraints() {
        try {
            return ResponseResult.success("查询成功", service.getRCConstraints());
        } catch (Exception e) {
            log.error("Error occurred while getting eqpg capacity getRCConstraints: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @RequestMapping("/insertCapacityRCConstraints")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "INSERT")
    public ResponseResult<Void> insertCapacityRCConstraints(@RequestBody Map<String, Object> data) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            log.info("Username: {}", username);
            data.put("UPDATE_USER", username);
            service.insertCapacityRCConstraints(data);
            return ResponseResult.success("插入成功");
        } catch (Exception e) {
            log.error("Error occurred while inserting capacity RCConstraints: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @RequestMapping("/deleteCapacityRCConstraints")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "DELETE")
    public ResponseResult<Void> deleteCapacityRCConstraints(
            @RequestParam(value = "CONSTRAINTS_ID") int CONSTRAINTS_ID
    ) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            log.info("Username: {}", username);
            service.deleteCapacityRCConstraints(CONSTRAINTS_ID);
            return ResponseResult.success("删除成功");
        } catch (Exception e) {
            log.error("Error occurred while deleting capacity RCConstraints: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }

    }

    @RequestMapping("/updateCapacityRCConstraints")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "UPDATE")
    public ResponseResult<Void> updateCapacityRCConstraints(@RequestBody Map<String, Object> data) {
        try {
            String username = jwtUtils.getUsernameFromToken();
            log.info("Username: {}", username);
            data.put("UPDATE_USER", username);
            service.updateCapacityRCConstraints(data);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            log.error("Error occurred while updating capacity RCConstraints: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }

    @RequestMapping("/getEqpgCommonCapacityDetails")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "SELECT")
    public ResponseResult<List<Map<String,Object>>> getEqpgCommonCapacityDetails(
            @RequestParam(value = "mode")String mode,
            @RequestParam(value = "eqpg")String eqpg
    ) {
        try {
            return ResponseResult.success("更新成功",service.getEqpgCommonCapacityDetails(mode,eqpg));
        } catch (Exception e) {
            log.error("Error occurred while updating capacity RCConstraints: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }


    @RequestMapping("/geteqpgCapacityUploadList")
    @SystemLog(function_id = "CommonCapacityTitle", operateType = "SELECT")
    public ResponseResult<List<CommonSelectDTO>> geteqpgCapacityUploadList(
            @RequestParam(value = "mode")String mode
    ) {
        try {
            return ResponseResult.success("更新成功",service.geteqpgCapacityUploadList(mode));
        } catch (Exception e) {
            log.error("Error occurred while updating capacity RCConstraints: {}", e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
    }



}
