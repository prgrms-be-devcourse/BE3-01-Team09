package org.programmer.cafe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.OrderMapper;
import org.programmer.cafe.domain.order.dto2.UserOrderRequest;
import org.programmer.cafe.domain.order.dto2.UserOrderResponse;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.service.UserOrderService;
import org.programmer.cafe.facade.changestatus.UpdateStatusService;
import org.programmer.cafe.facade.changestatus.UpdateStockService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final UserOrderService userorderService;
    private final UpdateStatusService updateStatusService;
    private final UpdateStockService updateStockService;


    @Operation(summary = "전체 주문 조회 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<UserOrderResponse>>> getAllOrdersByUser(
        HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        List<Order> orders = userorderService.findAllOrders(id);
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccess(OrderMapper.INSTANCE.toOrderViewDtoList(orders)));
    }

    @Operation(summary = "주문 취소 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 취소 성공")
    })
    @Transactional
    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApiResponse<?>> updateOrderStatus(@PathVariable Long id,
        @RequestBody UserOrderRequest userOrderRequest, @RequestParam OrderStatus updateStatus) {

            updateStatusService.updateStatus(id, updateStatus);
            if (updateStatus.equals(OrderStatus.COMPLETED)) {
                updateStockService.updateOrderStatus(id);
            }

        return ResponseEntity.ok()
            .body(ApiResponse.createSuccessWithNoData());
    }
}

