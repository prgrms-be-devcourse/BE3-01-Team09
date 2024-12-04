package org.programmer.cafe.domain.orderdetail.entity.dto;

import lombok.Getter;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;

@Getter
public class OrderDetailResponse {

    private Long id;
    private int count;
    private int totalPrice;
    private Long itemId;
    private Long orderId;

    public OrderDetailResponse(final OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.count = orderDetail.getCount();
        this.totalPrice = orderDetail.getTotalPrice();
        this.itemId = orderDetail.getItem().getId();
        this.orderId = orderDetail.getOrder().getId();
    }
}
