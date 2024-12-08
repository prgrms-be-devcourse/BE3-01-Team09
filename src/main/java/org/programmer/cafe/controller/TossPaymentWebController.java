package org.programmer.cafe.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.tosspayment.dto.UserOrderInfo;
import org.programmer.cafe.domain.tosspayment.service.TossPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/payments/toss")
@RequiredArgsConstructor
public class TossPaymentWebController {

    private final TossPaymentService tossPaymentService;

    @PostMapping(value = "/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody String jsonBody) {
        return tossPaymentService.confirmPayment(jsonBody);
    }

    /**
     * 인증성공처리
     */
    @GetMapping("/success")
    public String paymentRequest(Model model) {
        return "/payment/success";
    }

    @GetMapping()
    public String index(Model model) {
        // TODO: SecurityContextHolder에서 인증된 유저의 userId를 가져와야 함.
        Long userId = 1L;
        UserOrderInfo userOrderInfo = tossPaymentService.getUserOrderInfo(userId);
        model.addAttribute("userOrderInfo", userOrderInfo);
        return "/payment/checkout";
    }

    /**
     * 인증실패처리
     */
    @GetMapping("/fail")
    public String failPayment(HttpServletRequest request, Model model) {
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "/payment/fail";
    }
}
