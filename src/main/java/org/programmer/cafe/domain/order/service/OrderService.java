package org.programmer.cafe.domain.order.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.entity.dto.OrderMapper;
import org.programmer.cafe.domain.order.entity.dto.OrderRequest;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    // 모든 주문 불러오기
    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders(Long users) {
        return orderRepository.findAllByUser_Id(users).stream()
            .map(OrderResponse::new)
            .collect(Collectors.toList());
    }

    // 특정 주문 불러오기
    @Transactional(readOnly = true)
    public OrderResponse findOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            log.debug("주문 id {}에 해당하는 주문을 찾을 수 없음", orderId);
            return null;
        }

        OrderResponse orderResponse = OrderMapper.INSTANCE.toCreateResponseDto(order);
        log.info("주문 조회 성공 : {}", orderResponse);
        return orderResponse;
    }

     // 주문 상태 수정하기
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){log.info("주문 호출 실패 : {}", orderId);}
        order.updateStatus(OrderStatus.CANCEL);
        return OrderMapper.INSTANCE.toCreateResponseDto(order);
    }
}
