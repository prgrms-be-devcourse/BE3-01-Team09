package org.programmer.cafe.domain.cart.entity;

import lombok.Getter;

@Getter
public enum CartStatus {

    BEFORE_ORDER("주문 전"),
    PENDING_PAYMENT("결제 중");

    private final String status;

    CartStatus(String status) {
        this.status = status;
    }

}
