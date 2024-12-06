package org.programmer.cafe.domain.order.entity.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;

@Mapper
public interface OrderMapper {
        org.programmer.cafe.domain.order.entity.dto.OrderMapper INSTANCE = Mappers.getMapper(
            org.programmer.cafe.domain.order.entity.dto.OrderMapper.class);
        Order toEntity(OrderResponse orderResponse);
        OrderResponse toCreateResponseDto(Order order);
        OrderResponse toResponseDto(OrderRequest orderRequest);



}
