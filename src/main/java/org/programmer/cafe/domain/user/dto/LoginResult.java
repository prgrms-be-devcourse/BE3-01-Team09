package org.programmer.cafe.domain.user.dto;

import lombok.Getter;
import org.programmer.cafe.domain.user.entity.LoginStatus;
import org.programmer.cafe.domain.user.entity.User;

import java.util.Optional;

@Getter
public class LoginResult {
    private LoginStatus loginStatus;
    private User user;

    public LoginResult(LoginStatus loginStatus, User user) {
        this.loginStatus = loginStatus;
        this.user = user;
    }
}
