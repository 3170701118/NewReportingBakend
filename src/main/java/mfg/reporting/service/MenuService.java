package mfg.reporting.service;


import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ServiceException;
import mfg.reporting.enums.ResponseCode;
import mfg.reporting.mapper.MenuMapper;
import mfg.reporting.pojo.MenuInfo;
import mfg.reporting.dto.MenuInfoDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Resource
    public MenuMapper menuMapper;

    @Resource
    public JwtUtils jwtUtils;

    public List<MenuInfoDTO> getMenuListInfo(){
        List<MenuInfoDTO> returnList = new ArrayList<>();
        List<MenuInfo> menuList = menuMapper.getMenuInfoList();
        returnList = list2Tree(menuList);
         return returnList;
     };

    public List<MenuInfoDTO> list2Tree(List<MenuInfo> menuInfoList){
        List<MenuInfoDTO> resultlist = new ArrayList<>();

        for (MenuInfo list : menuInfoList) {
            //根节点、顶级节点，直接放入最终返回结果的Lists
            if(list.getParentMenuId().equals("-1")) {
                MenuInfoDTO menuInfoDTO = setMenuInfoDTOFromMenuInfo(list,"/");
                if(menuInfoDTO.getMenuType().equals("menu")){
                    menuInfoDTO.setLeaf(false);
                    menuInfoDTO.setChildren(getMenuDTOChildren(menuInfoDTO,menuInfoList));
                }

                if(menuInfoDTO.getMenuType().equals("page")){
                    menuInfoDTO.setLeaf(true);
                }
                resultlist.add(menuInfoDTO);
            }
        }
        return resultlist;
    }

    private MenuInfoDTO setMenuInfoDTOFromMenuInfo(MenuInfo list,String menuPath){
        MenuInfoDTO menuInfoDTO = new MenuInfoDTO();
        menuInfoDTO.setMenuId(list.getMenuId());
        menuInfoDTO.setParentMenuId(list.getParentMenuId());
        menuInfoDTO.setParentMenuName(list.getParentMenuName());
        menuInfoDTO.setMenuName(list.getMenuName());
        menuInfoDTO.setMenuPath(menuPath);
        menuInfoDTO.setMenuType(list.getMenuType());
        menuInfoDTO.setPath(list.getPath());
        menuInfoDTO.setRank(list.getRank());
        menuInfoDTO.setRemark(list.getRemark());
        menuInfoDTO.setHide(list.getHide());
        menuInfoDTO.setStatus(list.getStatus());
        return menuInfoDTO;
    }

    private List<MenuInfoDTO> getMenuDTOChildren(MenuInfoDTO menuInfoDTO,List<MenuInfo> list){
        List<MenuInfoDTO> menuInfoDTOList = new ArrayList<>();
        for(MenuInfo item: list){
            if(item.getParentMenuId().equals(menuInfoDTO.getMenuId())){
                String menuPath = menuInfoDTO.getMenuPath().concat(menuInfoDTO.getMenuName()).concat("/");
                MenuInfoDTO menuInfoDTO1 = setMenuInfoDTOFromMenuInfo(item,menuPath);
                if("menu".equals(menuInfoDTO.getMenuType())){
                    menuInfoDTO1.setChildren(getMenuDTOChildren(menuInfoDTO1,list));
                }
                menuInfoDTOList.add(menuInfoDTO1);
            }
        }
        return menuInfoDTOList;
    }

    public MenuInfo getMenuInfoDetails(String menuId){
        MenuInfo info =  menuMapper.getMenuInfoDetails(menuId.trim());
        return info;
    }

    public void insertMenuInfo(MenuInfo menuInfo){
        //先校验有无重复数据
        try{
            MenuInfo menuInfo1 = new MenuInfo();
            menuInfo1.setMenuId(menuInfo.getMenuId());
            menuInfo1.setPath(menuInfo.getPath());
            menuInfo1.setRouteName(menuInfo.getRouteName());
            menuInfo1.setParentMenuId(menuInfo.getParentMenuId());
            List<MenuInfo> list = menuMapper.getMenuInfoListByParam(menuInfo1);
            if(list.size()>0){
                throw new ServiceException(ResponseCode.WARN.getCode(),"存在相同的menuId，或者path");
            }
            //验证参数通过
            //开始验证权限
            if(!jwtUtils.havePermission("RtSys_Reporting_MenuList")){
                throw new ServiceException(ResponseCode.UNAUTHORIZED.getCode(),"无权限修改");
            }
            //全部验证通过 插入数据;
            menuInfo.setCreateUser(jwtUtils.getUsernameFromToken());
            menuMapper.insertMenuInfo(menuInfo);
            }catch (ServiceException e) {
                throw new ServiceException(e.getCode(),e.getMessage());
            }
            catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
    }

    public void updateMenuInfo(MenuInfo menuInfo){
        try{
            MenuInfo menuInfo1 = new MenuInfo();
            menuInfo1.setPath(menuInfo.getPath());
            menuInfo1.setParentMenuId(menuInfo.getParentMenuId());
            List<MenuInfo> list = menuMapper.getMenuInfoListByParam(menuInfo1);
            if(list.size()>0 && !list.get(0).getMenuId().equals(menuInfo.getMenuId())){
                throw new ServiceException(ResponseCode.WARN.getCode(),"存在相同的path，该path的名称为 "+list.get(0).getMenuName());
            }
            if(!jwtUtils.havePermission("RtSys_Reporting_MenuList")){
                throw new ServiceException(ResponseCode.UNAUTHORIZED.getCode(),"无权限修改");
            }
            //全部验证通过 插入数据;
            menuInfo.setUpdateUser(jwtUtils.getUsernameFromToken());
            menuMapper.updateMenuInfo(menuInfo);

        }catch (ServiceException e) {
            throw new ServiceException(e.getCode(),e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteMenuInfo(String menuId ){
        try{
            if(!jwtUtils.havePermission("RtSys_Reporting_MenuList")){
                throw new ServiceException(ResponseCode.UNAUTHORIZED.getCode(),"无权限删除");
            }
            menuMapper.deleteMenuInfo(menuId);
        }catch (ServiceException e){
            throw new ServiceException(e.getCode(),e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
