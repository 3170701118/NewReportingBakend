package mfg.reporting.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import mfg.reporting.service.CommonService;

import mfg.reporting.pojo.FileManage;

/**
 * @author gszhang
 */
@Component
@Slf4j
public class UploadUtil {

  /**管理类型的文件类型 */
  private static final String[] FILE_TYPE = new String[] { "jpg", "png", "jpeg", "doc", "docx", "xls", "xlsx", "ppt","pptx", "txt" ,"zip","7z"};

  /**上传/模板 类型的文件类型 */
  private static final String[] EXCEL_STRINGS = new String[] { "xls", "xlsx" };

  @Resource
  public JwtUtils jwtUtils;

  @Resource
  public CustomParam custom;

  @Resource
  public CommonService commonService;

  /**
   * 管理类型 文件上传
   * @param file 上传的文件
   * @param functionId 附带的上传参数
   * @param uploadType 上传类型，upload/template/manage
   * @return 实现上传管理文件 + 返回上传的文件path
   */
  public Map<String, Object> uploadFileManager(MultipartFile file, String functionId,String uploadType) {
    Map<String, Object> result = new HashMap<>();
      try{
    // param check
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("文件不能为空");
    }
    if (functionId.isEmpty()) {
      throw new IllegalArgumentException("参数function_id 不能为空");
    }
    //权限验证
/*    if (!jwtUtils.havePermission(functionId)) {
      throw new IllegalArgumentException("无权限上传文件");
    }*/

    // 获取 上传路径
    String uploadPath = "";
    if( "manage".equals(uploadType)){
        uploadPath = custom.getManageFileSaveLocation()+functionId;
    }else if("upload".equals(uploadType)){
        uploadPath = custom.getUploadFileSaveLocation();
    }else if("template".equals(uploadType)){
        uploadPath = custom.getTemplateFileSaveLocation();
    }
    //取文件名
    String fileName = file.getOriginalFilename();
    // 判断文件类型是否符合要求,并返回文件名
    String filePath = getManageFilePath(fileName, uploadPath,uploadType);
    File dest = new File(filePath);

    FileManage fileManager = new FileManage();

    //如果没有该文件，说明文件从来没有上传过；
    if (!dest.exists()) {
      try {
        // 如果没有文件目录，则创建上传目录
        if (!dest.getParentFile().exists()) {
          dest.getParentFile().mkdirs();
        }
        //上传文件
        file.transferTo(dest);
        // create 需要插入数据库
        fileManager.setFunctionId(functionId);
        fileManager.setRouteName(jwtUtils.getCurrentRouteName());
        fileManager.setFileName(fileName);
        fileManager.setFilePath(filePath);
        fileManager.setFunctionType("Manage");
        fileManager.setRemark("REMARK");
        fileManager.setCreateUser(jwtUtils.getUsernameFromToken());
        commonService.insertFileManagerDetails(fileManager);
      } catch (Exception e) {
        // 新增上传文件失败，需要目标文件
        // 删除目标文件
        dest.delete();
        throw new IllegalArgumentException("新增上传失败！");
      }
    } else {
        // 如果文件存在，则需要将原来的文件增加当前日期 yyyyMMdd HH24:mm:ss 备份,本地直接覆盖，更新数据库的更新时间
        String backupFilePath = getManageFilePath(fileName + "_" + System.currentTimeMillis(), uploadPath,uploadType);
        // 将此时 filePath 路径的文件 复制到 backupFilePath
        File backupFile = new File(backupFilePath);
      try {
        dest.renameTo(backupFile);
        // 将上传的文件 覆盖到 dest 路径
        file.transferTo(dest);
        // 更新数据库的更新时间
        fileManager.setFileName(fileName);
        fileManager.setFunctionId(functionId);
        fileManager.setUpdateUser(jwtUtils.getUsernameFromToken());
        commonService.updateFileManagerDetails(fileManager);
        backupFile.delete();
      } catch (Exception e) {
        // 更新上传失败，需要恢复原来的文件
        backupFile.renameTo(dest);
        backupFile.delete();

        throw new IllegalArgumentException("更新上传失败");
      }
    }
    }catch (Exception e) {
    throw new RuntimeException(e);
    }
    return result;
  }

    /**
     * 判断文件类型，并返回文件路径
     * @param fileName
     * @param uploadPath
     * @return
     * @throws IOException
     */
    private String getManageFilePath(String fileName, String uploadPath,String uploadType) throws IOException {
        if (fileName != null && !fileName.isEmpty()) {
          String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
          boolean isFileType = false;
            String[] upload_File_Type_Upload = uploadType == "manage"? FILE_TYPE :EXCEL_STRINGS;
          for (String type : upload_File_Type_Upload) {
            if (type.equals(fileType)) {
              isFileType = true;
              break;
            }
          }
          if (!isFileType) {
            throw new IllegalArgumentException("文件类型不符合要求");
          }
        }
        //取根目录 的 file路径
        File dir = new File( uploadPath +"/");

        String filePath = dir.getCanonicalPath()+  "/" + fileName;
        return filePath;
    }
}
