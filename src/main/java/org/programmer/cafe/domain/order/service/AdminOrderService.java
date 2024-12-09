package org.programmer.cafe.domain.order.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.dto.UserOrderResponse;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Page<UserOrderResponse> getOrdersWithPagination(Pageable pageable) {
        return orderRepository.getOrdersWithPagination(pageable);
    }
}
