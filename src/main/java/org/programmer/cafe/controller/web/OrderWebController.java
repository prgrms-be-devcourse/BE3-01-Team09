package org.programmer.cafe.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("orders")
public class OrderWebController {

    @GetMapping("/{id}")
    public ModelAndView list(ModelAndView mv, @PathVariable long id) {
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
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
