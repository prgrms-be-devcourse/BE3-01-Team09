package org.programmer.cafe.controller.web;

import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.entity.dto.MyPageSearchRequest;
import org.programmer.cafe.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("users")
public class UserWebController {

    private final UserService userService;

    public UserWebController(final UserService userService) {
        this.userService = userService;
    }

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

    // http://localhost:8080/users/1
    @GetMapping("/{id}")
    public ModelAndView info(ModelAndView mv, @PathVariable long id) {
        MyPageSearchRequest user = userService.getUserById(id);
        mv.addObject("name", user.getName());
        mv.addObject("email", user.getEmail());
        mv.addObject("id", id);
        mv.setViewName("/user/info");
        return mv;
    }

    // http://localhost:8080/users/1/edit
    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView mv, @PathVariable long id) {
        MyPageSearchRequest user = userService.getUserById(id);
        mv.addObject("name", user.getName());
        mv.addObject("email", user.getEmail());
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
        mv.setViewName("/user/edit");
        return mv;
    }
}
