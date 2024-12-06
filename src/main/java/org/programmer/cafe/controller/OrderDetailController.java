package org.programmer.cafe.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.service.OrderDetailService;
import org.programmer.cafe.facade.facadeservice.UpdateOrderDetailCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final UpdateOrderDetailCountService updateOrderDetailCountService;

    // 주문 상세 내역 가져오기
    @GetMapping("/{order-id}")
    public List<OrderDetailResponse> getOrderDetails(Long orderId) {
        return orderDetailService.findOrderDetails(orderId);
    }

    // 주문 수량 변경
    @PutMapping("/update-count/{order-detail-id}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 수량 수정 성공")
    })
    public OrderDetailResponse updateOrderCount(@PathVariable("order-detail-id") Long orderDetailId, @RequestParam Integer count,
        @RequestBody OrderDetailRequest orderDetailRequest) {
        return updateOrderDetailCountService.UpdateOrderDetailCountService(orderDetailId, count, orderDetailRequest);
    }

    // 배송지 변경
}
