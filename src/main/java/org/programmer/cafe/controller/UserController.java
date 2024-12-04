package org.programmer.cafe.controller;

import org.programmer.cafe.domain.user.entity.dto.MypageResult;
import org.programmer.cafe.exception.MyPageException;
import org.programmer.cafe.domain.user.service.UserService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage/{id}")
    public ResponseEntity<ApiResponse<Object>> getUser(@PathVariable("id") Long id) {
        try {
            MypageResult result = userService.getUserById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.createSuccess(result));
        } catch (MyPageException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createErrorWithMsg(e.getMyPageLoadStatus().getMessage()));
        }
    }
}
