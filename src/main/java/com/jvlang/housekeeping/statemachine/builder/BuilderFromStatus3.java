package com.jvlang.housekeeping.statemachine.builder;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class BuilderFromStatus3 {
    public StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> modifyBuilder
            (StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder){
        builder.externalTransition().from(OrderStatusEnum.REJECTED).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.CHOOSE_REFUND);
        builder.externalTransition().from(OrderStatusEnum.REJECTED).to(OrderStatusEnum.WAIT_ACCEPT)
                .on(OrderEventEnum.SPEC_ANOTHER_SHIFU);
        builder.externalTransition().from(OrderStatusEnum.REJECTED).to(OrderStatusEnum.WAIT_DISTRIBUTED)
                .on(OrderEventEnum.TURN_TO_DISTRIBUTE);
        return builder;
    }
}
