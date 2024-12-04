package org.programmer.cafe.domain.user.entity;

public enum MyPageStatus {
    SUCCESS("로드 성공"),
    INVALID_ID("유효하지 않은 ID입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.");

    private final String message;

    private MyPageStatus(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
