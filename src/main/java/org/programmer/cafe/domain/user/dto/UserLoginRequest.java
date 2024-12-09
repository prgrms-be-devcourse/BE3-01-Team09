package org.programmer.cafe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자 로그인 Request Dto 클래스
 */
@Getter
@Builder
@AllArgsConstructor
public class UserLoginRequest {

    @Schema(description = "이메일", example = "abc@gmail.com")
    @NotBlank(message = "null 또는 공백이 입력되었습니다.")
    @Email(message = "email 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank(message = "null 또는 공백이 입력되었습니다.")
    private String password;
}
