package org.programmer.cafe.domain.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.dto.OrderDTO;
import org.programmer.cafe.domain.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
// 주문 전체 조회
    // id 받아오기 변경 필요(?)
    @GetMapping("/users/{id}/orders")
    public List<OrderDTO> getAllOrdersByUser(@PathVariable long id) {
        return orderService.findAllOrders(id);
    }
}
