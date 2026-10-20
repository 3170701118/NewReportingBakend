package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.HoldLotToSubDeptPO;
import mfg.reporting.pojo.HoldLotToSubDeptRulePO;
import mfg.reporting.pojo.HoldLotToSubDeptSpecialRulePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HoldLotToSubDeptMapper {

    @UseDataSource("pdwmfg")
    HoldLotToSubDeptPO getLotInfoByLotId(@Param("lotId")String lotId, @Param("opeNo")String opeNo);

    @UseDataSource("pdw")
    List<HoldLotToSubDeptPO> getDYBHoldLotSDeptInfo(HoldLotToSubDeptPO data);

    @UseDataSource("pdwmfg")
    List<HoldLotToSubDeptPO> getPhotoReworkHoldLotToSubDeptList(HoldLotToSubDeptPO data);


    @UseDataSource("pdwmfg")
    List<HoldLotToSubDeptPO> getMainHoldLotToSubDeptList(HoldLotToSubDeptPO data);

    @UseDataSource("pdwmfg")
    List<HoldLotToSubDeptPO> getLastOpeLotInfoByLotId(HoldLotToSubDeptPO data);

    @UseDataSource("pdwmfg")
    List<HoldLotToSubDeptRulePO> getHoldLotToSubDeptRuleInfo(@Param("moduleOwner")String moduleOwner, @Param("eqpG")String eqpG ,@Param("module")String module, @Param("updateFlag")String updateFlag);

    @UseDataSource("pdwmfg")
    void updateHoldLotToSubDeptRuleInfo(HoldLotToSubDeptRulePO holdLotToSubDeptRulePO);

    @UseDataSource("pdwmfg")
    void insertHoldLotToSubDeptRuleInfo (HoldLotToSubDeptRulePO holdLotToSubDeptRulePO);

    @UseDataSource("pdwmfg")
    HoldLotToSubDeptRulePO selectOneHoldLotToSubDeptRule(HoldLotToSubDeptRulePO holdLotToSubDeptRulePO);

    @UseDataSource("pdwmfg")
    HoldLotToSubDeptRulePO testInsertHoldLotRule(HoldLotToSubDeptRulePO holdLotToSubDeptRulePO);

    @UseDataSource("pdwmfg")
    List<HoldLotToSubDeptSpecialRulePO> getSpecialRuleList();

    @UseDataSource("pdwmfg")
    void insertSpecialRule(HoldLotToSubDeptSpecialRulePO holdLotToSubDeptSpecialRulePO);

    @UseDataSource("pdwmfg")
    void insertHoldLotToSubDeptRuleList(List<HoldLotToSubDeptRulePO> list);
}

