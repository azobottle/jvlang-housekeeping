package com.jvlang.housekeeping.statemachine.builder;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class BuilderFromStatus2 {
    public StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> modifyBuilder
            (StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder) {
        builder.externalTransition().from(OrderStatusEnum.WAIT_ACCEPT).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.EXPIRED);
        builder.externalTransition().from(OrderStatusEnum.WAIT_ACCEPT).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.CANCEL_BY_CUSTOMER);
        builder.externalTransition().from(OrderStatusEnum.WAIT_ACCEPT).to(OrderStatusEnum.WAIT_SERVE)
                .on(OrderEventEnum.ACCEPT);
        builder.externalTransition().from(OrderStatusEnum.WAIT_ACCEPT).to(OrderStatusEnum.REJECTED)
                .on(OrderEventEnum.REJECT);
        return builder;
    }
}
