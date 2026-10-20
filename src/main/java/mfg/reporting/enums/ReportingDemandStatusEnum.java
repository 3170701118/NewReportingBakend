package mfg.reporting.enums;

/**
 * @author gszhang
 */

public enum ReportingDemandStatusEnum implements DoGetEnum<Integer>{
    /*需要修改的内容 - BEGIN*/
    NotComplete(0, "未开发"),

    OnDeveloper(1, "正在开发"),

    OnTest(2,"上测试环境"),

    OnProduction(3,"已上生产");

    /*需要修改的内容 - END*/

    final Integer enumKey;

    final String enumDesc;


    ReportingDemandStatusEnum(Integer enumKey, String enumDesc) {
        this.enumKey = enumKey;
        this.enumDesc = enumDesc;
    }

    public ReportingDemandStatusEnum getEnumByKey(Integer key) {
        for (ReportingDemandStatusEnum statusEnum : ReportingDemandStatusEnum.values()) {
            if (statusEnum.enumKey.equals(key)) {
                return statusEnum;
            }
        }
        return null;
    }


    /**
     * 根据value获取枚举key值
     *
     * @param value 获取数据
     */
    public ReportingDemandStatusEnum getEnumByValue(String value) {
        for (ReportingDemandStatusEnum statusEnum : ReportingDemandStatusEnum.values()) {
            if (statusEnum.enumDesc.equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }


    @Override
    public String getValueByKey(Integer key) {
        ReportingDemandStatusEnum statusEnum = getEnumByKey(key);
        return statusEnum == null ? null : statusEnum.enumDesc;
    }

    @Override
    public Integer getKeyByValue(String value) {
        ReportingDemandStatusEnum statusEnum = getEnumByValue(enumDesc);
        return statusEnum == null ? null : statusEnum.enumKey;
    }
}
