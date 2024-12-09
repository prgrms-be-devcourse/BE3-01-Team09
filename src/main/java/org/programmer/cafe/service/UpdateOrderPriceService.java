package org.programmer.cafe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailRequest;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;

// 주문 금액 변경
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateOrderPriceService {

    private final OrderRepository orderRepository;

    public void updateOrderPrice(Long orderId, UserOrderDetailRequest userOrderDetailRequest,
        int oldTotalPrice, int oldPayment) {

        int newPayment = (oldPayment - oldTotalPrice) + userOrderDetailRequest.getTotalPrice();

        UserOrderDetailRequest updatedRequest = userOrderDetailRequest.toBuilder()
            .totalPrice(newPayment)
            .build();

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));

        order.updateTotalPrice(newPayment);

    }
}
