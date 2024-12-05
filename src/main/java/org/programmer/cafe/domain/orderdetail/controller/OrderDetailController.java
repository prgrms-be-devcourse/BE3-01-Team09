package org.programmer.cafe.domain.orderdetail.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.service.OrderDetailService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    // 주문 상세 내역 가져오기

    @GetMapping("/{order-id}")
    public List<OrderDetailResponse> getOrderDetails(Long orderId) {
        return orderDetailService.findOrderDetails(orderId);
    }


    // 수량 변경
//    @PutMapping("")
//    public ResponseEntity<ApiResponse<UpdateItemResponse>> updateItemStock

    // 배송지 변경
}
