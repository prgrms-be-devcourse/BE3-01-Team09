package org.programmer.cafe.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    COMPLETED("주문 완료"),
    SHIPPING_STARTED("배송 완료");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
