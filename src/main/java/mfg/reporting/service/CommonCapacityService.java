package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CapacityCommentDTO;
import mfg.reporting.dto.CapacityEqpgInfoDTO;
import mfg.reporting.dto.CapacityProdMixDTO;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.mapper.CommonCapacityMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CommonCapacityService {



    @Resource
    private CommonCapacityMapper mapper;


    public List<CommonSelectDTO> getModeTypeList(){
        return mapper.getModeTypeList();
    }

    public List<Map<String,Object>> getProductMixInfo(String mode){
        return mapper.getProductMixInfo(mode);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProductMix(MultipartFile file , String username ,String mode) throws IOException {

        List<CapacityProdMixDTO> list = EasyExcel.read(file.getInputStream())
                .head(CapacityProdMixDTO.class)
                .sheet()
                .doReadSync();


        // trim PRODUCT
        for(CapacityProdMixDTO dto : list){
            dto.setPRODUCT(dto.getPRODUCT().trim().toUpperCase());
            // 需要 符合 规则 只能由 8码且必须为大写字母 &数字组成
            if(!dto.getPRODUCT().matches("[0-9A-Z]{8}")){
                throw new ServiceException("PRODUCT格式错误");
            }
        }


        // 校验 PRODUCT 数据不能存在重复值 ,即 list.product 去重值与 list.size一致
        if(list.size() != list.stream().map(CapacityProdMixDTO::getPRODUCT).distinct().count()){
            throw new ServiceException("PRODUCT 不能重复");
        }

        mapper.deleteProductMix(mode);
        mapper.insertProductMixList(list,username ,mode);

        // 异步触发同步机台配置；

        asynceqpgSettingAfterUploadProductMix(mode,username);

    }


    // 异步触发同步机台配置
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void asynceqpgSettingAfterUploadProductMix(String mode,String username){

        // By Module 不需要 trigger
        if(mode.isEmpty()){
            return ;
        }

        if(mode.contains("TEMP")){
            return;
        }
        mapper.asynceqpgSettingAfterUploadProductMix(mode,username);
    }





    public List<Map<String,Object>> getEqpgInfo(String mode){
        return mapper.geteqpgInfo(mode);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEqpgInfo(MultipartFile file,String mode , String username) throws IOException {
        List<CapacityEqpgInfoDTO> list = EasyExcel.read(file.getInputStream())
                .head(CapacityEqpgInfoDTO.class)
                .sheet()
                .doReadSync();

        // 验证 STD_EQP_ID 是否存在 ，检查EQP_G 是否存在
        for(CapacityEqpgInfoDTO dto : list){
            if(dto.getSTD_EQP_ID() == null || ("").equals(dto.getSTD_EQP_ID())){
                throw new ServiceException("STD_EQP_ID 不能为空");
            }
            if(dto.getEQP_G() == null || ("").equals(dto.getEQP_G())){
                throw new ServiceException("EQPG 不能为空");
            }
            // trim eqpg
            dto.setEQP_G(dto.getEQP_G().trim());
        }

        mapper.mergeEqpgInfo(list,mode,username);

    }


    public void updateEQPGComment(MultipartFile file,String source,String userName) throws IOException {
        List<CapacityCommentDTO> list = EasyExcel.read(file.getInputStream())
                .head(CapacityCommentDTO.class)
                .sheet()
                .doReadSync();

        for(CapacityCommentDTO dto : list){
            if(dto.getEqpg() == null || dto.getEqpg().isEmpty()){
                throw new ServiceException("EQPG 不能为空");
            }

            if(dto.getComment() == null || dto.getComment().isEmpty()){
                throw new ServiceException("Comment 不能为空");
            }
            // trim eqpg
            dto.setEqpg(dto.getEqpg().trim());

            List<String> lines = Arrays.asList(dto.getComment().split("\\R"));

            String result = String.join("</font></p><p><font face=\"思源黑体\" size=\"4\">",lines);

            String commentUpload = "<p style=\"margin: 0px; flex: 1 1 0%;\"><p><font face=\"思源黑体\" size=\"4\">".concat(result).concat("</font></p></p>");

            String comment1 = "";
            String comment2 = "";

            if(commentUpload.length()<3000){
                comment1 = commentUpload;
            }else{
                comment1 = commentUpload.substring(0,3000);
                comment2 = commentUpload.substring(3000);
            }
            dto.setUploadComment1(comment1);
            dto.setUploadComment2(comment2);
        }

        mapper.updateCapacityComment(list,source,userName);


    }



    public List<Map<String,Object>> getEqpgCapacitySummary(String mode){
        return mapper.geteqpgCapacitySummary(mode);
    }

    public List<Map<String,Object>> geteqpGCapacitySummaryByFabSelf(String mode,String fab){
        return mapper.geteqpGCapacitySummaryByFabSelf(mode,fab);
    }


    public void updateEqpgFComment(Map<String,Object> data){
        mapper.updateEqpgFComment(data);
    }

    public void updateEqpgSummaryComment(String eqpg , String eqpgComment , String eqpgComment2,String mode , String username){
        mapper.updateEqpgSummaryComment(eqpg,eqpgComment,eqpgComment2,mode,username);
    }


    public List<Map<String,Object>> getRCConstraints(){
        return mapper.getRCConstraints();
    }

    public void insertCapacityRCConstraints(Map<String,Object> data){
        mapper.insertCapacityRCConstraints(data);
    }

    public void insertCapacityRCConstraintsBatch(List<Map<String,Object>> list){
        mapper.insertCapacityRCConstraintsBatch(list);
    }

    public void deleteCapacityRCConstraints(int CONSTRAINTS_ID){
        mapper.deleteCapacityRCConstraints(CONSTRAINTS_ID);
    }

    public void updateCapacityRCConstraints(Map<String,Object> data){
        mapper.updateCapacityRCConstraints(data);
    }



    public List<Map<String,Object>> getEqpgCommonCapacityDetails(String mode,String eqpg){
        return mapper.getEqpgCommonCapacityDetails(mode,eqpg);
    }

    public List<CommonSelectDTO> geteqpgCapacityUploadList(String mode){
        return mapper.geteqpgCapacityUploadList(mode);
    }

}
