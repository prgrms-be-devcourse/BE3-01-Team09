package org.programmer.cafe.global.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.auth.dto.RefreshTokenResponse;
import org.programmer.cafe.domain.user.entity.UserTokens;
import org.programmer.cafe.domain.user.entity.CustomUserDetails;
import org.programmer.cafe.domain.user.service.CustomUserDetailService;
import org.programmer.cafe.exception.AuthException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static org.programmer.cafe.exception.ErrorCode.*;

/**
 * JWT Token 관리 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final CustomUserDetailService customUserDetailService;
    private static final String AUTHORITIES_KEY = "authorities";

    public UserTokens generateTokens(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();

        String accessToken = generateAccessToken(customUserDetails, now);
        String refreshToken = generateRefreshToken(customUserDetails, now);

        return UserTokens.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    // JWT Access Token 생성 메소드
    private String generateAccessToken(CustomUserDetails userDetails, Date now) {
        // 권한 가져오기
        String authorities = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(",")); // e.g. "ADMIN, USER"

        // 토큰 생성하여 반환 (권한목록 추가)
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setSubject(userDetails.getEmail()) //사용자 이메일정보 활용
            .claim(AUTHORITIES_KEY, authorities) // 권힌 claim 추가
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + jwtProperties.getAccessExpirationTime()))
            .signWith(jwtProperties.getSecretKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT Refresh Token 생성 메서드
    private String generateRefreshToken(CustomUserDetails userDetails, Date now) {
        // 토큰 생성하여 반환
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setSubject(userDetails.getEmail()) //사용자 이메일정보 활용
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshExpirationTime()))
            .signWith(jwtProperties.getSecretKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // 토큰 기반으로 인증 정보를 가져오는 메소드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        // 권한 정보 존재 토큰 확인
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new AuthException(INVALID_AUTHORITY_TOKEN);
        }

        // 토큰에서 사용자 정보, 권한 정보 추출
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());
        List<SimpleGrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new).toList();

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    // JWT Access Token 유효성 검증 메서드
    public boolean validateAccessToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info(EXPIRED_ACCESS_TOKEN.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            log.info(INVALID_ACCESS_TOKEN.getMessage());
        }
        return false;
    }

    // JWT Refresh Token 유효성 검증 메소드
    public boolean validateRefreshToken(String refreshToken) {
        try {
            getClaims(refreshToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.info(EXPIRED_REFRESH_TOKEN.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            log.info(INVALID_REFRESH_TOKEN.getMessage());
        }
        return false;
    }

    // 만료된 Access Token 재발급
    public RefreshTokenResponse regenerateToken(CustomUserDetails customUserDetails,
        String refreshToken) {
        Long userId = customUserDetails.getUser().getId();

        // Refresh Token 검증
        String newRefreshToken = refreshToken;

        if (!validateRefreshToken(refreshToken)) {
            // Refresh Token 재발급
            newRefreshToken = generateRefreshToken(customUserDetails, new Date());
        }
        // 새로운 access token 생성
        String newAccessToken = generateAccessToken(customUserDetails, new Date());

        // Access Token, Refresh Token 반환
        return RefreshTokenResponse.builder()
            .userId(userId)
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .build();
    }

    public String getEmail(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(jwtProperties.getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}