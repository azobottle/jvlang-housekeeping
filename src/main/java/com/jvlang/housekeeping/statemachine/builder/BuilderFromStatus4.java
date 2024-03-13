package com.jvlang.housekeeping.statemachine.builder;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class BuilderFromStatus4 {
    public StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> modifyBuilder
            (StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder){
        builder.externalTransition().from(OrderStatusEnum.WAIT_DISTRIBUTED).to(OrderStatusEnum.WAIT_SERVE)
                .on(OrderEventEnum.DISTRIBUTE);
        builder.externalTransition().from(OrderStatusEnum.WAIT_DISTRIBUTED).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.EXPIRED);
        builder.externalTransition().from(OrderStatusEnum.WAIT_DISTRIBUTED).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.CANCEL_BY_CUSTOMER);
        return builder;
    }
}
