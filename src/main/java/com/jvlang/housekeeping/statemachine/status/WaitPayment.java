package com.jvlang.housekeeping.statemachine.status;

import com.jvlang.housekeeping.repo.OrderRepository;
import com.jvlang.housekeeping.statemachine.OrderStatus;
import com.jvlang.housekeeping.statemachine.OrderStatusInfo;
import com.jvlang.housekeeping.statemachine.event.PayWithOutSpec;
import com.jvlang.housekeeping.statemachine.event.PayWithSpec;

@OrderStatusInfo(id = 1, description = "待付款")
public class WaitPayment extends OrderStatus {
    private OrderRepository orderRepository;

    @Override
    public WaitAccept whenPayWithSpec(PayWithSpec event) {
        if (event.getMoney() == null || event.getMoney() <= 0) {
            throw new RuntimeException("金额不足");
        }
        return new WaitAccept();
    }

    @Override
    public WaitDistribute whenPayWithOutSpec(PayWithOutSpec event) {

        return new WaitDistribute();
    }

}