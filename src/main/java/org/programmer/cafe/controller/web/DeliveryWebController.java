package org.programmer.cafe.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("deliveries")
public class DeliveryWebController {

    @GetMapping("/{id}")
    public ModelAndView deliveryList(ModelAndView mv, @PathVariable long id) {
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
        mv.setViewName("/delivery/list");
        return mv;
    }

    @GetMapping("/{id}/create")
    public ModelAndView deliveryCreate(ModelAndView mv, @PathVariable long id) {
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
        mv.setViewName("/delivery/create");
        return mv;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView deliveryEdit(ModelAndView mv, @PathVariable long id) {
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
        mv.setViewName("/delivery/edit");
        return mv;
    }
}
