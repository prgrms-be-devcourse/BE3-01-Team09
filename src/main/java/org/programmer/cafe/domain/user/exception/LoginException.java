package org.programmer.cafe.domain.user.exception;

import org.programmer.cafe.domain.user.entity.LoginStatus;

public class LoginException extends RuntimeException {
    private final LoginStatus loginStatus;

    public LoginException(LoginStatus loginStatus) {
        super(loginStatus.loginStatus);
        this.loginStatus = loginStatus;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }
}
