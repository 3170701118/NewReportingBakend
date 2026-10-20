package mfg.reporting.service;

// import java.util.Map;

import mfg.reporting.CommonUtil.CustomParam;
import mfg.reporting.mapper.CommonMapper;
import mfg.reporting.pojo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author gszhang
 */
@Service
public class CommonService {
  @Resource
  public CommonMapper commonMapper;

  @Resource
  public CustomParam customParam;

    /**
     * 根据log 插入点击率
     * @param list 数据
     */
  public void insertClickRateLogList(List<ClickRate> list){
      commonMapper.insertClickRateLogList(list);
  }

    /**
     * 插入上传 文件
     * @param data 数据
     */
  public void insertFileManagerDetails(FileManage data){
      commonMapper.insertFileManagerDetails(data);
  }

    /**
     * 更新文件 update
     * @param data 数据
     */
  public void updateFileManagerDetails(FileManage data){
      commonMapper.updateFileManagerDetails(data);
      }
    /**
     * 更新文件 merge
     * @param data 数据
     */
  public void mergeFileManagerDetails(FileManage data){
      commonMapper.mergeFileManagerDetails(data);
  }

    /**
     * 根据 functionId 获取是否存在权限
     * @param functionId id
     */
  public MFGPermission getPermissionInfo(String functionId){
        return commonMapper.getPermissionInfo(functionId);
    }

    /**
     * 获取template 文件内容
     * @param functionId id
     * @param fileName name
     * @return
     */
  public FileManage getDownLoadTemplate(String functionId,String fileName){
      FileManage fileManage = commonMapper.getDownLoadTemplate(functionId,fileName);
      //使用虚拟路径
        String basePath = customParam.getTemplateFileSaveLocation();
        String filePath = basePath + fileName;
        //虚拟路径转换为真实路径
        fileManage.setFilePath(filePath);
        return fileManage;
  }

    /**
     * 获取manage 文件内容
     * @param functionId id
     * @param fileName name
     * @return
     */
    public FileManage getDownLoadManage(String functionId,String fileName){
        FileManage fileManage = commonMapper.getDownLoadManage(functionId,fileName);
        //使用虚拟路径
        String basePath = customParam.getManageFileSaveLocation();
        String filePath = basePath + fileName;
        //虚拟路径转换为真实路径
        fileManage.setFilePath(filePath);
        return fileManage;
    }

    /**
     * 删除文件
     * @param functionId id
     * @param fileName name
     */
  public void deleteFileByFunctionIdAndFileName(String functionId,String fileName){
      FileManage fileManage = getDownLoadTemplate(functionId,fileName);
      String Path = fileManage.getFilePath();
      File file = new File(Path);
      file.delete();
      commonMapper.deleteFileManagerDetails(functionId,fileName);
  }

    /**
     * 获取menuId 下所有的页面owner 信息
     * @param menuId routeName
     * @return
     */
  public List<FunctionList> GetFunctionListByRouteName(String menuId){
      return commonMapper.GetFunctionListByRouteName(menuId);
  }


    /**
     * 获取指定的functionId for page show
     * @param functionId
     * @return
     */
  public FunctionList getFunctionListByFunctionId(String functionId){
      return commonMapper.getFunctionListByFunctionId(functionId);
  }

    /**
     * 搜索 functionInfo for Form
     * @param functionId 数据
     * @return
     */
  public FunctionList getFunctionInfoDetailsByFunctionId(String functionId){
        return commonMapper.getFunctionInfoDetailsByFunctionId(functionId);
    }
    /**
     * 插入 page function data
     * @param functionList 数据
     */
  public void insertFunctionList(FunctionList functionList){
      commonMapper.insertFunctionList(functionList);
  }

  /**
   * 获取menu 列表
   * @return
   */
  public List<MenuInfo> getMenuList(Map<String, String> map){
        return commonMapper.getMenuInfoList(map);
  }



}
