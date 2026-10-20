package mfg.reporting.CommonUtil;

import java.lang.annotation.*;
/**
 * 字典映射转换自定义注解
 * @author : gszhang
 * 2024/8/14 17:22
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelEnumValue {
    Class<? extends Enum<?>> value();
}

