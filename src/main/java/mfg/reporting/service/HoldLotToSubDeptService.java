package mfg.reporting.service;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.mapper.HoldLotToSubDeptMapper;
import mfg.reporting.pojo.HoldLotToSubDeptPO;
import mfg.reporting.pojo.HoldLotToSubDeptRulePO;
import mfg.reporting.pojo.HoldLotToSubDeptSpecialRulePO;
import mfg.reporting.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
public class HoldLotToSubDeptService {

    @Resource
    public HoldLotToSubDeptMapper holdLotToSubDeptMapper;
    @Resource
    public JwtUtils jwtUtils;

    /**
     * @TODO 页面呈现 / maintain方式 / 其他生产课内置数据；
     * @param lotId
     * @param opeNo
     * @return
     */
    public List<HoldLotToSubDeptPO> searchHoldLotToSubDeptInfo(
            String lotId,
            String opeNo
    ){
        List<HoldLotToSubDeptPO> resultList = new ArrayList<>();
        //根据DTH 的站点 归属；
            HoldLotToSubDeptPO lotTemp = new HoldLotToSubDeptPO();
            lotTemp = holdLotToSubDeptMapper.getLotInfoByLotId(lotId, opeNo);
            //验证LOT 是否存在
            if(lotTemp == null || ("0").equals(lotTemp.getLotIdReal())){
                //  lotId不存在
                throw new ServiceException("LotId不存在,请重新输入");
            }

            //查未来站点，查询的未来站点不在Flow 中
            if(!"".equals(opeNo) && "".equals(lotTemp.getOpeNo())){
                // 传入的opeNo与lotId对应的opeNo不一致
                String message = "Flow 中无此站点,请确认输入的OpeNo是否正确";
                log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: {}", message);
                throw new ServiceException(message);
            }

            //for 吴星 Comment , XX.SXN/XX.SXW 无需Hold,不在该网页处理范围
            if(lotTemp.getOpeNo().contains("XX.SXN") || lotTemp.getOpeNo().contains("XX.SXW")){
                // 站点为XX.SXN__
                String message = "LOT当前处于".concat(lotTemp.getOpeNo()).concat("站点,无需Hold处理");
                throw new ServiceException(message);
            }

            //验证是否存在平台数据，如果无平台数据，则直接返回提示信息；
                if(lotTemp.getProdG1() == null || ("").equals(lotTemp.getProdG1())){
                    //  平台无数据，只能使用All 平台数据处理
                    String message = "无产品数据,使用 All产品 通用Rule 数据处理";
                    log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: {}", message);
                    lotTemp.setProdG1("All");
                }

        if(lotTemp.getChipBody() == null || ("").equals(lotTemp.getChipBody())){
            //  平台无数据，只能使用All 平台数据处理
            String message = "无chip数据,请确认是否download".concat(lotTemp.getLotId()).concat("chip Suffix数据");
            log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: {}", message);
            throw new ServiceException(message);
        }

                if(lotTemp.getProdG1().toUpperCase().contains("TEST")){
                    //  ProdG1 包含 TEST
                    String message = "ProdG1 包含 TEST,使用 All 平台 通用Rule 数据处理";
                    log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: {}", message);
                    lotTemp.setTech("All");
                }

        if(lotTemp.getOpeNo().startsWith("XX.") && lotTemp.getRouteId().startsWith("DYB")){
            // 站点为XX.且工艺路线为DYB, for 提单人员课别；
            // 抓取不到提单人员的话，直接返回 提示消息；
            //resultList = holdLotToSubDeptMapper.getDYBHoldLotSDeptInfo(lotTemp);
            log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 站点为XX.且工艺路线为DYB, for 提单人员课别");
            resultList = holdLotToSubDeptMapper.getDYBHoldLotSDeptInfo(lotTemp);
            if( resultList.size()==0 || ("").equals(resultList.get(0).getSdeptId())){
                // 提单人员课别不存在，个别ENG Lot 超过 6 Month 没有提单人员课别；
                log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 未查询到提单人员,{}",resultList.get(0));
                String message = "未查询到提单人员，请在MM查询最新提单人员课别";
                throw new ServiceException(message);
            }
                return resultList;
            };

        //对黄光Rework 需要进行特殊处理， 查询最后RUN的主站点；匹配数据

        if(lotTemp.getLotMfgModule().equals("PHOTO") &&(lotTemp.getMainPdId().startsWith("YY") || lotTemp.getMainPdId().startsWith("YX"))){
            resultList = holdLotToSubDeptMapper.getPhotoReworkHoldLotToSubDeptList(lotTemp);
            //对结果进行验证
            if (resultList.size()==0){
                // 存在问题，不可能为空；
                log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 系统查询存在问题，日志记录查询数据:{}", lotTemp);
                String message = "系统查询结果异常,请联系开发人员";
                throw new ServiceException(message);
            }
            //可能还原不到主站点
            if(resultList.get(0).getOpeNo() == null || ("").equals(resultList.get(0).getOpeNo())){
                log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 黄光Rework 未还原到主站点,{}", lotTemp);
                String message = "lot在该站点可能停留时间过长，或直接拆批进入rework,未还原到主站点";
                throw new ServiceException(message);
            }

            if (("未匹配Rule").equals(resultList.get(0).getSdeptId())) {
                // Rule不完善
                log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 黄光Rework 未匹配Rule,{}", lotTemp);
                String message = lotTemp.getLocation().concat(lotTemp.getLotMfgModule()).concat(" Rule不完整，请联系课内系统负责人&开发人员 完善Rule");
                throw new ServiceException(message);
            }


            return resultList;
        }

            if(!lotTemp.getOpeNo().startsWith("XX.") && !lotTemp.getRouteId().startsWith("DYB")) {
                // 非 DYB情况：1) 有maintain Flow的主站点；未maintain的主站点；Rework/Branch的情况；
                // 1.如果是主站点则一定会带出 EQP_G 字段
                // 2.不是主站点/未Maintain 则带不出来，需要重新根据PATH查询EQPG
                // 3.如果是Rework/Branch的情况，则需要根据lotId重新查询EQPG

                //整合算法: 如果非主站点/未Maintain 则带不出来/Rework/Branch的情况,补充根据PATH计算EQP_G；
                resultList = holdLotToSubDeptMapper.getMainHoldLotToSubDeptList(lotTemp);
                if (resultList.size() == 0) {
                    // 存在问题，不可能为空；
                    log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 系统查询存在问题，日志记录查询数据:{}", lotTemp);
                    String message = "系统查询结果异常,请联系开发人员";
                    throw new ServiceException(message);
                }

                if (("未匹配Rule").equals(resultList.get(0).getSdeptId())) {
                    // Rule不完善
                    log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 未匹配Rule,{}", lotTemp);
                    String message = lotTemp.getLocation().concat(lotTemp.getLotMfgModule()).concat(" Rule不完整，请联系课内系统负责人&开发人员 完善Rule");
                    throw new ServiceException(message);
                }

                boolean flag = ("FOLLOW_LAST_OPENO").equals(resultList.get(0).getSdeptId());
                if (flag) {
                    // 查出的 Rule 为Follow 上一个OpeNo的情况;
                    log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: Follow 上一非量测站点匹配,{}", lotTemp);
                    // 如果是Follow 上一非量测站点匹配,则需要根据lotId重新查询EQPG
                    List<HoldLotToSubDeptPO> dataList = holdLotToSubDeptMapper.getLastOpeLotInfoByLotId(lotTemp);

                    if (resultList.size() == 0) {
                        // 存在问题，不可能为空；
                        log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: 系统查询存在问题，Follow上一主站点 日志记录查询数据:{}", lotTemp);
                        String message = "系统查询结果异常,请联系开发人员";
                        throw new ServiceException(message);
                    }

                    if (("未匹配Rule").equals(resultList.get(0).getSdeptId())) {
                        // Rule不完善
                        log.info("HoldLotToSubDeptService.searchHoldLotToSubDeptInfo: Follow上一主站点 未匹配Rule,{}", lotTemp);
                        return resultList;
                    }

                    //不可能再会Follow上一主站点；

                    return dataList;
                }
                //如果不是 Follow 上一非量测站点匹配,则直接返回结果；

                return resultList;
            }

            if(resultList.size()==0){
                log.info("系统未完善此情况,请联系开发人,记录数据-{}",lotTemp);
                throw new RuntimeException("系统未完善此情况,请联系开发人");
            }

        return resultList;
    }



