package com.jvlang.housekeeping.statemachine.status;

import com.jvlang.housekeeping.statemachine.OrderStatus;
import com.jvlang.housekeeping.statemachine.OrderStatusInfo;

@OrderStatusInfo(id = 3, description = "已拒绝")
public class Rejected extends OrderStatus {
}
