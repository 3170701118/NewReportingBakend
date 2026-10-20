package mfg.reporting.service;

import mfg.reporting.dto.MFGPermissionTableDTO;
import mfg.reporting.mapper.PermissionMapper;
import mfg.reporting.pojo.FunctionList;
import mfg.reporting.pojo.MFGPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService {

    @Resource
    public PermissionMapper permissionMapper;

    public List<MFGPermissionTableDTO> searchTableList(Map<String,String> data){
        return permissionMapper.searchTableList(data);
    }

    public void insertPermissionItem(MFGPermission data){
        permissionMapper.insertPermissionItem(data);
    }

    public void updatePermissionItem(MFGPermission data){
        permissionMapper.updatePermissionItem(data);
    }

    public void deletePermissionItem(String functionId){
        permissionMapper.deletePermissionItem(functionId);
    }
}
