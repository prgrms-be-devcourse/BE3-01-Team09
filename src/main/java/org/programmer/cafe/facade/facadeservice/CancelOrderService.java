package org.programmer.cafe.facade.facadeservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.dto.OrderMapper;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.facade.subservice.UpdateItemStockService;
import org.programmer.cafe.facade.subservice.UpdateOrderStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CancelOrderService {

    private final UpdateItemStockService updateItemStockService;
    private final UpdateOrderStatusService updateOrderStatusService;

    @Transactional
    public OrderResponse cancelOrderService(Long orderId) {
        // 주문 상태 변경
        OrderResponse order= updateOrderStatusService.updateOrderStatus(orderId);
        if(order!=null) {
            // 아이템 재고 변경
            updateItemStockService.updateItemStock(orderId);
            log.info("주문 취소 완료");
        }else{
            log.info("이미 완료된 배송입니다.");
        }
        return order;
    }

}
