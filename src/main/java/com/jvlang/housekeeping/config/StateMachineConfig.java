package com.jvlang.housekeeping.config;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.jvlang.housekeeping.pojo.OrderContext;
import com.jvlang.housekeeping.statemachine.OrderEventEnum;
import com.jvlang.housekeeping.statemachine.OrderStatusEnum;
import com.jvlang.housekeeping.statemachine.builder.*;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateMachineConfig {
    @Resource
    private BuilderFromStatus1 builderFromStatus1;
    @Resource
    private BuilderFromStatus2 builderFromStatus2;
    @Resource
    private BuilderFromStatus3 builderFromStatus3;
    @Resource
    private BuilderFromStatus4 builderFromStatus4;
    @Resource
    private BuilderFromStatus5 builderFromStatus5;
    @Resource
    private BuilderFromStatus6 builderFromStatus6;

    @Bean
    public StateMachine<OrderStatusEnum, OrderEventEnum, OrderContext> stateMachine() {
        StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderContext> builder = StateMachineBuilderFactory.create();
        builder = builderFromStatus1.modifyBuilder(builder);
        builder = builderFromStatus2.modifyBuilder(builder);
        builder = builderFromStatus3.modifyBuilder(builder);
        builder = builderFromStatus4.modifyBuilder(builder);
        builder = builderFromStatus5.modifyBuilder(builder);
        builder = builderFromStatus6.modifyBuilder(builder);
        return builder.build("OrderStateMachine");
    }
}
