package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.ReportingDemandListPO;
import mfg.reporting.pojo.ReportingDemandListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gszhang
 */
@UseDataSource("pdwmfg")
public interface ReportingDemandsListMapper {
  
  
  /**
   *  新增需求
   * @param demandList 需求
   */
   void insertDemandsList(ReportingDemandListPO demandList);
  
  /**
   *  更新需求
   * @param demandList 需求list
   */
   void updateDemandsList(ReportingDemandListPO demandList);
  
  /**
   *  删除需求
   * @param demandsId 需求Id
   */
   void deleteDemandsById(@Param("demandsId") String demandsId);
  
  /**
   * 查询具体数据
   */
   List<ReportingDemandListPO> selectDemandsList(ReportingDemandListPO demandList);

   ReportingDemandListPO getDemandsInfoById(@Param("demandsId") String demandsId);

   void insertDemandsListAll(List<ReportingDemandListVO> list);

   List<ReportingDemandListVO> selectDemandsListAll();
}
