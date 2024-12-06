package org.programmer.cafe.domain.order.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;

@Mapper
public interface OrderMapper {
        OrderMapper INSTANCE = Mappers.getMapper(
            OrderMapper.class);
        Order toEntity(OrderResponse orderResponse);
        OrderResponse toCreateResponseDto(Order order);
        OrderResponse toResponseDto(OrderRequest orderRequest);

        AdminOrderResponse toAdminResponseDto(Order order);
        List<AdminOrderResponse> toResponseDtoList(List<Order> orders);

        AdminOrderResponse toAdminResponseDto(OrderResponse orderResponse);

}
