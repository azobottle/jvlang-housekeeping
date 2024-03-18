package com.jvlang.housekeeping.statemachine.status;

import com.jvlang.housekeeping.statemachine.OrderStatus;
import com.jvlang.housekeeping.statemachine.OrderStatusInfo;

@OrderStatusInfo(id = 5, description = "待服务")
public class WaitServe extends OrderStatus {
}
