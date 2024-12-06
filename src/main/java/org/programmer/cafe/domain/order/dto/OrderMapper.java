package org.programmer.cafe.domain.order.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(
        OrderMapper.class);

    UserOrderResponse toOrderViewDto(Order order);

    List<UserOrderResponse> toOrderViewDtoList(List<Order> orders);

    List<org.programmer.cafe.domain.order.dto.AdminOrderResponse> toAdminOrderViewDtoList(
        List<Order> orders);

    UserOrderResponse toUserOrderResponseDto(UserOrderRequest userOrderRequest);
}
