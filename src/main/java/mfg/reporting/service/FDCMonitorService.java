package mfg.reporting.service;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.dto.TFClnSpecialRuleDTO;
import mfg.reporting.mapper.FDCMonitorMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@Service
@Slf4j
public class FDCMonitorService {

    @Resource
    public FDCMonitorMapper mapper;


    public List<Map<String,Object>> getTFEqpgFDCSummary(String startDate,String endDate){

        return mapper.getTFEqpgFDCSummary(startDate,endDate);

    }


    public List<Map<String,Object>> getTFEqpgRecipeFDCSummary(String eqpg,String date,String specialFlag){
        return mapper.getTFEqpgRecipeFDCSummary(eqpg,date,specialFlag);
    }


    public List<Map<String,Object>> getTFToolRecipeFDCSUmmary(String eqpg,String date,String specialFlag,String recipe){
        return mapper.getTFToolRecipeFDCSUmmary(eqpg,date,specialFlag,recipe);
    }

    public List<Map<String,Object>> getClnRecipeChartMonitor(String eqpg,String date,String toolName,String recipe,String specialFlag){
        return mapper.getClnRecipeChartMonitor(eqpg,date,toolName,recipe,specialFlag);
    }

    public List<Map<String,Object>> getTFClnSpecialFlagMaintainInfo(){
        return mapper.getTFClnSpecialFlagMaintainInfo();
    }

    public List<Map<String,Object>> getTFClnFilterDataMaintainInfo(){
        return mapper.getTFClnFilterDataMaintainInfo();
    }


    public void mergeTFClnSpecialFlagMaintainInfo(MultipartFile file, String username){

        log.info("开始上传 Excel 数据，用户：{}", username);

        try{
            List<TFClnSpecialRuleDTO> list = EasyExcel.read(file.getInputStream())
                    .head(TFClnSpecialRuleDTO.class)
                    .sheet()
                    .doReadSync();

            if (list == null || list.isEmpty()) {
                throw new IOException("上传文件为空");
            }

            log.info("读取到 {} 条数据", list.size());

            mapper.mergeTFClnSpecialFlagMaintainInfo(list,username);

        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }


    }


    public void insertTFClnFilterDataMaintainInfo(Map<String,Object> data){
        mapper.insertTFClnFilterDataMaintainInfo(data);
    }

    public void deleteTFClnSpecialFlagMaintainInfo(Map<String,Object> data){
        mapper.deleteTFClnSpecialFlagMaintainInfo(data);
    }

    public void delteTFClnFilterDataMaintainInfo(Map<String,Object> data){
        mapper.delteTFClnFilterDataMaintainInfo(data);
    }

}
