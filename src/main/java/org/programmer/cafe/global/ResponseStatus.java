package org.programmer.cafe.global;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS("성공"),
    ERROR("오류");

    private final String msg;

    ResponseStatus(String msg) {
        this.msg= msg;
    }

//    public String getMsg() {
//        return msg;
//    }
}