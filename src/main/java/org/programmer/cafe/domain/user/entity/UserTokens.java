package org.programmer.cafe.domain.user.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserTokens {

    private final String accessToken;
    private final String refreshToken;
}
