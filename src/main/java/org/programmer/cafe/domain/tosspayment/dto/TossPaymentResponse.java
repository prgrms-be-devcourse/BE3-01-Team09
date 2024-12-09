package org.programmer.cafe.domain.tosspayment.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TossPaymentResponse {

    private String tossOrderId;
    private String paymentKey;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private long totalAmount;
}
