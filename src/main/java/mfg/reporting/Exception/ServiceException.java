package mfg.reporting.Exception;

import mfg.reporting.enums.BaseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gszhang
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ServiceException extends RuntimeException {

  private int code;
	private String message;

  public ServiceException(String message){
      super(message);
      this.message=message;
  }

  public ServiceException(int code,String message){
      super(message);
      this.code=code;
      this.message=message;
  }

  public ServiceException(BaseCodeEnum baseCodeEnum){
      this(baseCodeEnum.getCode(),baseCodeEnum.getMessage());
  }
}
