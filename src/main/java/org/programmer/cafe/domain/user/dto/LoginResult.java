package org.programmer.cafe.domain.user.dto;

import lombok.Getter;
import org.programmer.cafe.domain.user.entity.LoginStatus;
import org.programmer.cafe.domain.user.entity.User;

import java.util.Optional;

@Getter
public class LoginResultDto {
    private LoginStatus loginStatus;
    private Optional<User> user;

    public LoginResultDto(LoginStatus loginStatus, Optional<User> user) {
        this.loginStatus = loginStatus;
        this.user = user;
    }
}
