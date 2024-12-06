package org.programmer.cafe.domain.order.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Optional<Order> getPendingPaymentOrderByUserId(Long userId) {
        return orderRepository.findByUserIdAndStatusIs(userId, OrderStatus.PENDING_PAYMENT);
    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        order.updateStatus(status);
        orderRepository.save(order);
    }
}