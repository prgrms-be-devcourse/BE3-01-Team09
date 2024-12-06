package org.programmer.cafe.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    COMPLETED("주문 완료"),
    SHIPPING_STARTED("배송 완료"),
    PENDING_PAYMENT("결제 대기"),
    FAILED_PAYMENT("결제 실패");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
