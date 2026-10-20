package mfg.reporting.service;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.mapper.HomePageMapper;
import mfg.reporting.mapper.WorkSysMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WorkSysService {

    @Resource
    public WorkSysMapper workSysMapper;

    @Resource
    public JwtUtils jwtUtils;

    /**
     * 查询各课出缺勤summary状况
     */
    public List<Map<String,Object>> getWorkModuleSummary(String ym,String deptid, String sdeptname,String aclass){
        List<Map<String,Object>> list = workSysMapper.getWorkModuleSummary(ym,deptid,sdeptname,aclass);
        return list;
    }

    public List<Map<String,Object>> getOpFabSum(String ym,String deptid, String sdeptname){
        List<Map<String,Object>> list = workSysMapper.getOpFabSum(ym,deptid,sdeptname);
        return list;
    }

    public List<Map<String,Object>> getOpSummary(String ym){
        List<Map<String,Object>> list = workSysMapper.getOpSummary(ym);
        return list;
    }

    public List<Map<String,Object>> getAbnormalDetail(String ym,String deptid, String sdeptname,String aclass){
        List<Map<String,Object>> list = workSysMapper.getAbnormalDetail(ym,deptid,sdeptname,aclass);
        return list;
    }

    public List<Map<String,Object>> getLeaveInfo(String deptid,String sdeptname){
        List<Map<String,Object>> list = workSysMapper.getLeaveInfo(deptid,sdeptname);
        return list;
    }

    public List<Map<String,Object>> getWorkModule(String ym, String sdeptname,String aclass){
        List<Map<String,Object>> list = workSysMapper.getWorkModule(ym,sdeptname,aclass);
        return list;
    }

    public List<Map<String,Object>> getWorkModuleOver(String ym, String sdeptname,String aclass){
        List<Map<String,Object>> list = workSysMapper.getWorkModuleOver(ym,sdeptname,aclass);
        return list;
    }

    public void updateOpStatus(String YM,String ACLASS,String EMPID,String UPDATE_STATUS,String FAB_TYPE,String UPDATE_USER)
    {
        workSysMapper.updateOpStatus(YM,ACLASS,EMPID,UPDATE_STATUS,FAB_TYPE,UPDATE_USER);
    }

    public void updateOpDefault(String DEPT_ID,String SDEPT_CNAME,String EMP_ID,String CNAME,String FAB_TYPE,String UPDATE_USER)
    {
        workSysMapper.updateOpDefault(DEPT_ID,SDEPT_CNAME,EMP_ID,CNAME,FAB_TYPE,UPDATE_USER);
    }

    public List<Map<String,Object>> getYmAclass(){
        List<Map<String,Object>> list = workSysMapper.getYmAclass();
        return list;
    }

    public List<Map<String,Object>> getClassByShiftDate(String shift_date){
        List<Map<String,Object>> list = workSysMapper.getClassByShiftDate(shift_date);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("ACLASS_VALUE"));
            map.put("label",item.get("ACLASS_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }


    public List<Map<String,Object>> getModuleList(String deptid){
        List<Map<String,Object>> list = workSysMapper.getModuleList(deptid);
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("MODULE_VALUE"));
            map.put("label",item.get("MODULE_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getYMList(){
        List<Map<String,Object>> list = workSysMapper.getYMList();
        List<Map<String,Object>> returnList = new ArrayList<>();
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("YM_VALUE"));
            map.put("label",item.get("YM_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }


    public List<Map<String,Object>> getOpDefaultFab(String deptid,String sdeptname,String aclass){
        aclass=aclass.replace("班","");
        List<Map<String,Object>> list = workSysMapper.getOpDefaultFab(deptid,sdeptname,aclass);
        return list;
    }


    public List<Map<String,Object>> getModuleByPermission(String deptid)
    {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> returnList = new ArrayList<>();
        if(jwtUtils.havePermission("RtOPControl2") || jwtUtils.havePermission("RtOPControl3"))
        {
            list = workSysMapper.getModuleLevel(deptid);
            //log.info("姓名："+jwtUtils.getUserInfoFromToken().getCname()+" 课别："+jwtUtils.getUserInfoFromToken().getSdeptCname());
        }else if(jwtUtils.havePermission("RtOPControl4") || jwtUtils.havePermission("RtOPControl5"))
        {
            list = workSysMapper.getModuleProduce(deptid,jwtUtils.getUserInfoFromToken().getSdeptCname());
            //log.info("姓名："+jwtUtils.getUserInfoFromToken().getCname()+" 课别："+jwtUtils.getUserInfoFromToken().getSdeptCname());
        }else{
            list = workSysMapper.getModuleOther();
            //log.info("姓名："+jwtUtils.getUserInfoFromToken().getCname()+" 课别："+jwtUtils.getUserInfoFromToken().getSdeptCname());
        }
        for (Map<String,Object> item :list){
            Map<String,Object> map = new HashMap<>();
            map.put("value",item.get("MODULE_VALUE"));
            map.put("label",item.get("MODULE_LABEL"));
            returnList.add(map);
        }
        return returnList;
    }

    public List<Map<String,Object>> getLeaveDetail(String shift_date,String deptid,String sdept_cname){
        List<Map<String,Object>> list = workSysMapper.getLeaveDetail(shift_date,deptid,sdept_cname);
        return list;
    }

    public List<Map<String,Object>> getDoorDataByEmpid(String empid,String ym,int shift){
        List<Map<String,Object>> list = workSysMapper.getDoorDataByEmpid(empid,ym,shift);
        return list;
    }


}
