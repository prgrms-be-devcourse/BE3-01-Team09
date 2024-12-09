package org.programmer.cafe.domain.auth.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.auth.entity.RefreshToken;
import org.programmer.cafe.domain.auth.repository.RefreshTokenRepository;
import org.programmer.cafe.domain.user.entity.CustomUserDetails;
import org.programmer.cafe.domain.user.service.CustomUserDetailService;
import org.programmer.cafe.global.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService implements LogoutHandler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];

        Claims claims = jwtTokenProvider.getClaims(token);
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(
            claims.getSubject());

        Long userId = customUserDetails.getUser().getId();
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);
        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteById(refreshToken.get().getId());
        } else {
            System.out.println("사용자 정보 오류");
        }
    }
}