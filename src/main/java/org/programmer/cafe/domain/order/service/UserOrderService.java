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
public class UserOrderService {

    private final OrderRepository orderRepository;

    public List<Order> findAllOrders(Long id) {
        return orderRepository.findAllByUser_Id(id);
    }

    public Page<UserOrderResponse> getOrdersByUserIdWithPagination(long id, Pageable pageable) {
       return orderRepository.getOrdersByUserIdWithPagination(id, pageable);
    }
}