    public List<HoldLotToSubDeptRulePO> searchHoldLotToSubDeptRuleInfo(String moduleOwner, String eqpG,String module,String updateFlag) {
        return holdLotToSubDeptMapper.getHoldLotToSubDeptRuleInfo(moduleOwner, eqpG,module,updateFlag);
    }

    public void updateHoldLotToSubDeptRuleInfo(HoldLotToSubDeptRulePO holdLotToSubDeptRulePO) {

        //验证Rule信息是否完整
        if(holdLotToSubDeptRulePO.getRule() == null || ("").equals(holdLotToSubDeptRulePO.getRule())){
            throw new ServiceException("Rule不能为空");
        }

        if(!("SDEPT").equals(holdLotToSubDeptRulePO.getRule()) ){
            // 不是指定课别的情况，清空课别信息；
            holdLotToSubDeptRulePO.setSdeptId("");
        }


        HoldLotToSubDeptRulePO lotTemp = new HoldLotToSubDeptRulePO();
        lotTemp = holdLotToSubDeptMapper.selectOneHoldLotToSubDeptRule(holdLotToSubDeptRulePO);
        //日志记录 原Rule
        log.info("HoldLotToSubDeptService.updateHoldLotToSubDeptRuleInfo: 原规则信息,{}", lotTemp);


        String UserName = jwtUtils.getUsernameFromToken();
        //日志留存 更新数据内容
        log.info("HoldLotToSubDeptService.updateHoldLotToSubDeptRuleInfo: 更新规则信息,{}，更新人：{}", holdLotToSubDeptRulePO,UserName);

        holdLotToSubDeptRulePO.setUpdateUser(UserName);
        holdLotToSubDeptMapper.updateHoldLotToSubDeptRuleInfo(holdLotToSubDeptRulePO);
    }

