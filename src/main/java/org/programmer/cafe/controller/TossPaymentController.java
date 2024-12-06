package org.programmer.cafe.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.tosspayment.dto.CreateTempPaymentAmountRequest;
import org.programmer.cafe.domain.tosspayment.dto.VerifyPaymentAmountRequest;
import org.programmer.cafe.domain.tosspayment.service.TossPaymentService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments/toss")
public class TossPaymentController {

    private final TossPaymentService tossPaymentService;

    /**
     * 결제를 요청하기 전에 orderId와 amount를 세션에 저장하는 컨트롤러 (결제 요청과 승인 사이에 데이터 무결성을 확인하기 위함)
     */
    @PostMapping("/amounts/sessions")
    public ResponseEntity<?> createTempPaymentAmount(HttpSession session,
        @RequestBody CreateTempPaymentAmountRequest createTempPaymentAmountRequest) {
        tossPaymentService.createTempPaymentAmount(session, createTempPaymentAmountRequest);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoData());
    }

    @PostMapping("/amounts/verify")
    public ResponseEntity<?> verifyPaymentAmount(HttpSession session,
        @RequestBody VerifyPaymentAmountRequest verifyPaymentAmountRequest) {
        tossPaymentService.verifyPaymentAmountAndRemoveSessionData(session,
            verifyPaymentAmountRequest);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoData());
    }
}
