package org.programmer.cafe.domain.order.dto2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Getter
public class UserOrderRequest {
    @Schema(description = "주문번호", defaultValue = "7")
    private Long id;

    @Schema(description = "결제금액", defaultValue = "23000")
    private int totalPrice;
    @Schema(description = "주문현황", defaultValue = "completed", allowableValues = {"completed", "cancel", "started"})
    private OrderStatus status;
    @Schema(description = "주문인", defaultValue = "order2")
    private String name;
    @Schema(description = "아이템 이름", defaultValue = "Item B")
    private String itemName; // 대표 상품명
    @Schema(description = "아이템 이미지 URL", defaultValue = "item2.jpg")
    private String itemImage; // 대표 상품 이미지
    @Schema(description = "사용자 ID", defaultValue = "2")
    private Long user_id;
}
