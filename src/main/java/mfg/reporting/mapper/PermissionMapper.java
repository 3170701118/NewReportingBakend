package mfg.reporting.mapper;

import com.mybatisflex.annotation.UseDataSource;
import mfg.reporting.dto.MFGPermissionTableDTO;
import mfg.reporting.pojo.MFGPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PermissionMapper {

     @UseDataSource("pdwmfg")
     List<MFGPermissionTableDTO> searchTableList(Map<String,String> data);

     @UseDataSource("pdwmfg")
     void insertPermissionItem( MFGPermission data);

     @UseDataSource("pdwmfg")
     void updatePermissionItem( MFGPermission data);

     @UseDataSource("pdwmfg")
     void deletePermissionItem(@Param("functionId") String functionId);
}

