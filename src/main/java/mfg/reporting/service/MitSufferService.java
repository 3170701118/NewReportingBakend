package mfg.reporting.service;

import mfg.reporting.mapper.MitSufferMapper;
import mfg.reporting.mapper.NtoPathMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MitSufferService {
    @Resource
    public MitSufferMapper mitSufferMapper;

    public List<Map<String,Object>> getMitSummary(String fab)
    {
        List<Map<String,Object>> list =  mitSufferMapper.getMitSummary(fab);
        return list;
    }

    public List<Map<String,Object>> getMitSummaryNon(String fab)
    {
        List<Map<String,Object>> list =  mitSufferMapper.getMitSummaryNon(fab);
        return list;
    }

    public List<Map<String,Object>> getShiftDate()
    {
        List<Map<String,Object>> list =  mitSufferMapper.getShiftDate();
        return list;
    }

    public List<Map<String,Object>> getMitAllSummary(String fab)
    {
        List<Map<String,Object>> list =  mitSufferMapper.getMitAllSummary(fab);
        return list;
    }
}
