package mfg.reporting.service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.pojo.ReportingDemandListVO;
import org.springframework.stereotype.Service;
import mfg.reporting.pojo.ReportingDemandListPO;
import mfg.reporting.mapper.ReportingDemandsListMapper;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gszhang
 */
@Service
@Slf4j
public class ReportingDemandsListService {
  
  @Resource
  public ReportingDemandsListMapper rdl;

  @Resource
  public JwtUtils jwtUtils;
/**
 * 插入或更新需求信息
 * @param demand 需求list
 */
  public void insertDemand(ReportingDemandListPO demand){
    // hutool-all 判断如果demandsId存在，则更新，不存在则新增
    demand.setUpdateUser(jwtUtils.getUsernameFromToken());
    if(!StrUtil.isEmptyIfStr(demand.getDemandsId())){
      //更新 completionRate
      rdl.updateDemandsList(demand);
    }else{
      //新增 UUID 生成一个 string DEMAINDS_ID
      String demandsId = UUID.randomUUID().toString();
      demand.setDemandsId(demandsId);
      rdl.insertDemandsList(demand);
    }
  }

  /**
   * 删除需求信息
   * @param id
   */
  public void deleteDemandsById(String id){
    //删除
    rdl.deleteDemandsById(id);
  }

  /**
   *  根据id获取需求信息
   * @param demand
   * @return
   */
  public List<ReportingDemandListPO> selectDemandsList(ReportingDemandListPO demand){
    //查询
    return rdl.selectDemandsList(demand);
  }

  public ReportingDemandListPO getDemandsInfoById(String demandsId){
    return rdl.getDemandsInfoById(demandsId);
  }

  /**
   * 批量插入excel
   * @param file
   * @throws IOException
   */
  public void insertDemandListExcel(MultipartFile file) throws IOException {
    //使用easyexcel 上传
    try {
      List<ReportingDemandListVO> list = EasyExcel.read(file.getInputStream())
              .head(ReportingDemandListVO.class)
              .sheet()
              .doReadSync();

      //循环 list 赋予 demandId, updateUser
      for(ReportingDemandListVO rdlp :list){
        String demandsId = UUID.randomUUID().toString();
        rdlp.setDemandsId(demandsId);
        rdlp.setUpdateUser(jwtUtils.getUsernameFromToken());
      }
      rdl.insertDemandsListAll(list);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 批量导出excel
   */

  public List<ReportingDemandListVO> selectDemandsListAll(){
    return rdl.selectDemandsListAll();
  }
}
