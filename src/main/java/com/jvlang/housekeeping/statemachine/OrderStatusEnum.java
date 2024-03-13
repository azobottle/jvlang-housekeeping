package com.jvlang.housekeeping.statemachine;

public enum OrderStatusEnum {
    WAIT_PAYMENT(1, "待付款"),
    WAIT_ACCEPT(2, "待接单"),
    REJECTED(3, "已拒绝"),
    WAIT_DISTRIBUTED(4, "待派单"),
    WAIT_SERVE(5, "待服务"),
    SERVING(6, "服务中"),
    OVER(7, "结束");

    private final Integer num;
    private final String name;

    OrderStatusEnum(Integer num, String name) {
        this.num = num;
        this.name = name;
    }
}
