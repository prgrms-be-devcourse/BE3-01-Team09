package org.programmer.cafe.domain.orderdetail.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.cart.entity.Cart;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;

@Mapper
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(source = "cart.item", target = "item")
    @Mapping(source = "cart.count", target = "count")
    @Mapping(source = "cart.totalPrice", target = "totalPrice")
    OrderDetail orderAndCartToOrderDetail(Order order, Cart cart);

}
