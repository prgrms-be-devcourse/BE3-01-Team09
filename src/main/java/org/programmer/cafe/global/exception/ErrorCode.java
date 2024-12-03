package org.programmer.cafe.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode { // 예외 발생시, body에 실어 날려줄 상태, code, message 커스텀

    //-1000: USER
    USER_ALREADY_EXIST(400, -1006, "해당 아이디가 이미 존재합니다."),
    EMPTY_PROFILE_IMAGE(400, -1007, "프로필 사진이 존재하지 않습니다."),
    WRONG_SIGNUP(400, -1008, "올바르지 않은 회원가입입니다."),

    //-2000: JWT
    EMPTY_JWT_TOKEN(400, -2000, "JWT 토큰이 없습니다."),
    INVALID_TOKEN(400, -2001, "유효하지 않은 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(400, -2002, "인증 정보가 만료되었습니다."),
    INVALID_REFRESH_TOKEN(400, -2003, "잘못된 리프레시 토큰입니다."),

    NOT_FOUND(404, -3000, "잘못된 경로");

    //-3000: 비즈니스 1

    //-4000: 비즈니스 2

    //-5000: 비즈니스 3

    // 1. status = 날려줄 상태코드
    // 2. code = 해당 오류가 어느부분과 관련있는지 카테고리화 해주는 코드. 예외 원인 식별하기 편하기에 추가
    // 3. message = 발생한 예외에 대한 설명.

    private final int status;
    private final int code;
    private final String message;

    ErrorCode(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}