package org.programmer.cafe.controller;
import static org.programmer.cafe.global.response.ApiResponse.createError;
import static org.programmer.cafe.global.response.ApiResponse.createSuccess;

import io.swagger.v3.oas.annotations.Operation;
import org.programmer.cafe.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.OrderRequest;
import org.programmer.cafe.domain.order.dto.OrderResponse;
import org.programmer.cafe.domain.order.service.UserOrderService;
import org.programmer.cafe.facade.facadeservice.CancelOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {

    private final UserOrderService orderService;
    private final CancelOrderService cancelOrderService;

    // 주문 전체 조회
    @GetMapping("/orders")
    public List<OrderResponse> getAllOrdersByUser(HttpSession session) {
        Long id = (Long)session.getAttribute("id");
        return orderService.findAllOrders(id);
    }

    // 주문 상태 변경
    @Operation(summary = "주문 취소 API", description = "주문을 취소하고 재고를 수정하는 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 상태 수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "주문 상태 수정 불가")
    })
    @PutMapping("orders/status/{orderId}")
    public ResponseEntity< ApiResponse<Object>> updateOrderStatus(@PathVariable("orderId") Long orderId,
        @RequestBody OrderRequest orderRequest) {

        if(orderId == null || orderId<=0){
            throw new IllegalArgumentException("order-id를 다시 확인해주세요.");
        }
        OrderResponse updated = cancelOrderService.cancelOrderService(orderId, orderRequest);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createError("이미 배송이 시작되었습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(createSuccess(updated));
    }
}
