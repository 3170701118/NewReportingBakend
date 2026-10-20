package mfg.reporting.mapper;


import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.pojo.MenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@UseDataSource("pdwmfg")
public interface MenuMapper {

     List<MenuInfo> getMenuInfoList();


     MenuInfo getMenuInfoDetails(@Param("menuId")String menuId);


     List<MenuInfo> getMenuInfoListByParam(MenuInfo menuInfo);

    /**
     * 插入菜单
     * @param menuInfo
     */
     void insertMenuInfo(MenuInfo menuInfo);

    /**
     * 更新菜单
     * @param menuInfo
     */
     void updateMenuInfo(MenuInfo menuInfo);

     void deleteMenuInfo(@Param("menuId")String menuId);
}
