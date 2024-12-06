package org.programmer.cafe.facade.facadeservice;

import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.entity.dto.OrderRequest;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.facade.subservice.UpdateAllItemStockService;
import org.programmer.cafe.facade.subservice.UpdateOrderStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CancelOrderService {

    private final UpdateAllItemStockService updateAllItemStockService;
    private final UpdateOrderStatusService updateOrderStatusService;

    @Transactional
    public OrderResponse cancelOrderService(Long orderId, OrderRequest orderRequest) {
        // 주문 상태 변경
        OrderResponse order= updateOrderStatusService.updateOrderStatus(orderId, orderRequest);

        if(order!=null) {
            updateAllItemStockService.updateAllItemStock(orderId);
        }
        return order;
    }

}
