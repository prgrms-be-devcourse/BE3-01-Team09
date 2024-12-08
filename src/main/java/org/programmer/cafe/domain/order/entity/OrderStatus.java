package org.programmer.cafe.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum OrderStatus {

    COMPLETED("주문 완료"),
    SHIPPING_STARTED("배송 완료"),
    PENDING_PAYMENT("결제 대기"),
    FAILED_PAYMENT("결제 실패"),
    CANCEL("주문 취소");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getName() {
        return name(); // Enum의 이름 (COMPLETED, SHIPPING_STARTED, CANCEL)을 반환
    }

    @JsonCreator
    public static OrderStatus from(String value) {
        return Arrays.stream(OrderStatus.values())
            .filter(status -> status.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid OrderStatus value: " + value));
    }
}
