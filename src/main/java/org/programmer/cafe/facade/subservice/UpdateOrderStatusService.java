package org.programmer.cafe.facade.subservice;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.dto.OrderMapper;
import org.programmer.cafe.domain.order.dto.OrderRequest;
import org.programmer.cafe.domain.order.dto.OrderResponse;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

// 주문 상태 변경 서비스
@RequiredArgsConstructor
@Service
public class UpdateOrderStatusService {
    private final OrderRepository orderRepository;

    // 주문 상태를 주문 취소로 변경
    public OrderResponse updateOrderStatus(Long orderId, OrderRequest orderRequest, int flag) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Not found item with id: " + orderId));

        if(order.getStatus().equals(OrderStatus.SHIPPING_STARTED)){
            return null;
        }
        if(flag ==2){
            order.updateStatus(OrderStatus.CANCEL); // 상태 변경
        }else if(flag ==1){
            order.updateStatus(OrderStatus.SHIPPING_STARTED);
        }

        orderRepository.save(order); // 변경된 상태 저장
        return OrderMapper.INSTANCE.toCreateResponseDto(order);
    }
}
