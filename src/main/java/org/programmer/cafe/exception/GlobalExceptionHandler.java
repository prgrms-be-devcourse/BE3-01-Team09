package org.programmer.cafe.exception;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 공통 예외처리 클래스
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // validation 체크
    private String bindingResultErrorsCheck(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fe : bindingResult.getFieldErrors()) {
            errorMap.put(fe.getField(), fe.getDefaultMessage());
        }
        return errorMap.toString();
    }

    // 잘못된 경로 에러 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("[NoHandlerFoundException] message: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.NOT_FOUND;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.createError(errorCode.getMessage()));
    }

    // Http Method 에러
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[HttpRequestMethodNotSupportedException] message: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.createError(errorCode.getMessage()));
    }

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] message: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.createErrorWithMsg(e.getMessage()));
    }

    // Jwt 400 에러
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(AuthException e) {
        log.error("[AuthException] message: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.createError(e.getErrorCode().getMessage()));
    }

    // 각종 400 에러
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException e) {
        log.error("[BadRequestException] message: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.createError(e.getErrorCode().getMessage()));
    }

    // 각종 404 에러
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException e) {
        log.error("[NotFoundException] message: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.createError(e.getErrorCode().getMessage()));
    }

    // 유효성 검사 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(
        MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] message: {}", e.getMessage());
        final String errorMessage = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(errorMessage));
    }

    // Entity 조회 실패 시 에러
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("[EntityNotFoundException] message: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(e.getMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<Object>> handleIOException(IOException e) {
        log.error("[IOException] message: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.createError(e.getMessage()));
    }

    // 위의 경우를 제외한 모든 에러 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        log.error("[Exception] message: {},{}", e.getMessage(), e.getClass(), e.getCause());
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.createError(errorCode.getMessage()));
    }

    // 토스 페이먼츠 API 서버와의 통신 에러
    @ExceptionHandler(TossPaymentException.class)
    public ResponseEntity<ApiResponse<Object>> handleTossPaymentException(TossPaymentException e) {
        log.error("[TossPaymentException] message: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ApiResponse.createError(e.getErrorCode().getMessage()));
    }
}
