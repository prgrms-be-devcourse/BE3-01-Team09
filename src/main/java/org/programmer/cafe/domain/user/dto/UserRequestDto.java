package org.programmer.cafe.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String name;
    private String email;
    private String password;

    @Builder
    public UserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
