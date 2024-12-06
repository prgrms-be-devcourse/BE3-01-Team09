package org.programmer.cafe.domain.order.dto2;

import lombok.Builder;
import lombok.Getter;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Builder
@Getter
public class UserOrderViewResponse {

    private Long id;
    private int totalPrice;
    private OrderStatus status;
    private String name;
    private String itemName; // 대표 상품명
    private String itemImage; // 대표 상품 이미지
    private Long user_id;
}
