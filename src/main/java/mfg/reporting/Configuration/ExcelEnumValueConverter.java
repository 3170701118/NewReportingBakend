package mfg.reporting.Configuration;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import mfg.reporting.CommonUtil.ExcelEnumValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义converter
 * @author : CloverAn
 * 2024/8/14 17:22
 */
public class ExcelEnumValueConverter implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        ExcelEnumValue enumValue = contentProperty.getField().getAnnotation(ExcelEnumValue.class);
        Class<?> enumClass = enumValue.value();
        Object enumConstant = enumClass.getEnumConstants()[0];
        Method method;
        Object invoke = null;
        try {
            //获取枚举类中的方法并执行，以期望获取到对应的value值数据
            method = enumClass.getDeclaredMethod("getValueByKey", Object.class);
            invoke = method.invoke(enumConstant, value);
        } catch (NoSuchMethodException e) {
            //抛出getValueByKey方法未找到异常
        } catch (InvocationTargetException | IllegalAccessException e) {
            //抛出getValueByKey方法调用失败异常
        }

        //注意，当枚举类中没有对应的key时，或者对应key的value是null时，invoke会返回null，此时会出现空指针。
        if (invoke == null) {
            invoke = "未找到指定key对应的value值";
        }
        return new WriteCellData<>(invoke.toString());
    }

    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        ExcelEnumValue enumValue = contentProperty.getField().getAnnotation(ExcelEnumValue.class);
        Class<?> enumClass = enumValue.value();
        Object enumConstant = enumClass.getEnumConstants()[0];
        Method method;
        Object invoke = null;
        try {
            //获取枚举类中的方法并执行，以期望获取到对应的key值数据
            method = enumClass.getDeclaredMethod("getKeyByValue", String.class);
            invoke = method.invoke(enumConstant, stringValue);
        } catch (NoSuchMethodException e) {
            try {
                //获取枚举类中的方法并执行，以期望获取到对应的key值数据
                method = enumClass.getDeclaredMethod("getKeyByValue", Object.class);
                invoke = method.invoke(enumConstant, stringValue);
            } catch (NoSuchMethodException ex) {
                //抛出getKeyByValue方法未找到异常
            }
        }

        //Method method = null;
        //try {
        //    method = enumClass.getDeclaredMethod("getKeyByValue", String.class);
        //} catch (NoSuchMethodException e) {
        //    try {
        //        method = enumClass.getDeclaredMethod("getKeyByValue", Object.class);
        //    } catch (NoSuchMethodException ex) {
        //        //抛出getKeyByValue方法未找到异常
        //    }
        //}
        //Object invoke = null;
        //try {
        //    invoke = method.invoke(enumConstant, stringValue);
        //} catch (Exception e) {
        //    //抛出getKeyByValue方法调用失败异常
        //}

        //注意，当枚举类中没有对应的value时，或者对应value的key是null时，invoke会返回null，此时会出现空指针。
        if (invoke == null) {
            invoke = "未找到指定value对应的key值";
        }
        return invoke;
    }

}
