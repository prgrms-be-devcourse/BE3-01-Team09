package org.programmer.cafe.domain.orderdetail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.orderdetail.entity.dto2.UserDetailViewResponse;
import org.programmer.cafe.domain.orderdetail.service2.UserDetailOrderService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("domainOrderDetailController")
@RequestMapping("/api/order-detail")
public class OrderDetailController {

    private final UserDetailOrderService userDetailOrderService;

    @Operation(summary = "사용자 주문 상세 조회 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공")
    })
    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse< List<UserDetailViewResponse>>> getAllOrderDetails(@PathVariable Long orderId) {
        List<UserDetailViewResponse> userDetailViewResponses = userDetailOrderService.findAllOrderDetails(orderId);
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccess(userDetailViewResponses));
    }
}
