package org.programmer.cafe.domain.orderdetail.entity.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;

@Mapper
public interface OrderDetailMapper {
    org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper INSTANCE = Mappers.getMapper(
        org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper.class);
    Order toEntity(OrderDetailResponse orderDetailResponse);
    OrderDetailResponse toCreateResponseDto(OrderDetail orderDetail);
}
