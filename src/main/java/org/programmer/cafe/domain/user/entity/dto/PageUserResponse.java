package org.programmer.cafe.domain.user.entity.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class PageUserResponse {

    private Long id;

    private String email;

    private String name;

    private String address;

    private String createdAt;

    private String updatedAt;
}
