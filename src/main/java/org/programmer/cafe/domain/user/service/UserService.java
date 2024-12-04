package org.programmer.cafe.domain.user.service;

import static org.programmer.cafe.exception.ErrorCode.USER_ALREADY_EXIST;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.authority.Role;
import org.programmer.cafe.domain.authority.entity.Authority;
import org.programmer.cafe.domain.user.dto.UserSignupRequest;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.ConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
            throw new ConflictException(USER_ALREADY_EXIST);
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
}
