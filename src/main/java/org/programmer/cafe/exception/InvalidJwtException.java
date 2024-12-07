package org.programmer.cafe.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class InvalidJwtException extends AuthException {

    public InvalidJwtException(final ErrorCode ErrorCode) {
        super(ErrorCode);
    }
}
