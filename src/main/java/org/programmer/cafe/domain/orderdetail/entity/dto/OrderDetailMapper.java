package org.programmer.cafe.domain.orderdetail.entity.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;


@Mapper
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);
    // OrderDetailResponse -> OrderDetail
    OrderDetail toEntity(UserOrderDetailRequest userOlderDetailRequest);

}
