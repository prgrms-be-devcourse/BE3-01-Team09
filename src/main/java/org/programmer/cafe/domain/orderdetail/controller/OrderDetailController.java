package org.programmer.cafe.domain.orderdetail.controller;


import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.service.OrderDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    // 주문 상세 내역 가져오기
    @GetMapping("/{order-id}")
    public OrderDetailResponse getOrderDetail( Long orderId) {
        return orderDetailService.findOrderDetail(orderId);
    }
}
