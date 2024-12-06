package org.programmer.cafe.domain.order.service2;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
