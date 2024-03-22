package com.jvlang.housekeeping.statemachine.status;

import com.jvlang.housekeeping.statemachine.OrderStatus;
import com.jvlang.housekeeping.statemachine.OrderStatusInfo;

@OrderStatusInfo(id = 1, description = "待接单")
public class WaitAccept extends OrderStatus {
}
