package org.programmer.cafe.domain.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageUpdateResponse {
    private final boolean success;
    private final String message;
    private final MyPageSearchRequest updatedUserInfo;
}
