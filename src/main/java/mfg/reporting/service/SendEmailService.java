package mfg.reporting.service;

import mfg.reporting.mapper.SendEmailMapper;
import mfg.reporting.pojo.CommonSendEmail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */

@Service
public class SendEmailService {

    @Resource
    public SendEmailMapper sendEmailMapper;
    /**
     * 发送mail
     * @param  data 数据
     */
    public void callSendEmail(CommonSendEmail data){
        sendEmailMapper.callSendEmail(data);
    }

    /**
     * 发送mail 加抄送人
     */
    public void callSendEmailAddCC(CommonSendEmail data){
        sendEmailMapper.callSendEmailAddCC(data);
    }

    /**
     * 获取 ReworkLotAlarm List
     * @return list
     */
    public List<Map<String,Object>> getReworkLotList(){
        return sendEmailMapper.getReworkLotList();
    }

    /**
     * 获取PA20收件人
     * @return
     */
    public List<Map<String,Object>> getPA20Recivers(String deptid){
        return sendEmailMapper.getPA20Recivers(deptid);
    }

    /**
     * 获取MFG收件人
     * @return
     */
    public List<Map<String,Object>> getMfgRecivers(String deptid){
        return sendEmailMapper.getMfgRecivers(deptid);
    }

    /**
     * 插入mail log
     * @param type
     * @param prodg
     * @param deptid
     */
    public void insertNtoMailLog(String type,String prodg,String deptid){
        sendEmailMapper.insertNtoMailLog(type,prodg,deptid);
    }

    /**
     * 获取需要发送module的部门
     * @return
     */
    public List<Map<String,Object>> getMfgToModule(){
        return sendEmailMapper.getMfgToModule();
    }

    /**
     * Over200获取需要发送module的部门
     * @return
     */
    public List<Map<String,Object>> getOver200ToModule(){
        return sendEmailMapper.getOver200ToModule();
    }

    /**
     * Over200获取需要发送module的部门细项
     * @param prodg
     * @param deptid
     * @return
     */
    public List<Map<String,Object>> getOver200ToModuleDetail(String prodg,String deptid){
        return sendEmailMapper.getOver200ToModuleDetail(prodg,deptid);
    }

    /**
     * Maintain EQPG获取需要发送module的部门
     * @return
     */
    public List<Map<String,Object>> getMaintainToModule(){
        return sendEmailMapper.getMaintainToModule();
    }

    /**
     * Maintain EQPG获取需要发送module的部门细项
     * @param prodg
     * @param deptid
     * @return
     */
    public List<Map<String,Object>> getMaintainToModuleDetail(String prodg,String deptid){
        return sendEmailMapper.getMaintainToModuleDetail(prodg,deptid);
    }

    /**
     * 发送Maintain EQPG TO OPD
     * @return
     */
    public List<Map<String,Object>> getMaintainEQPGToOPD(){
        return sendEmailMapper.getMaintainEQPGToOPD();
    }

    /**
     * 发送未维护产品给产品Owner
     * @return
     */
    public List<Map<String,Object>> getNotMaintainToOwner(){
        return sendEmailMapper.getNotMaintainToOwner();
    }


    /**
     * 获取系统自动导入产品
     * @return
     */
    public List<Map<String,Object>> getAutoInsertProdgList(){
        return sendEmailMapper.getAutoInsertProdgList();
    }


}
