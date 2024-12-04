package org.programmer.cafe.domain.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateCartItemRequest {

    @Schema(description = "상품 ID", requiredMode = RequiredMode.REQUIRED, defaultValue = "1L")
    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long itemId;

}
