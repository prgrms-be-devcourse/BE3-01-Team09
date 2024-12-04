package org.programmer.cafe.domain.user.entity;

public enum LoginStatus {
    SUCCESS("로그인 성공"),
    EMPTY_PASSWORD("비밀번호 미입력"),
    INVALID_PASSWORD("비밀번호 불일치");

    public final String loginStatus;
    LoginStatus(String loginStatus) { this.loginStatus = loginStatus; }
}
