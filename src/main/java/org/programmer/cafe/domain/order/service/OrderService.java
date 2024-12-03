package org.programmer.cafe.domain.order.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.dto.OrderDTO;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    // 해당 사용자의 모든 주문 내역 가져오기
    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrders(Long users) {
        return orderRepository.findAllByUser(users).stream()
            .map(OrderDTO::new)
            .collect(Collectors.toList());
    }
}
