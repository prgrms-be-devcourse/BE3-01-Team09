package org.programmer.cafe.domain.order.dto;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrderResponse {

    private Long id;
    private int totalPrice;
    private OrderStatus status;
    private String name; // 받는 분 성함
    private String itemName; // 대표 상품명
    private String itemImage; // 대표 상품 이미지
//    private Long user_id;
    private String address;
    private String addressDetail;
    private String createdAt;
    private String updatedAt;
}
