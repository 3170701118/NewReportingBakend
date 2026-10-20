package mfg.reporting.pojo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "A1MFG.SYS_MENU_LIST", dataSource = "pdwmfg")
public class MenuInfo implements Serializable {

    @Column(value = "MENU_ID")
    private String menuId;

    @Column(value = "PARENT_MENU_ID")
    private String parentMenuId;

    @Column(value = "PARENT_MENU_NAME")
    private String parentMenuName;

    @Column(value = "ROUTE_NAME")
    private String routeName;

    @Column(value = "MENU_NAME")
    private String menuName;

    @Column(value = "MENU_TYPE")
    private String menuType;

    @Column(value = "PATH")
    private String path;

    @Column(value = "RANK")
    private Integer rank;

    @Column(value = "REMARK")
    private String remark;

    @Column(value = "HIDE")
    private String hide;

    @Column(value = "STATUS")
    private String status;

    @Column(value = "ADMIN_FLAG")
    private String adminFlag;

    @Column(value = "CREATE_TIME")
    private String createTime;

    @Column(value = "CREATE_USER")
    private String createUser;

    @Column(value = "UPDATE_TIME")
    private String updateTime;

    @Column(value = "UPDATE_USER")
    private String updateUser;
}
