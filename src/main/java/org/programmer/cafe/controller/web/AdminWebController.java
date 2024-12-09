package org.programmer.cafe.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.ItemStatus;
import org.programmer.cafe.domain.item.entity.dto.GetItemResponse;
import org.programmer.cafe.domain.item.entity.dto.PageItemResponse;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.domain.user.entity.dto.PageUserResponse;
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
@RequestMapping("admins")
@RequiredArgsConstructor
@Slf4j
public class AdminWebController {

    private final ItemService itemService;
    private final UserService userService;

    // http://localhost:8080/admins/items
    @GetMapping("/items")
    public ModelAndView getItemsWithPagination(
        @PageableDefault(page = 0, size = 5) Pageable pageable, ModelAndView mv) {
        final Page<PageItemResponse> pagination = itemService.getItemsWithPagination(pageable);
        mv.addObject("pagination", pagination);
        mv.setViewName("admin/item/item-list");
        return mv;
    }

    // http://localhost:8080/admins/items/create
    @GetMapping("/items/create")
    public ModelAndView createPage(ModelAndView mav) {
        mav.setViewName("admin/item/item-create");
        return mav;
    }

    // http://localhost:8080/admins/items/?/update
    @GetMapping("/items/{id}/update")
    public ModelAndView updatePage(ModelAndView mv, @PathVariable long id) {
        mv.setViewName("admin/item/item-update");
        final GetItemResponse item = itemService.getItem(id);
        mv.addObject("statusList", ItemStatus.values());
        mv.addObject("item", item);
        mv.addObject("id", id);
        return mv;
    }

    // http://localhost:8080/admins/users
    @GetMapping("/users")
    public ModelAndView getUsersWithPagination(
        @PageableDefault(page = 0, size = 5) Pageable pageable, ModelAndView mv) {
        log.info("PAGEBLE : {}", pageable);
        final Page<PageUserResponse> pagination = userService.getUsersWithPagination(pageable);
        mv.addObject("pagination", pagination);
        mv.setViewName("admin/user/user-list");
        return mv;
    }

    // http://localhost:8080/admins/sign-in
    @GetMapping("/sign-in")
    public ModelAndView signIn(ModelAndView mv) {
        mv.setViewName("admin/sign-in");
        return mv;
    }

    @GetMapping("/orders")
    public ModelAndView orders(ModelAndView mv) {
        mv.setViewName("admin/order/order-list");
        return mv;
    }

    @GetMapping("/orders/{id}")
    public ModelAndView orderInfo(ModelAndView mv, @PathVariable long id) {
        // TODO : 1 -> id 로 바꿔야 함.
        mv.addObject("id", 1);
        mv.setViewName("admin/order/order-detail");
        return mv;
    }
}