    public void insertHoldLotToSubDeptRuleInfo(HoldLotToSubDeptRulePO holdLotToSubDeptRulePO) {
        //只能新增静态 Rule && TECH 与OPE_NO 只能为 *；Layer Sponser 特殊处理；

        //验证Rule信息是否完整

        if(holdLotToSubDeptRulePO.getEqpG() == null || ("").equals(holdLotToSubDeptRulePO.getEqpG())){
            throw new ServiceException("EQP_G不能为空");
        }
        if(holdLotToSubDeptRulePO.getModuleOwner() == null || ("").equals(holdLotToSubDeptRulePO.getModuleOwner())){
            throw new ServiceException("Module_Owner不能为空");
        }
        if(holdLotToSubDeptRulePO.getRule() == null || ("").equals(holdLotToSubDeptRulePO.getRule())){
            throw new ServiceException("Rule不能为空");
        }
        if(holdLotToSubDeptRulePO.getLocation() == null || ("").equals(holdLotToSubDeptRulePO.getLocation())){
            throw new ServiceException("location不能为空");
        }

        if( (holdLotToSubDeptRulePO.getSdeptId() == null || ("").equals(holdLotToSubDeptRulePO.getSdeptId())) && ("SDEPT").equals(holdLotToSubDeptRulePO.getRule()) ){
            throw new ServiceException("部门不能为空");
        }

        //验证是否存在相同的 EQP_G,Module_Owner,Rule,SDEPT_ID 的记录；

        //首先判断该 User 能不能新增该群组；

        HoldLotToSubDeptRulePO lotTemp = new HoldLotToSubDeptRulePO();
        String UserName = jwtUtils.getUsernameFromToken();
        holdLotToSubDeptRulePO.setUpdateUser(UserName);
        lotTemp = holdLotToSubDeptMapper.testInsertHoldLotRule(holdLotToSubDeptRulePO);

        if(lotTemp == null){
            // 如果存在相同的 EQP_G,Module_Owner 的记录；
            log.info("HoldLotToSubDeptService.insertHoldLotToSubDeptRuleInfo: 已存在相同的规则信息,{}", lotTemp);
            throw new ServiceException("已存在相同的规则信息，请修改后再试");
        }
        //日志记录 新增数据内容
        log.info("HoldLotToSubDeptService.insertHoldLotToSubDeptRuleInfo: 新增规则信息,{}，更新人：{}", holdLotToSubDeptRulePO,UserName);

        //判断有无权限；
        User user = jwtUtils.getUserInfoFromToken();
        boolean permission = false;
        //如果 lotTemp的Module 为Measurement ，更新人的 DeptId PT11 subdept_id 04
        if("Measurement".equals(lotTemp.getModule())) {
            //如果是量测站点，则只能是PT11的间接人员；
            if ("PT11".equals(user.getDeptId()) && "量测生产课".equals(user.getSdeptCname())) {
                permission = true;
            }
        }
        //Module 为 PHOTO;ETCH;DIFF;TF; (CMP;WET)  Location 需要一致 ，SDEPT_ID 03,04,05,06,08,08;
        if(("PHOTO".equals(lotTemp.getModule()) && ("03").equals(user.getSubDeptId()))
                || ("ETCH".equals(lotTemp.getModule()) && ("04").equals(user.getSubDeptId()))
                || ("DIFF".equals(lotTemp.getModule()) && ("05").equals(user.getSubDeptId()))
                || ("TF".equals(lotTemp.getModule()) && ("06").equals(user.getSubDeptId()))
                || ("CMP".equals(lotTemp.getModule()) && ("08").equals(user.getSubDeptId()))
                || ("WET".equals(lotTemp.getModule()) && ("08").equals(user.getSubDeptId()))){
            //如果是PHOTO;ETCH;DIFF;TF;CMP;WET 站点，则只能是PT11的间接人员；
            if("间".equals(user.getDirect())){
                permission = true;
            }
        }

        if(user.getRole().equals("admin")){
            //管理员可以新增
            permission = true;
        }

        if(!permission){
            throw new ServiceException("无权限新增该规则,只有生产课间接人员可以新增归属本课别的机群");
        }

        holdLotToSubDeptRulePO.setUpdateUser(UserName);
        holdLotToSubDeptMapper.insertHoldLotToSubDeptRuleInfo(holdLotToSubDeptRulePO);

        //@TODO 批量上传 ； 自动更新Rule ; Layer Sponser 特殊处理；
    }

    public List<HoldLotToSubDeptSpecialRulePO> getSpecialRuleList() {
       return  holdLotToSubDeptMapper.getSpecialRuleList();
    }

    public void insertSpecialRule(HoldLotToSubDeptSpecialRulePO pojo) {
          holdLotToSubDeptMapper.insertSpecialRule(pojo);
    }

    public void insertHoldLotToSubDeptRuleList(List<HoldLotToSubDeptRulePO> list) {

        try {
            String updateUser = jwtUtils.getUsernameFromToken();
            list.forEach(rule -> {
                rule.setUpdateUser(updateUser);
            });

            log.info("导入的数据,{}", list);
            holdLotToSubDeptMapper.insertHoldLotToSubDeptRuleList(list);
        }catch (Exception e){
            log.error("批量导入数据失败",e);
            throw new ServiceException("批量导入数据失败,请检查数据格式是否正确");
        }
    }
}
