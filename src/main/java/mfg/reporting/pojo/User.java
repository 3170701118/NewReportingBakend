package mfg.reporting.pojo;

import com.mybatisflex.annotation.Column;
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
public class User implements Serializable{
  
  @Column(value = "EMP_ID")
  private String empId;

  @Column(value = "CNAME")
  private String cname;

  @Column(value = "EXT")
  private String ext;


  @Column(value = "EMAIL")
  private String email;

  @Column(value = "POSITION")
  private String position;

  @Column(value = "DEPT_ID")
  private String deptId;

  @Column(value = "SUBDEPT_ID")
  private String subDeptId;

  @Column(value = "DEPT_CNAME")
  private String deptCname;

  @Column(value = "SUBJECT_ID")
  private String subjectId;

  @Column(value = "SDEPT_CNAME")
  private String sdeptCname;

  @Column(value = "POWER")
  private Integer power;

  @Column(value = "TITLE")
  private String title;

  @Column(value = "MAG_EMP_ID")
  private String magEmpId;

  @Column(value = "MAG_CNAME")
  private String magCname;


  @Column(value = "AREA_ID")
  private String areaId;


  @Column(value = "DIRECT")
  private String direct;

  @Column(value = "CLS")
  private String cls;

  @Column(value = "ROLE")
  private String role;

}
