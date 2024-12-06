package org.programmer.cafe.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("users")
public class UserWebController {


    // http://localhost:8080/users/sign-up
    @GetMapping("/sign-up")
    public ModelAndView signUp(ModelAndView mv) {
        mv.setViewName("sign-up");
        return mv;
    }

    // http://localhost:8080/users/sign-in
    @GetMapping("/sign-in")
    public ModelAndView signIn(ModelAndView mv) {
        mv.setViewName("sign-in");
        return mv;
    }
}
