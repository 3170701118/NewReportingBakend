package mfg.reporting.service;

import mfg.reporting.Exception.ServiceException;
import mfg.reporting.mapper.MoveTargetMapper;
import mfg.reporting.pojo.MoveTargetPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MoveTargetService  {

    @Resource
    public MoveTargetMapper mapper;

    public List<MoveTargetPO> getMoveTargetList(String fab) {

        if(!fab.equals("N1A1") && !fab.equals("N1A3")){
            throw new ServiceException("FAB 只能是 N1A1 或 N1A3");
        }
        if("N1A1".equals(fab)) {
            return mapper.getN1A1MoveTargetList();
        }
        else {
            return mapper.getN1A3MoveTargetList();
        }
    }
}
