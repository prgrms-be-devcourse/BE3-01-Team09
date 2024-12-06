package org.programmer.cafe.domain.order.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.dto2.UserOrderRequest;
import org.programmer.cafe.domain.order.dto2.UserOrderResponse;
import org.programmer.cafe.domain.order.entity.Order;

@Mapper
public interface OrderMapper {
        OrderMapper INSTANCE = Mappers.getMapper(
            OrderMapper.class);
        Order toEntity(OrderResponse orderResponse);
        OrderResponse toCreateResponseDto(Order order);
        OrderResponse toResponseDto(OrderRequest orderRequest);

        org.programmer.cafe.domain.order.dto.AdminOrderResponse toAdminResponseDto(Order order);
        List<org.programmer.cafe.domain.order.dto.AdminOrderResponse> toResponseDtoList(List<Order> orders);

        org.programmer.cafe.domain.order.dto.AdminOrderResponse toAdminResponseDto(OrderResponse orderResponse);
        //추가
        //응답 : Order -> UserOrderViewResponse 변환
        UserOrderResponse toOrderViewDto(Order order);
        List<UserOrderResponse> toOrderViewDtoList(List<Order> orders);
        //응답 : Order -> AdminOrderViewResponse 반환
        List<org.programmer.cafe.domain.order.dto2.AdminOrderResponse> toAdminOrderViewDtoList(List<Order> orders);
        //UserOrderRequest -> UserOrderResponse
        UserOrderResponse toUserOrderResponseDto(UserOrderRequest userOrderRequest);
}
