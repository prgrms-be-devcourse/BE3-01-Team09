package org.programmer.cafe.domain.order.dto2;

import lombok.Builder;
import lombok.Getter;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Builder
@Getter
public class AdminOrderResponse {
    private Long id;
    private OrderStatus status;
    private String name; // 받는 사람
    private String zipcode;
    private String address;
    private Long userId;// id만 필요
}
