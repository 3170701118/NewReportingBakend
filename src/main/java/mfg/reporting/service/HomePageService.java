package mfg.reporting.service;

import mfg.reporting.mapper.CommonMapper;
import mfg.reporting.mapper.HomePageMapper;
import mfg.reporting.pojo.FileManage;
import mfg.reporting.pojo.MenuInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HomePageService {

    @Resource
    public HomePageMapper homePageMapper;

    @Resource
    public CommonMapper commonMapper;

    /**
     * 获取到 ClickRate Summary的数据
     */
    public List<Map<String,Object>> getClickRateSummary(String weekType, String rankType){
        List<Map<String,Object>> list = homePageMapper.getClickRateSummary(weekType,rankType);
        return list;
    }

    public List<FileManage> getFileTableData(String functionId){
        List<FileManage> list = commonMapper.getFileListTableByFunctionId(functionId);
        return list;
    }

    /**
     * 查询 首页共用web / 测试链接的页面数据
     * @param menuType
     * @return
     */
    public List<MenuInfo> getMenuInfolist(String menuType){
        return homePageMapper.getMenuInfolist(menuType);
    }
}
