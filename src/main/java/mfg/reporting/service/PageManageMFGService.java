package mfg.reporting.service;

import mfg.reporting.mapper.PageManageMFGMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PageManageMFGService {
    @Resource
    public PageManageMFGMapper pageManageMFGMapper;

    public List<Map<String,Object>> getPageManageMFGList(String module,String parentMenuIdParam) {
        List<Map<String,Object>> list = pageManageMFGMapper.getPageManageMFGList(module,parentMenuIdParam);
        // 这里可以使用递归或其他方法将list 通过 MENU_ID 与 PARENT_MENU_ID 转为结构树转换为树形结构
        List<Map<String,Object>> returnList =  new ArrayList<>();
        //先将 parent_menu_id 为 null 的菜单添加到返回列表
        for (Map<String,Object> item : list) {
            if (item.get("PARENT_MENU_ID") == null) {
                returnList.add(item);
            }
        }
        //循环遍历返回列表，查找子菜单
        for (Map<String,Object> parentItem : returnList) {
            String parentMenuId = (String) parentItem.get("MENU_ID");
            List<Map<String,Object>> children = new ArrayList<>();
            for (Map<String,Object> item : list) {
                if (parentMenuId.equals(item.get("PARENT_MENU_ID"))) {
                    children.add(item);
                }
            }
            // 将子菜单添加到父菜单的 children 字段中
            //将children 按照 SEQ 排序
            children.sort((a, b) -> {
                BigDecimal seqA = (BigDecimal) a.get("SEQ");
                BigDecimal seqB = (BigDecimal) b.get("SEQ");
                return Integer.compare(seqA.intValue(), seqB.intValue());
            });
            parentItem.put("children", children);
        }
        return returnList;
    }

    public void updateMenuName(String menuId, String menuName) {
        // 这里可以添加更新菜单名称的逻辑
        // 例如调用 mapper 方法更新数据库中的菜单名称
        pageManageMFGMapper.updateMenuName(menuId, menuName);
    }

    public void insertPageManageLink(Map<String,String> data) {
        // 这里可以添加插入页面链接的逻辑
        // 例如调用 mapper 方法插入到数据库中
        pageManageMFGMapper.insertPageManageLink(data);
    }

    public void updatePageManageLink(Map<String,String> data) {
        // 这里可以添加更新页面链接的逻辑
        // 例如调用 mapper 方法更新数据库中的页面链接
        pageManageMFGMapper.updatePageManageLink(data);
    }

    public void deletePageManageLink(String menuId){
        pageManageMFGMapper.deletePageManageLink(menuId);
    }
}
