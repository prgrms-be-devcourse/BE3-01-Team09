package org.programmer.cafe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.dto.CreateOrderRequest;
import org.programmer.cafe.domain.order.service.OrderLockFacade;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderLockFacade orderLockFacade;

    @Operation(summary = "장바구니 상품 주문 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "주문 성공")})
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createOrder(
        @RequestBody CreateOrderRequest createOrderRequest) throws InterruptedException {
        // TODO: SecurityContextHolder에서 인증된 유저의 userId를 가져와야 함.
        Long userId = 1L;
        orderLockFacade.order(userId, createOrderRequest);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoData());
    }
}