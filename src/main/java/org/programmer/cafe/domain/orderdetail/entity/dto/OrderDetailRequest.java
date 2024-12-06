package org.programmer.cafe.domain.orderdetail.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.order.entity.Order;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailRequest {

    @Schema(description = "주문 수량", defaultValue = "3")
    private int count;
    @Schema(description = "총액", defaultValue = "3000")
    private int totalPrice;
    @Schema(description = "주문 아이템", defaultValue = "2")
    private Long itemId;
    @Schema(description = "주문 번호", defaultValue = "7")
    private Long orderId;
}
