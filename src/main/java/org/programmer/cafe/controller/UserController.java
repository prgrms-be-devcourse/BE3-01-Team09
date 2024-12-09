package org.programmer.cafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.user.dto.UserLoginRequest;
import org.programmer.cafe.domain.user.dto.UserLoginResponse;
import org.programmer.cafe.domain.user.dto.UserSignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.entity.dto.MyPageSearchRequest;
import org.programmer.cafe.domain.user.entity.dto.MyPageUpdateResponse;
import org.programmer.cafe.exception.MyPageException;
import org.programmer.cafe.domain.user.service.UserService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 사용자 회원가입 요청 메서드
     *
     * @param userSignupRequest 회원가입  요청 Dto
     * @return ResponseEntity(String)
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> createUser(
        @RequestBody @Valid UserSignupRequest userSignupRequest) {
        Long userId = userService.createUser(userSignupRequest);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("userId = " + userId));
    }

    /**
     * 사용자 로그인 요청 메서드
     *
     * @param userLoginRequest 로그인요청 dto
     * @return ResponseEntity(UserLoginResponse)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(
        @RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(userLoginResponse));
    }

    @GetMapping("/mypage/{id}")
    public ResponseEntity<ApiResponse<Object>> getUser(@PathVariable("id") Long id) {
        try {
            MyPageSearchRequest result = userService.getUserById(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess(result));
        } catch (MyPageException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createErrorWithMsg(e.getMyPageLoadStatus().getMessage()));
        }
    }

    @PatchMapping("/mypage/update")
    public ResponseEntity<ApiResponse<Object>> updateUser(@RequestBody User user) {
        try{
            MyPageUpdateResponse result = userService.patchUser(user);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.createSuccess(result));
        } catch (MyPageException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createErrorWithMsg(e.getMyPageLoadStatus().getMessage()));
        }
    }
    /**
     * 관리자 권한 부여 요청 (현재는 API를 통해 직접 부여)
     *
     * @param userId Long
     */
    @PostMapping("/admin/{userId}")
    public ResponseEntity<ApiResponse<?>> updateAuthority(@PathVariable Long userId) {
        userService.updateAuthority(userId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoData());
    }
}
