package org.programmer.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.auth.dto.RefreshTokenRequest;
import org.programmer.cafe.domain.auth.dto.RefreshTokenResponse;
import org.programmer.cafe.domain.auth.service.AuthService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService refreshTokenService;

    /**
     * 토큰 재발급 요청
     *
     * @param tokenRequest
     * @return RefreshTokenResponse
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> regenerateToken(
        @RequestBody RefreshTokenRequest tokenRequest) {
        RefreshTokenResponse tokenResponse = refreshTokenService.regenerateToken(tokenRequest);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(tokenResponse));
    }
}
