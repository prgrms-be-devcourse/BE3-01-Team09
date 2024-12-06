package org.programmer.cafe.domain.order.controller2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.OrderMapper;
import org.programmer.cafe.domain.order.dto2.UserOrderViewResponse;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.service2.UserOrderService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {

    private final UserOrderService userorderService;


    @Operation(summary = "전체 주문 조회 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공")
    })
    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<List<UserOrderViewResponse>>> getAllOrdersByUser(
        HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        List<Order> orders = userorderService.findAllOrders(id);
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccess(OrderMapper.INSTANCE.toOrderViewDtoList(orders)));
    }
}

