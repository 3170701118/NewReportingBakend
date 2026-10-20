package mfg.reporting.enums;

/**
 *  converter中反射获取的查询数据的方法，
 * @author : gszhang
 * 2024/8/14 17:22
 */
public interface DoGetEnum<T> {

    /**
     * 根据字典key值获取对应value值，主要用于导出时字典映射转换
     *
     * @param key 字典值
     * @return 描述
     */

    String getValueByKey(T key);

    /**
     * 根据value获取对应字典key值，主要用于导入时字典映射转换
     *
     * @param value 描述
     * @return 字典值
     */
    T getKeyByValue(String value);
}
