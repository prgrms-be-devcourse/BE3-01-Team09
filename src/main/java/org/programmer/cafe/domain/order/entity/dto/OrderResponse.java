package org.programmer.cafe.domain.order.entity.dto;

import lombok.Getter;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private OrderStatus status;
    private String name;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String itemName;
    private String itemImage;
    private Long user;


    public OrderResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.name = order.getName();
        this.zipcode = order.getZipcode();
        this.address = order.getAddress();
        this.addressDetail = order.getAddressDetail();
        this.itemName = order.getItemName();
        this.itemImage = order.getItemImage();
        this.user = order.getUser().getId();
    }
}
