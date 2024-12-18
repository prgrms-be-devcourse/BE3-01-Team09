package org.programmer.cafe.domain.authority;

import org.springframework.security.core.GrantedAuthority;

/**
 * 사용자 권한 enum 클래스
 */
public enum Role implements GrantedAuthority {
    ROLE_MEMBER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
