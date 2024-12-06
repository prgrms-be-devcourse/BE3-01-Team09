package org.programmer.cafe.domain.order.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.dto2.AdminOrderViewResponse;
import org.programmer.cafe.domain.order.dto2.UserOrderViewResponse;
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
        //
        //응답 : Order -> UserOrderViewResponse 변환
        UserOrderViewResponse toOrderViewDto(Order order);
        List<UserOrderViewResponse> toOrderViewDtoList(List<Order> orders);
        //응답 : Order -> AdminOrderViewResponse 반환
        List<AdminOrderViewResponse> toAdminOrderViewDtoList(List<Order> orders);
}
