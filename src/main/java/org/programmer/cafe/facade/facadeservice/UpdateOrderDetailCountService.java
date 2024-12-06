package org.programmer.cafe.facade.facadeservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.dto.OrderRequest;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.service.OrderDetailService;
import org.programmer.cafe.facade.subservice.UpdateItemStockService;
import org.programmer.cafe.facade.subservice.UpdateOrderDetailPriceService;
import org.programmer.cafe.facade.subservice.UpdateOrderPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateOrderDetailCountService {

    private final UpdateItemStockService updateItemStockService;
    private final UpdateOrderDetailPriceService updateOrderDetailPriceService;
    private final UpdateOrderPriceService orderPriceService;

    @Transactional
    public OrderDetailResponse UpdateOrderDetailCountService(Long orderDetailId, int newCount, OrderDetailRequest orderDetailRequest) {
        // 재고 변경
        updateItemStockService.updateItemStock(orderDetailId, newCount, orderDetailRequest);
        // 총액 변경
        OrderDetailResponse orderDetailResponse= updateOrderDetailPriceService.UpdateOrderDetailPriceService(orderDetailId,orderDetailRequest);
        // 결제 금액 변경
        orderPriceService.updateOrderPrice(orderDetailId, orderDetailRequest);

        return orderDetailResponse;
    }
}
