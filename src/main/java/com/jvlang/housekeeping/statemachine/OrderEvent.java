package com.jvlang.housekeeping.statemachine;

import com.jvlang.housekeeping.pojo.entity.Order0;
import lombok.Data;

@Data
public abstract class OrderEvent {
    private Order0 order0;
}
