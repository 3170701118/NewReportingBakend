package mfg.reporting.pojo;


import java.io.Serializable;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gszhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "A1_MFG.SYS_CLICK_DATE", dataSource = "a3mfg")
public class ClickRate implements Serializable {
  /**
   * 日期
   */
  @Column(value = "SHIFT_DATE")
  private String shiftDate;

  /**
   * 页面名 前端路由配置
   */
  @Column(value = "ROUTE_NAME")
  private String routeName;

  /**
   * 模块ID
   */
  @Column(value = "FUNCTION_ID")
  private String functionId;

  /**
   * 点击人员 email
   */
  @Column(value = "CNAME")
  private String cname;


  /**
   * 点击的功能类型
   * SELECT/UPLOAD/DOWNLOAD/LINK（跳转外部）
   */
  @Column(value = "FUNCTION_TYPE")
  private String functionType;

  /**
   * 点击的后端功能链接
   */
  @Column(value = "LINK")
  private String link;

  /**
   * 日期
   */
  @Column(value = "UPDATE_TIME")
  private String updateTime;


}
