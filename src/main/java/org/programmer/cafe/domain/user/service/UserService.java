package org.programmer.cafe.domain.user.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.auth.entity.RefreshToken;
import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;
import org.programmer.cafe.domain.user.entity.UserTokens;
import org.programmer.cafe.domain.auth.repository.RefreshTokenRepository;
import org.programmer.cafe.domain.authority.Role;
import org.programmer.cafe.domain.authority.entity.Authority;
import org.programmer.cafe.domain.user.dto.UserLoginRequest;
import org.programmer.cafe.domain.user.dto.UserLoginResponse;
import org.programmer.cafe.domain.user.dto.UserSignupRequest;
import org.programmer.cafe.domain.user.entity.dto.MyPageSearchRequest;
import org.programmer.cafe.domain.user.entity.MyPageStatus;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.entity.dto.MyPageUpdateResponse;
import org.programmer.cafe.domain.user.entity.dto.PageUserResponse;
import org.programmer.cafe.domain.user.repository.UserProjection;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.NotFoundException;
import org.programmer.cafe.global.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.programmer.cafe.exception.MyPageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final EntityManagerFactory emf;

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
                .role(Role.ROLE_MEMBER)
                .build()
        );

        // 배송지 등록
        user.getDeliveryAddress().add(
            DeliveryAddress.builder()
                .name(userSignupRequest.getAddressName())
                .zipcode(userSignupRequest.getZipcode())
                .address(userSignupRequest.getAddress())
                .addressDetail(userSignupRequest.getAddressDetail())
                .defaultYn(true)
                .user(user)
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

    /**
     * 사용자에게 관리자 권한 부여 메서드
     */
    public void updateAuthority(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Authority authority = Authority.builder()
            .user(user)
            .role(Role.ROLE_ADMIN)
            .build();

        user.updateAuthority(authority);
    }

    public MyPageSearchRequest getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new MyPageException(MyPageStatus.INVALID_ID);
        }
        UserProjection user = userRepository.findProjectionById(id)
            .orElseThrow(() -> new MyPageException(MyPageStatus.USER_NOT_FOUND));
        return new MyPageSearchRequest(user.getId(), user.getName(), user.getEmail());
    }

    //patchUser 사용자 입력 검증
    @Transactional
    public MyPageUpdateResponse patchUser(User user) {
        validateInput(user);

        UserProjection foundUser = findUserByEmail(user.getEmail());
        checkForChanges(user, foundUser);

        return updateUserInfo(user);
    }

    // 웹앱에서 에서 비밀번호 및 아이디 미입력시
    private void validateInput(User user) {
        if (user.getPassword() == null || user.getName().isEmpty()) {
            throw new MyPageException(MyPageStatus.EMPTY_INPUT);
        }
    }

    // db 에서 emil 조회에 실패할 시
    private UserProjection findUserByEmail(String email) {
        return userRepository.findProjectionByEmail(email)
            .orElseThrow(() -> new MyPageException(MyPageStatus.USER_NOT_FOUND));
    }

    // 웹앱 에서 변화된 값 없이 update 요청할 시
    private void checkForChanges(User newUser, UserProjection foundUser) {
        boolean sameName = newUser.getName().equals(foundUser.getName());
        boolean passwordUnchanged = userRepository.existsByEmailAndPassword(newUser.getEmail(),
            newUser.getPassword());

        if (sameName && passwordUnchanged) {
            throw new MyPageException(MyPageStatus.NO_CHANGE);
        }
    }

    // db 에서 Update 쿼리를 수행
    @Transactional
    protected MyPageUpdateResponse updateUserInfo(User user) {
        try {
            userRepository.updateAllByEmail(user.getEmail(), user.getName(), user.getPassword());

            return new MyPageUpdateResponse(true, "Update Successful",
                new MyPageSearchRequest(user.getId(), user.getName(), user.getEmail()));
        } catch (Exception e) {
            throw new MyPageException(MyPageStatus.UPDATE_FAILED);
        }
    }

    public Page<PageUserResponse> getUsersWithPagination(Pageable pageable) {
        return userRepository.getUsersWithPagination(pageable);
    }
}
