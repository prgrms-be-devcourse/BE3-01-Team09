package org.programmer.cafe.controller.web;

import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.dto.UserOrderResponse;
import org.programmer.cafe.domain.order.service.UserOrderService;
import org.programmer.cafe.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
@Slf4j
public class OrderWebController {

    private final UserOrderService userOrderService;

    @GetMapping("/{id}")
    public ModelAndView list(ModelAndView mv, @PathVariable long id,
        @PageableDefault(page = 0, size = 5) Pageable pageable) {
        final Page<UserOrderResponse> pagination = userOrderService.getOrdersByUserIdWithPagination(
            id, pageable);
        // TODO : 1 -> id 로 바꿔야 함. session에서 유저 id 값 가져와야함
        mv.addObject("id", 1);
        mv.addObject("pagination", pagination);
        mv.setViewName("order/list");
        return mv;
    }

    @GetMapping("/{id}/detail/{orderId}")
    public ModelAndView detail(ModelAndView mv, @PathVariable long id, @PathVariable long orderId) {
        // TODO : 1 -> orderId 로 바꿔야 함.
        mv.addObject("orderId", 1);
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
        mv.setViewName("order/detail");
        return mv;
    }
}
