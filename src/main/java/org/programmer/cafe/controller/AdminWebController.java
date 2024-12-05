package org.programmer.cafe.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.dto.PageItemResponse;
import org.programmer.cafe.domain.item.service.ItemService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admins")
@RequiredArgsConstructor
@Slf4j
public class AdminWebController {

    private final ItemService itemService;

    @GetMapping("/items")
    public ModelAndView getItemsWithPagination(
        @PageableDefault(page = 0, size = 5) Pageable pageable, ModelAndView mv) {
        final Page<PageItemResponse> pagination = itemService.getItemsWithPagination(pageable);
        mv.addObject("pagination", pagination);
        mv.setViewName("admin/item-list");
        return mv;
    }

    @GetMapping("/items/create")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("admin/item-create");
//        mav.addObject("name", "USER");
        return mav;
    }
}
