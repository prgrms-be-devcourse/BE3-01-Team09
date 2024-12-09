package org.programmer.cafe.domain.user.entity;

public enum MyPageStatus {
    SUCCESS("성공"),
    INVALID_ID("유효하지 않은 ID입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    EMPTY_INPUT("입력값이 없습니다."),
    NO_CHANGE("변경사항이 없습니다."),
    UPDATE_FAILED("내 정보 수정 실패");

    private final String message;

    private MyPageStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
