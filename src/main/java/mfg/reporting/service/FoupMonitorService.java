package mfg.reporting.service;

import mfg.reporting.mapper.FoupMonitorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FoupMonitorService {

    @Resource
    public FoupMonitorMapper foupMonitorMapper;

    public List<Map<String,Object>> getEmptyFoupDailyData(String fab) {
        return foupMonitorMapper.getEmptyFoupDailyData(fab);
    }

}
