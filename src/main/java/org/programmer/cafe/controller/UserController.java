package org.programmer.cafe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.user.dto.UserLoginRequest;
import org.programmer.cafe.domain.user.dto.UserLoginResponse;
import org.programmer.cafe.domain.user.dto.UserSignupRequest;
import org.programmer.cafe.domain.user.service.UserService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
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
}
