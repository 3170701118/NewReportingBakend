package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PageManageMFGMapper {

    @UseDataSource("pdwmfg")
    List<Map<String,Object>> getPageManageMFGList(@Param("module") String module,@Param("parentMenuId") String parentMenuId);

    @UseDataSource("pdwmfg")
    void updateMenuName(@Param("menuId") String menuId, @Param("menuName") String menuName);

    @UseDataSource("pdwmfg")
    void insertPageManageLink(Map<String,String> data);

    @UseDataSource("pdwmfg")
    void updatePageManageLink(Map<String,String> data);

    @UseDataSource("pdwmfg")
    void deletePageManageLink(@Param("menuId") String menuId);
}
