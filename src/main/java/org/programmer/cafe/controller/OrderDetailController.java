package org.programmer.cafe.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.service.UserDetailOrderService;
import org.programmer.cafe.global.response.ApiResponse;
import org.programmer.cafe.service.UpdateOrderDetailPriceService;
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

    private final UserDetailOrderService userDetailOrderService;

    @Operation(summary = "사용자 주문 상세 조회 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<UserOrderDetailResponse>>> getAllOrderDetails(
        @PathVariable Long id) {
        List<UserOrderDetailResponse> userOrderDetailRespons = userDetailOrderService.findAllOrderDetails(
            id);
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccess(userOrderDetailRespons));
    }

    @Operation(summary = "사용자 주문 수량 변경 API")
    @PutMapping("/update-count/{id}")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 수량 수정 성공")
    })
    public ResponseEntity<ApiResponse<?>> updateOrderCount(@PathVariable("id") Long id,
        @RequestParam Integer count,
        @RequestBody UserOrderDetailRequest userOrderDetailRequest) {

        userDetailOrderService.updateOrderDetailCountService(id, count, userOrderDetailRequest);
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccessWithNoData());
    }

    // 배송지 변경
}
