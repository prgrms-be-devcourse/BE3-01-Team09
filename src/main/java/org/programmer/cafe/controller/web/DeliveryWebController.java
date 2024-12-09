package org.programmer.cafe.controller.web;

import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressResponse;
import org.programmer.cafe.domain.deliveryaddress.service.DeliveryAddressService;
import org.programmer.cafe.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("deliveries")
public class DeliveryWebController {

    private final DeliveryAddressService das;

    private final UserService userService;

    public DeliveryWebController(DeliveryAddressService das, UserService userService) {
        this.das = das;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ModelAndView deliveryList(ModelAndView mv, @PathVariable long id) {
        String email = userService.getUserById(id).getEmail();
        List<DeliveryAddressResponse> addressList = das.getAddressByEmail(email);
        mv.addObject("id", id);
        mv.addObject("list", addressList);
        mv.setViewName("/delivery/list");
        return mv;
    }

    @GetMapping("/{id}/create")
    public ModelAndView deliveryCreate(ModelAndView mv, @PathVariable long id) {
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", id);
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
