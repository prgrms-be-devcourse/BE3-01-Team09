package org.programmer.cafe.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    }
}
