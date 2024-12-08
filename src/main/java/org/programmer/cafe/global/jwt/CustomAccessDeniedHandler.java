package org.programmer.cafe.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.exception.ErrorCode;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Custom Access Denied 예외처리 Handler
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("[CustomAccessDeniedHandler] :: {}", accessDeniedException.getMessage());
        log.info("[CustomAccessDeniedHandler] :: {}", request.getRequestURL());
        log.info("[CustomAccessDeniedHandler] :: 접근 권한이 없습니다.");

        response.setStatus(ErrorCode.FORBIDDEN.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        // response body 담기
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(
            ApiResponse.createErrorWithMsg(ErrorCode.FORBIDDEN.getMessage()));
        response.getWriter().write(jsonResponse);
    }
}