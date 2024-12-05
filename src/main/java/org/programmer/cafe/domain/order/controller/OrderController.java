package org.programmer.cafe.domain.order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.order.service.OrderService;
import org.programmer.cafe.facade.facadeservice.CancelOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final CancelOrderService cancelOrderService;

    // 주문 전체 조회
    @GetMapping("/orders")
    public List<OrderResponse> getAllOrdersByUser(HttpSession session) {
        Long id = (Long)session.getAttribute("id");
        return orderService.findAllOrders(id);
    }

    // 주문 상태 변경
    @PutMapping("orders/status/{orderId}")
    public OrderResponse postOrderStatus(@PathVariable("orderId") long orderId) {
        // null 이면 주문 완료 -> 취소 불가
       return cancelOrderService.cancelOrderService(orderId);
    }
}
