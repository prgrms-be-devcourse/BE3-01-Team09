package org.programmer.cafe.domain.orderdetail.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {

    private Long id;
    private int count;
    private int totalPrice;
    private Long itemId;
    private Long orderId;

}