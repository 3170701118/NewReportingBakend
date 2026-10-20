package mfg.reporting.CommonUtil;

import java.lang.annotation.*;

/**
 * <p> @Title SystemLog
 * <p> @Description 接口日志注解
 *
 * @author gszhang
 * 2021/4/1 11:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String function_id()  default "";//模块ID
    String operateType() default "OTHER" ;//事件类型：ADD；DELETE；UPDATE；select；UPLOAD；DOWNLOAD；OTHER
}
