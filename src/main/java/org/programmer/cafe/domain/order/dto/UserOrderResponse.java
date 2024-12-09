package org.programmer.cafe.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Builder(toBuilder = true)
@Getter
public class UserOrderResponse {

    private Long id;
    private int totalPrice;
    private OrderStatus status;
    private String name;
    private String itemName; // 대표 상품명
    private String itemImage; // 대표 상품 이미지
    private Long user_id;


}
