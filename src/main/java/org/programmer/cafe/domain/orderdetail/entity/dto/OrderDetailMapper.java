package org.programmer.cafe.domain.orderdetail.entity.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.dto.OrderRequest;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;

@Mapper
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    // OrderDetail -> OrderDetailResponse
    OrderDetailResponse toCreateResponseDto(OrderDetail orderDetail);
    // OrderDetailResponse -> OrderDetail
    OrderDetail toEntity(OrderDetailResponse orderDetailResponse);
    // OrderDetailRequest -> OrderDetailResponse
    OrderDetailResponse toDto(OrderDetailRequest orderDetailRequest);


}
