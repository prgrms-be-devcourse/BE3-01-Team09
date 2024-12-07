package org.programmer.cafe.domain.order.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.user.entity.User;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(
        OrderMapper.class);

    UserOrderResponse toOrderViewDto(Order order);

    List<UserOrderResponse> toOrderViewDtoList(List<Order> orders);

    List<org.programmer.cafe.domain.order.dto.AdminOrderResponse> toAdminOrderViewDtoList(
        List<Order> orders);

    UserOrderResponse toUserOrderResponseDto(UserOrderRequest userOrderRequest);

    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "itemName", source = "displayItemName")
    @Mapping(target = "itemImage", source = "displayItemImage")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "name", source = "createOrderRequest.name")
    Order toOrder(CreateOrderRequest createOrderRequest, int totalPrice, String displayItemName,
        String displayItemImage, User user);
}
