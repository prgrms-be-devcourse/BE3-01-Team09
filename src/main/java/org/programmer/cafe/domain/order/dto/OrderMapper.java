package org.programmer.cafe.domain.order.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.user.entity.User;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "itemName", source = "displayItemName")
    @Mapping(target = "itemImage", source = "displayItemImage")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "name", source = "createOrderRequest.name")
    Order toOrder(CreateOrderRequest createOrderRequest, int totalPrice, String displayItemName,
        String displayItemImage, User user);
}
