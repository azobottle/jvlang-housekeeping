package com.jvlang.housekeeping.statemachine.event;

import com.jvlang.housekeeping.statemachine.OrderEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecAnotherShifu extends OrderEvent {
}