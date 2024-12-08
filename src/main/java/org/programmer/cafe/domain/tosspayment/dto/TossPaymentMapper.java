package org.programmer.cafe.domain.tosspayment.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.user.entity.User;

@Mapper
public interface TossPaymentMapper {

    TossPaymentMapper INSTANCE = Mappers.getMapper(TossPaymentMapper.class);

    @Mapping(target = "amountValue", source = "order.totalPrice")
    @Mapping(target = "orderName", expression = "java(order.getItemName() + \"결제건\")")
    @Mapping(target = "customerEmail", source = "user.email")
    @Mapping(target = "customerName", source = "user.name")
    // 별도의 전화번호를 저장하거나, 받고있지 않기에 "01012341234"로 설정
    @Mapping(target = "customerMobilePhone", constant = "01012341234")
    UserOrderInfo toUserOrderInfo(User user, Order order);
}
