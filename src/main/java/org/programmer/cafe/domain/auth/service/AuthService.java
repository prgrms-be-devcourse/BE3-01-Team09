package org.programmer.cafe.domain.auth.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.auth.dto.RefreshTokenRequest;
import org.programmer.cafe.domain.auth.dto.RefreshTokenResponse;
import org.programmer.cafe.domain.auth.entity.RefreshToken;
import org.programmer.cafe.domain.auth.repository.RefreshTokenRepository;
import org.programmer.cafe.domain.user.entity.CustomUserDetails;
import org.programmer.cafe.domain.user.service.CustomUserDetailService;
import org.programmer.cafe.global.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;

    public RefreshTokenResponse regenerateToken(RefreshTokenRequest tokenRequest) {
        Claims claims = jwtTokenProvider.getClaims(tokenRequest.getAccessToken());
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(
            claims.getSubject());

        Long userId = customUserDetails.getUser().getId();
        RefreshTokenResponse refreshTokenResponse = jwtTokenProvider.regenerateToken(
            customUserDetails, getTokenByUserId(userId).getToken());

        updateRefreshToken(refreshTokenResponse.getRefreshToken(), userId);
        return refreshTokenResponse;
    }

    // 사용자 ID를 통해 토큰 검색
    private RefreshToken getTokenByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Refresh Token을 찾을 수 없습니다. : " + userId));
    }

    // 기존 Refresh Token 삭제 후, 새로운 토큰 저장
    public void updateRefreshToken(String refreshToken, Long userId) {
        // 기존 Refresh Token 삭제
        refreshTokenRepository.deleteById(userId);

        // 새로운 Refresh Token 생성 후 저장
        refreshTokenRepository.save(RefreshToken.builder()
            .token(refreshToken)
            .userId(userId)
            .build()
        );
    }
}
