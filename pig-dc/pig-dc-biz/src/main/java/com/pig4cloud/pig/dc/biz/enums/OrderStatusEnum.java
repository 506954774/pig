package com.pig4cloud.pig.dc.biz.enums;

/**
 * <p>
 * 订单状态枚举
 *
//订单状态: 0预支付 1已取消 2已支付 3申请退款中 4,已全部退款完毕 5已核销
 *
 * </p>
 *
 * @author wz
 * @since 2021-07-2
 */
public enum OrderStatusEnum {
    PREPAY(0,"待支付"),
    CANCEL(1,"已取消"),
    PAID(2,"已支付"),
    REFOUND_APPLY(3,"申请退款中"),
    APPLIED(4,"已全部退款完毕"),
    FINISHED(5,"已核销"),

    ;
    /**
     * 类型编码
     */
    private Integer typeCode;
    /**
     * 类型名称
     */
    private String typeName;

    OrderStatusEnum(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
    OrderStatusEnum() {
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
