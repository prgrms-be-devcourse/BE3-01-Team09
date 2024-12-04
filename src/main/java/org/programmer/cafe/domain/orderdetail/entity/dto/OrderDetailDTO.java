package org.programmer.cafe.domain.orderdetail.entity.dto;

import lombok.Getter;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;

@Getter
public class OrderDetailDTO {

    private Long id;
    private int count;
    private int totalPrice;
    private Item item;
    private Order order;

    public OrderDetailDTO(final OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.count = orderDetail.getCount();
        this.totalPrice = orderDetail.getTotalPrice();
        this.item = orderDetail.getItem();
        this.order = orderDetail.getOrder();
    }
}
