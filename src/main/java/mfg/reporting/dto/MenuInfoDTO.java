package mfg.reporting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoDTO implements Serializable {
    private String menuId;
    private String parentMenuId;
    private String parentMenuName;
    private String menuName;
    private String menuPath;
    private String menuType;
    private String path;
    private Integer rank;
    private String remark;
    private String hide;
    private String status;
    private boolean isLeaf;
    private List<MenuInfoDTO> children;
}
