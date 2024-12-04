package org.programmer.cafe.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    COMPLETED("주문 완료"),
    SHIPPING_STARTED( "배송 완료"),
    CANCEL("주문 취소");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
