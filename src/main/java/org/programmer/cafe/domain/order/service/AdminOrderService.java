package org.programmer.cafe.domain.order.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.AdminOrderResponse;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;

    // 모든 주문 조회
    public List<AdminOrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        log.info("조회된 주문: {}", orders.size());
        return orders.stream().map(AdminOrderResponse::new).collect(Collectors.toList());
    }

}
