package com.jvlang.housekeeping.statemachine;

public enum OrderEventEnum {
    PAY_WITH_SPEC("支付并指定师傅"),
    PAY_WITHOUT_SPEC("支付但不指定师傅"),
    ACCEPT("师傅接单"),
    EXPIRED("超时"),
    CANCEL_BY_CUSTOMER("用户取消"),
    REJECT("师傅拒绝指定"),
    CHOOSE_REFUND("退款"),
    SPEC_ANOTHER_SHIFU("指定另一位师傅"),
    TURN_TO_DISTRIBUTE("转为派单"),
    DISTRIBUTE("主管派单"),
    CANCEL_BY_SHIFU("师傅取消"),
    START_THE_JOB("开始服务"),
    FAIT_TO_BE_PRESENT("未到场"),
    ACCIDENT_HAPPENED_DURING_THE_JOB("服务中发生意外"),
    CUSTOMER_ACCEPT_THE_JOB("客户验收");
    public Integer id;
    public final String description;

    OrderEventEnum(String description) {
        this.description = description;
    }

    public static OrderEventEnum getEnumById(Integer id) {
        for (OrderEventEnum value : OrderEventEnum.values()) {
            if (id.equals(value.id)) {
                return value;
            }
        }
        return null;
    }

    public static OrderEventEnum getEnumByDescription(String description) {
        for (OrderEventEnum value : OrderEventEnum.values()) {
            if (description.equals(value.description)) {
                return value;
            }
        }
        return null;
    }
}
