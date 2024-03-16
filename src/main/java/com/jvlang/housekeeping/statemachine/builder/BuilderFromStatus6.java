package com.jvlang.housekeeping.statemachine.builder;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class BuilderFromStatus6 {
    public StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> modifyBuilder
            (StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder) {
        builder.externalTransition().from(OrderStatusEnum.SERVING).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.ACCIDENT_HAPPENED_DURING_THE_JOB);
        builder.externalTransition().from(OrderStatusEnum.SERVING).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.CUSTOMER_ACCEPT_THE_JOB);
        return builder;
    }
}
