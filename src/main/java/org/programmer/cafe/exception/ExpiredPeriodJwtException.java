package org.programmer.cafe.exception;

import lombok.Getter;

@Getter
public class ExpiredPeriodJwtException extends AuthException {

    public ExpiredPeriodJwtException(final ErrorCode ErrorCode) {
        super(ErrorCode);
    }
}
