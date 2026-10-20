package mfg.reporting.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import mfg.reporting.Configuration.HttpUtil;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.mapper.CVDCleanMonitorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CVDCleanMonitorService {

    @Resource
    private  CVDCleanMonitorMapper mapper;


    @Resource
    private HttpUtil httpUtil;


    @Transactional(rollbackFor = Exception.class)
    public void getFDCMoveHistory(String fab){

        List<String> list = new ArrayList<>();
        if("N1A1".equals(fab)){
            list = mapper.getN1A1ToolIdList();
        }else if("N1A3".equals(fab)){
            list = mapper.getN1A3ToolIdList();
        }else{
            log.error("fab字段不可识别，直接返回不可执行谢列排程");
        }

        //mapper.deleteFDCMoveInfo(fab);

        // 调用接口， 每500个 调用一次
        int batchSize = 500;
        int insertBatchSize = 2000;
        for (int i = 0; i < list.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, list.size());
            List<String> batchList = list.subList(i, endIndex);
            // 调用接口
            log.info("调用接口，处理批次：" + (i / batchSize + 1) + "，数据量：" + batchList.size());

            Map<String,Object> body = new HashMap<>();
            body.put("fabId",fab);
            body.put("systemName","A1MFG");
            // toolList 需要转换 [{"ToolChamber":list[0]},{"ToolChamber":list[1]}]...
            body.put("dataList",batchList.stream()
                    .map(item -> Map.of("ToolChamber", item))
                    .collect(Collectors.toList()));

            String Params = JSON.toJSONString(body);

            Map<String,Object> resultData = httpUtil.sendPost("http://10.53.194.131:80/TEST/FDCValuePlatfromService/api/context/FDC_ContextData_OneHour",Params);

            if("0".equals(resultData.get("stateCode"))){
                // 将data 转化成List<Map<String,Object>> 分批插入数据库
                List<Map<String,Object>> data = (List<Map<String,Object>>)resultData.get("data");

                // 如果 data.size>0 , 则需要分批插入数据
                if(data.size()>0){
                    // 分批插入数据库
                    for(int j = 0;  j < data.size(); j += insertBatchSize){

                        int insertEndIndex = Math.min(j + insertBatchSize, data.size());
                        List<Map<String,Object>> insertData = data.subList(j, insertEndIndex);
                        mapper.insertFDCMoveInfo(insertData,fab);
                    }

                    // 删除冗余数据；

                    mapper.deleteFDCMoveInfo(fab);
                }

            }else{
                throw new ServiceException("查询 FDC Run 货历史记录失败，请重新查询");
            }
        }
    }

}
