package com.jvlang.housekeeping.statemachine.builder;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class BuilderFromStatus5 {
    public StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> modifyBuilder
            (StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder){
        builder.externalTransition().from(OrderStatusEnum.WAIT_SERVE).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.CANCEL_BY_SHIFU);
        builder.externalTransition().from(OrderStatusEnum.WAIT_SERVE).to(OrderStatusEnum.SERVING)
                .on(OrderEventEnum.START_THE_JOB);
        builder.externalTransition().from(OrderStatusEnum.WAIT_SERVE).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.FAIT_TO_BE_PRESENT);
        return builder;
    }
}
