package com.jvlang.housekeeping.statemachine;

public enum OrderStatusEnum {
    WAIT_PAYMENT(1, "待付款"),
    WAIT_ACCEPT(2, "待接单"),
    REJECTED(3, "已拒绝"),
    WAIT_DISTRIBUTED(4, "待派单"),
    WAIT_SERVE(5, "待服务"),
    SERVING(6, "服务中"),
    OVER(7, "结束");

    public final Integer id;
    public final String description;

    OrderStatusEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static OrderStatusEnum getEnumById(Integer id) {
        for (OrderStatusEnum value : OrderStatusEnum.values()) {
            if (id.equals(value.id)) {
                return value;
            }
        }
        return null;
    }

    public static OrderStatusEnum getEnumByDescription(String description) {
        for (OrderStatusEnum value : OrderStatusEnum.values()) {
            if (description.equals(value.description)) {
                return value;
            }
        }
        return null;
    }
}
