package org.programmer.cafe.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.entity.dto2.UserDetailViewResponse;
import org.programmer.cafe.domain.orderdetail.service.OrderDetailService;
import org.programmer.cafe.domain.orderdetail.service2.UserDetailOrderService;
import org.programmer.cafe.facade.facadeservice.UpdateOrderDetailCountService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final UpdateOrderDetailCountService updateOrderDetailCountService;
    private final UserDetailOrderService userDetailOrderService;

    @Operation(summary = "사용자 주문 상세 조회 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse< List<UserDetailViewResponse>>> getAllOrderDetails(@PathVariable Long id) {
        List<UserDetailViewResponse> userDetailViewResponses = userDetailOrderService.findAllOrderDetails(id);
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccess(userDetailViewResponses));
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
