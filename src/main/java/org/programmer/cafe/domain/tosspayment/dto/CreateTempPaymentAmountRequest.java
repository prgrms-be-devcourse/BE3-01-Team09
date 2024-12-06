package org.programmer.cafe.domain.tosspayment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateTempPaymentAmountRequest {

    @Schema(description = "토스 주문 ID", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "토스 주문 ID를 입력해주세요.")
    private String orderId;

    @Schema(description = "결제 금액", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "결제 금액을 입력해주세요.")
    private String amount;
}
