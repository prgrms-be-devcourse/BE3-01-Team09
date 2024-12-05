package org.programmer.cafe.facade.subservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.entity.dto.OrderMapper;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

// 주문 상태 변경 서비스
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateOrderStatusService {
    private final OrderRepository orderRepository;

    // 주문 상태를 주문 취소로 변경
    public OrderResponse updateOrderStatus(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){log.info("주문 호출 실패 : {}", orderId);}

        if(order.getStatus().equals(OrderStatus.SHIPPING_STARTED)){
            // 배송 완료인 경우
            return null;
        }else {
            order.updateStatus(OrderStatus.CANCEL);

            if(order.getStatus().equals(OrderStatus.CANCEL)){
                log.info("주문 상태 변경 완료");
            }
            return OrderMapper.INSTANCE.toCreateResponseDto(order);
        }
    }
}
