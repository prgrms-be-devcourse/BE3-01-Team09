package org.programmer.cafe.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ConflictException extends RuntimeException {
    private final ErrorCode errorCode;

}
