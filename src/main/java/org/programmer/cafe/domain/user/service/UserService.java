package org.programmer.cafe.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.auth.entity.RefreshToken;
import org.programmer.cafe.domain.user.entity.UserTokens;
import org.programmer.cafe.domain.auth.repository.RefreshTokenRepository;
import org.programmer.cafe.domain.authority.Role;
import org.programmer.cafe.domain.authority.entity.Authority;
import org.programmer.cafe.domain.user.dto.UserLoginRequest;
import org.programmer.cafe.domain.user.dto.UserLoginResponse;
import org.programmer.cafe.domain.user.dto.UserSignupRequest;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.NotFoundException;
import org.programmer.cafe.global.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.programmer.cafe.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * 사용자 회원가입 메서드
     *
     * @param userSignupRequest 요청 Dto
     * @return Long userId
     */
    public Long createUser(UserSignupRequest userSignupRequest) {
        // 사용자 이메일 중복 확인
        if (userRepository.existsByEmail(userSignupRequest.getEmail())) {
            log.warn("Email already exists {}", userSignupRequest.getEmail());
            throw new BadRequestException(USER_ALREADY_EXIST);
        }

        // 사용자 생성
        User user = User.builder()
            .email(userSignupRequest.getEmail())
            .password(passwordEncoder.encode(userSignupRequest.getPassword()))
            .name(userSignupRequest.getName())
            .build();

        // 사용자 권한 부여
        user.getAuthorities().add(
            Authority.builder()
                .user(user)
                .role(Role.MEMBER)
                .build()
        );
        return userRepository.save(user).getId();
    }

    /**
     * 사용자 로그인 메서드
     *
     * @param userLoginRequest
     * @return UserLoginResponse(Access Token, Refresh Token 포함)
     */
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        // email을 통해 사용자 검색
        User user = userRepository
            .findByEmail(userLoginRequest.getEmail())
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        // 비밀번호 불일치시 예외처리
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException(INVALID_PASSWORD);
        }

        // 유저 인증 객체 생성하여 인증처리
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),
                userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        // AccessToken, RefreshToken 발금하여 로그인 정보 반환
        UserTokens tokens = jwtTokenProvider.generateTokens(authentication);

        // 기존 Refresh Token 삭제
        if (refreshTokenRepository.existsByUserId(user.getId())) {
            refreshTokenRepository.deleteByUserId(user.getId());
        }
        // 새로운 Refresh Token 생성 후 저장
        refreshTokenRepository.save(RefreshToken.builder()
            .token(tokens.getRefreshToken())
            .userId(user.getId())
            .build()
        );

        // 사용자 권한 목록
        Set<Role> roles = user.getAuthorities()
            .stream().map(Authority::getRole).collect(Collectors.toSet());

        return UserLoginResponse.builder()
            .userId(user.getId())
            .role(roles)
            .accessToken(tokens.getAccessToken())
            .refreshToken(tokens.getRefreshToken())
            .build();
    }
}
