package com.jvlang.housekeeping.statemachine;

import com.jvlang.housekeeping.statemachine.event.*;

public abstract class OrderStatus {
    public static Integer getId(Class<? extends OrderStatus> orderStatus) {
        return orderStatus.getAnnotation(OrderStatusInfo.class).id();
    }

    public static String getDescription(Class<? extends OrderStatus> orderStatus) {
        return orderStatus.getAnnotation(OrderStatusInfo.class).description();
    }

    public OrderStatus whenPayWithSpec(PayWithSpec event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenPayWithOutSpec(PayWithOutSpec event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenAccept(Accept event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenExpired(Expired event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenCancelByCustomer(CancelByCustomer event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenRejectByShifu(RejectByShifu event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenChooseRefund(ChooseRefund event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenSpecAnotherShifu(SpecAnotherShifu event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenTurnToDistribute(TurnToDistribute event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenDistributed(Distributed event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenCancelByShifu(CancelByShifu event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenStartTheJob(StartTheJob event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenFailToBePresent(FailToBePresent event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenAccidentHappenedDuringTheJob(AccidentHappenedDuringTheJob event) {
        throw new RuntimeException("非法操作");
    }

    public OrderStatus whenCustomerAcceptTheJob(CustomerAcceptTheJob event) {
        throw new RuntimeException("非法操作");
    }


}
