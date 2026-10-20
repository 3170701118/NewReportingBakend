package mfg.reporting.service;


import com.alibaba.excel.EasyExcel;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.CommonSelectDTO;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.mapper.DownSamplingMapper;
import mfg.reporting.pojo.DownSamplingPEJurgePO;
import mfg.reporting.pojo.DownSamplingSRTargetPO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DownSamplingService {
    @Resource
    public DownSamplingMapper mapper;

    @Resource
    public JwtUtils jwtUtils;

    private static final String permissionId = "DownSamplingTitle";


    public List<CommonSelectDTO> getMainTainEqpGroup(){
        List<Map<String,String>> list = mapper.getMainTainEqpGroup();
        List<CommonSelectDTO> result =  new ArrayList<>();

        if(list.size()>0){
            for(Map<String,String> item : list){
                CommonSelectDTO dto = new CommonSelectDTO();
                dto.setValue(item.get("EQP_G"));
                dto.setLabel(item.get("EQP_G"));
                result.add(dto);
            }
        }else{
            throw new ServiceException("未查询到数据");
        }

        return result;
    }

    public List<DownSamplingPEJurgePO> getPEJurgeList(String eqpG){
        List<DownSamplingPEJurgePO> list = mapper.getPEJurgeList(eqpG);
        return list;
    }

    public void insertPEJurgeRecord(DownSamplingPEJurgePO po){
        try{
            if(!jwtUtils.havePermission(permissionId)){
                throw new ServiceException(ResponseCode.UNAUTHORIZED);
            }
            //验证判断的是否有权限 操作
            String CurModule = getCurModule();
            if(!po.getModule().equals(CurModule)){
                throw new ServiceException(ResponseCode.UNAUTHORIZED.getCode(),"无权限操作"+po.getModule()+"模块数据");
            }

            String userName = jwtUtils.getUsernameFromToken();
            po.setUpdateUser(userName);
            mapper.insertPEJurgeRecord(po);

        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<DownSamplingPEJurgePO> getPEJurgeRecordHs(String eqpG){
        List<DownSamplingPEJurgePO> list = mapper.getPEJurgeRecordHs(eqpG);
        return list;
    }

    public List<Map<String,Object>> getDownSamplingTargetSrList(String tech,String opeNo){
        List<Map<String,Object>> list = mapper.getDownSamplingTargetSrList(tech,opeNo);
        return list;
    }

    /**
     * @TODO 前端单独传EQPG
     * @param file
     */
    public void uploadRevenueTemplate(MultipartFile file){
        try {

            if(!jwtUtils.havePermission(permissionId)){
                throw new ServiceException(ResponseCode.UNAUTHORIZED);
            }

            List<DownSamplingPEJurgePO> list = EasyExcel.read(file.getInputStream())
                    .head(DownSamplingPEJurgePO.class)
                    .sheet()
                    .doReadSync();
            String updateUser = jwtUtils.getUsernameFromToken();

            String curModule = getCurModule();
            List<DownSamplingPEJurgePO> insertList = new ArrayList<>();
            //验证上传的 jurgeResult 是否为N ,Y；
            for (DownSamplingPEJurgePO po : list) {
                if (po.getJurgeResult().isBlank()|| (!po.getJurgeResult().equals("N") && !po.getJurgeResult().equals("Y"))) {
                    throw new ServiceException("上传失败,请检查判定结果是否为N或Y");
                }

                if (po.getModule().equals(curModule)) {
                    po.setUpdateUser(updateUser);
                    insertList.add(po);
                }

                }
            if(insertList.size()>0) {
                mapper.insertPEJurgeRecordList(insertList);
            }
        }catch (Exception e){
            throw new ServiceException("上传失败"+e.getMessage());
        }
    }

    private String getCurModule() {
        String deptId = jwtUtils.getUserInfoFromToken().getDeptId();
        String curModule = "";
        if(deptId.equals("PB21") || deptId.equals("PH21")){
            curModule = "PHOTO";
        }else if(deptId.equals("PB31") || deptId.equals("PH31")){
            curModule = "ETCH";
        }else if(deptId.equals("PB41") || deptId.equals("PH41")){
            curModule = "TF";
        }else if(deptId.equals("PB51") || deptId.equals("PH51")){
            curModule = "DIFF";
        }else if(deptId.equals("PF21") || deptId.equals("PH71")){
            curModule = "WET";
        }else if(deptId.equals("PF41") || deptId.equals("PH81")){
            curModule = "CMP";
        }
        return curModule;
    }

    /**
     * @TODO insert 逻辑
     * @param file
     */
    public void uploadStdSRTarget(MultipartFile file){
        try {

            if(!jwtUtils.havePermission(permissionId)){
                throw new ServiceException(ResponseCode.UNAUTHORIZED);
            }

            List<DownSamplingSRTargetPO> list = EasyExcel.read(file.getInputStream())
                    .head(DownSamplingSRTargetPO.class)
                    .sheet()
                    .doReadSync();
            String updateUser = jwtUtils.getUsernameFromToken();
            List<DownSamplingSRTargetPO> insertList = new ArrayList<>();
            //验证上传的 STD_SR 是否为 0 1 2 4 5 8 之间的数据
            for (DownSamplingSRTargetPO po : list) {
                if (po.getStdSR() == null || (!po.getStdSR().equals(0) && !po.getStdSR().equals(1) && !po.getStdSR().equals(2)
                        && !po.getStdSR().equals(4) && !po.getStdSR().equals(5) && !po.getStdSR().equals(8))) {
                    throw new ServiceException("请检查STD_SR是否为 0 1 2 4 5 8 之间的数据");
                }
                po.setUpdateUser(updateUser);
                insertList.add(po);
            }

            if(insertList.size()>0) {
                mapper.insertStdSRTargetList(insertList);
            }

        }catch (Exception e){
            throw new ServiceException("上传失败"+e.getMessage());
        }
    }


    public List<Map<String,Object>> getDownSamplingMoveDetails(DownSamplingPEJurgePO po){
        try {
            List<Map<String,Object>> list = mapper.getDownSamplingMoveDetails(po);
            return list;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
