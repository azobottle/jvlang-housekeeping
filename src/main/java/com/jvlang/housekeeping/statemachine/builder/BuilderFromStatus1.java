package com.jvlang.housekeeping.statemachine.builder;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class BuilderFromStatus1 {
    public StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> modifyBuilder
            (StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder){
        builder.externalTransition().from(OrderStatusEnum.WAIT_PAYMENT).to(OrderStatusEnum.WAIT_ACCEPT)
                .on(OrderEventEnum.PAY_WITH_SPEC);
        builder.externalTransition().from(OrderStatusEnum.WAIT_PAYMENT).to(OrderStatusEnum.WAIT_DISTRIBUTED)
                .on(OrderEventEnum.PAY_WITHOUT_SPEC);
        builder.externalTransition().from(OrderStatusEnum.WAIT_PAYMENT).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.EXPIRED);
        builder.externalTransition().from(OrderStatusEnum.WAIT_PAYMENT).to(OrderStatusEnum.OVER)
                .on(OrderEventEnum.CANCEL_BY_CUSTOMER);

        return builder;
    }
}
