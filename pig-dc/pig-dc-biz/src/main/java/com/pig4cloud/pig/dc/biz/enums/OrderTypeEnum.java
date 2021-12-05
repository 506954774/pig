package com.pig4cloud.pig.dc.biz.enums;

/**
 * <p>
 * 订单类型枚举
 *
//0:会员  1:商品
 *
 * </p>
 *
 * @author wz
 * @since 2021-07-2
 */
public enum OrderTypeEnum {
    MEMBER(0,"购买会员"),
    PRODUCT(1,"购买商品"),

    ;
    /**
     * 类型编码
     */
    private Integer typeCode;
    /**
     * 类型名称
     */
    private String typeName;

    OrderTypeEnum(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
    OrderTypeEnum() {
    }
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
