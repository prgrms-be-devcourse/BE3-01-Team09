package org.programmer.cafe.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.programmer.cafe.domain.authority.Role;

import java.util.Set;

/**
 * 사용자 로그인 Response Dto 클래스<br/> Access Token, Refresh Token, 회원정보 반환
 */
@Getter
@Builder
@AllArgsConstructor
public class UserLoginResponse {

    private Long userId;
    private Set<Role> role;
    private String accessToken;
    private String refreshToken;
}
