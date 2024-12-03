package org.programmer.cafe.global.constant;

import lombok.Getter;

/**
 * 데이터 Response status 속성 구성 enum 클래스
 */
@Getter
public enum ResponseStatus {
    SUCCESS("성공"),
    ERROR("오류");

    private final String msg;

    ResponseStatus(String msg) {
        this.msg= msg;
    }
}