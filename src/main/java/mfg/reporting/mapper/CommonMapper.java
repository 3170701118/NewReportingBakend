package mfg.reporting.mapper;

import mfg.reporting.pojo.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.mybatisflex.annotation.UseDataSource;

/**
 * @author gszhang
 */
@Mapper
public interface CommonMapper {

  @UseDataSource("a3mfg")
  void insertClickRateDetails(ClickRate data);

  @UseDataSource("a3mfg")
  void insertClickRateLogList(List<ClickRate> list);

  @UseDataSource("pdwmfg")
  void insertFileManagerDetails(FileManage data);

  @UseDataSource("pdwmfg")
  void deleteFileManagerDetails(@Param("functionId") String functionId, @Param("fileName") String fileName);

  @UseDataSource("pdwmfg")
  List<FileManage> getFileListTableByFunctionId(@Param("functionId") String functionId);

  @UseDataSource("pdwmfg")
  void updateFileManagerDetails(FileManage data);

  
  @UseDataSource("pdwmfg")
  void mergeFileManagerDetails(FileManage data);


  @UseDataSource("pdwmfg")
  MFGPermission getPermissionInfo(@Param("functionId") String functionId);

  @UseDataSource("pdwmfg")
  FileManage getDownLoadTemplate(@Param("functionId") String functionId, @Param("fileName") String fileName);

  @UseDataSource("pdwmfg")
  FileManage getDownLoadManage(@Param("functionId") String functionId, @Param("fileName") String fileName);

  /**
   * 获取页面 所有functionId 的owner/developer/tooltips/etl/tableName
   * @param menuId 菜单Id
   * @return 返回FunctionList
   */
  @UseDataSource("pdwmfg")
  List<FunctionList> GetFunctionListByRouteName(@Param("menuId") String menuId);

  /**
   * 删除 functionId
   * @param functionId
   */
  @UseDataSource("pdwmfg")
  void deleteFunctionItem(@Param("functionId") String functionId);

  @UseDataSource("pdwmfg")
  FunctionList getFunctionListByFunctionId(@Param("functionId") String functionId);

  @UseDataSource("pdwmfg")
  FunctionList getFunctionInfoDetailsByFunctionId(@Param("functionId") String functionId);

  @UseDataSource("pdwmfg")
  List<FunctionList> searchFunctionList(FunctionList functionList);

  @UseDataSource("pdwmfg")
  void insertFunctionList(FunctionList functionList);


  @UseDataSource("pdwmfg")
  List<MenuInfo> getMenuInfoList(Map<String, String> map);

}
