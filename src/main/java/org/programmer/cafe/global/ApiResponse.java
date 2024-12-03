package org.programmer.cafe.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.global.exception.ErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private String status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL) // null일 경우 반환x
    private T data;

    @Builder
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message= message;
        this.data = data;
    }

    // 반환 데이터 없는 성공 response
    public static ApiResponse<Object> createSuccessWithNoData() {
        return ApiResponse.builder()
                .status(ResponseStatus.SUCCESS.getMsg())
                .message(null)
                .data(null)
                .build();
    }

    // 반환 데이터 있는 성공 response
    public static <T> ApiResponse<T> createSuccess(T data) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.SUCCESS.getMsg())
                .message(null)
                .data(data)
                .build();
    }

    // 에러 response
    public static ApiResponse<Object> createError(ErrorCode errorCode) {
        return ApiResponse.builder()
                .status(ResponseStatus.ERROR.getMsg())
                .message(errorCode.getMessage())
                .data(null)
                .build();
    }

    // 에러 response(msg 직접 지정)
    public static ApiResponse<Object> createErrorWithMsg(String msg) {
        return ApiResponse.builder()
                .status(ResponseStatus.ERROR.getMsg())
                .message(msg)
                .data(null)
                .build();
    }
}
