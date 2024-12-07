package org.programmer.cafe.domain.tosspayment.service;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.programmer.cafe.domain.cart.entity.Cart;
import org.programmer.cafe.domain.cart.entity.CartStatus;
import org.programmer.cafe.domain.cart.repository.CartRepository;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.order.service.OrderService;
import org.programmer.cafe.domain.tosspayment.dto.CreateTempPaymentAmountRequest;
import org.programmer.cafe.domain.tosspayment.dto.TossPaymentMapper;
import org.programmer.cafe.domain.tosspayment.dto.TossPaymentResponse;
import org.programmer.cafe.domain.tosspayment.dto.UserOrderInfo;
import org.programmer.cafe.domain.tosspayment.dto.VerifyPaymentAmountRequest;
import org.programmer.cafe.domain.tosspayment.entity.TossPayment;
import org.programmer.cafe.domain.tosspayment.repository.TossPaymentRepository;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.programmer.cafe.exception.TossPaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentService {

    @Value("${toss-payment.secret-key}")
    private String widgetSecretKey;

    private final OrderService orderService;
    private final TossPaymentRepository tossPaymentRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    /**
     * 결제 전 금액 세션에 저장
     */
    public void createTempPaymentAmount(HttpSession session,
        CreateTempPaymentAmountRequest createTempPaymentAmountRequest) {
        session.setAttribute(createTempPaymentAmountRequest.getOrderId(),
            createTempPaymentAmountRequest.getAmount());
    }

    /**
     * 결제 전의 금액과 결제 후의 금액이 같은지 검증하고, 세션 데이터 삭제
     */
    public void verifyPaymentAmountAndRemoveSessionData(HttpSession session,
        VerifyPaymentAmountRequest verifyPaymentAmountRequest) {
        String amount = (String) session.getAttribute(verifyPaymentAmountRequest.getOrderId());
        verifyPaymentAmount(amount, verifyPaymentAmountRequest.getAmount());

        // 검증에 사용했던 세션은 삭제
        session.removeAttribute(verifyPaymentAmountRequest.getOrderId());
    }

    private void verifyPaymentAmount(String expectedAmount, String actualAmount) {
        if (Objects.isNull(expectedAmount) || !expectedAmount.equals(actualAmount)) {
            throw new BadRequestException(ErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }

    @Transactional
    public ResponseEntity<?> confirmPayment(String jsonBody) {
        // TODO: SecurityContextHolder에서 인증된 유저의 userId를 가져와야 함.
        Long userId = 1L;
        Order order = null;

        try {
            // 클라이언트 요청 파싱 및 토스 결제 요청 승인
            JSONObject confirmPaymentResponse = requestConfirmPayment(jsonBody);

            // 현재 이러한 조회를 통한 방식은 불완전한 결제 방식임.
            // orderId를 파라미터로 전달하는 것이 더 안전하다면 그 방식을 택해야 하고,
            // 그렇지 않다면, 유저가 결제 대기 상태의 주문을 하나만 가질 수 있도록 보장해야 함.
            order = getPendingOrder(userId);

            // 결제 성공 처리
            TossPaymentResponse paymentResponse = parseTossPaymentResponse(confirmPaymentResponse);
            createPaymentAndUpdateOrderStatus(order, paymentResponse);
            deleteCartAfterPayment(userId);
            return ResponseEntity.ok(confirmPaymentResponse);
        } catch (TossPaymentException e) {
            log.error("결제 요청이나 파싱 중 오류 발생");
            if (Objects.nonNull(order)) {
                rollbackPendingPaymentCarts(userId);
                updateOrderStatusToFailedPayment(order);
            }
            return buildErrorResponse(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("결제 성공 후 비즈니스 로직 오류 발생. 결제 취소.");
            if (Objects.nonNull(order)) {
                rollbackPendingPaymentCarts(userId);
                updateOrderStatusToFailedPayment(order);
            }
            return handlePaymentCancellation(jsonBody);
        }
    }

    private void deleteCartAfterPayment(Long userId) {
        cartRepository.deleteAllInBatchByUserIdAndStatus(userId, CartStatus.PENDING_PAYMENT);
    }

    private void rollbackPendingPaymentCarts(Long userId) {
        List<Cart> carts = cartRepository.findAllByUserIdAndStatus(userId,
            CartStatus.PENDING_PAYMENT);

        List<Item> items = carts.stream()
            .peek(cart -> cart.getItem().increaseStock(cart.getCount()))
            .map(Cart::getItem)
            .toList();

        // TODO: 배치 처리 필요
        itemRepository.saveAll(items);

        List<Cart> updateCarts = carts.stream()
            .peek(cart -> cart.updateStatus(CartStatus.BEFORE_ORDER))
            .toList();

        // TODO: 배치 처리 필요
        cartRepository.saveAll(updateCarts);
    }

    private void updateOrderStatusToFailedPayment(Order order) {
        order.updateStatus(OrderStatus.FAILED_PAYMENT);
        orderRepository.save(order);
    }

    private ResponseEntity<?> handlePaymentCancellation(String jsonBody) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject requestObj = parsePaymentRequest(parser, jsonBody);
            String paymentKey = (String) requestObj.get("paymentKey");

            requestPaymentCancel(paymentKey, "결제 성공 처리 중 오류 발생");

            JSONObject errorResponse = buildErrorResponseJson(
                ErrorCode.INTERNAL_SERVER_ERROR,
                "서버 내부 오류로 결제가 취소되었습니다."
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception e) {
            throw new TossPaymentException(ErrorCode.TOSS_PAYMENT_CANCEL_REQUEST_ERROR);
        }
    }

    private JSONObject buildErrorResponseJson(ErrorCode errorCode, String message) {
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("code", errorCode.getStatus());
        errorResponse.put("message", message);
        return errorResponse;
    }

    private ResponseEntity<JSONObject> buildErrorResponse(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getStatus())
            .body(buildErrorResponseJson(errorCode, message));
    }

    private Order getPendingOrder(Long userId) {
        return orderService.getPendingPaymentOrderByUserId(userId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_ORDER_FOR_PAYMENT));
    }

    private TossPaymentResponse parseTossPaymentResponse(JSONObject responseObj) {
        System.out.println("responseObj.toJSONString() = " + responseObj.toJSONString());

        return new TossPaymentResponse(
            (String) responseObj.get("orderId"),
            (String) responseObj.get("paymentKey"),
            (String) responseObj.get("method"),
            (String) responseObj.get("status"),
            parseLocalDateTime((String) responseObj.get("requestedAt")),
            parseLocalDateTime((String) responseObj.get("approvedAt")),
            (long) responseObj.get("totalAmount")
        );
    }

    private LocalDateTime parseLocalDateTime(String dateTimeString) {
        // JSON의 ISO-8601 형식을 LocalDateTime으로 변환
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private JSONObject requestConfirmPayment(String jsonBody) {
        try {
            JSONParser parser = new JSONParser();
            // 클라이언트 요청 파싱
            JSONObject requestObj = parsePaymentRequest(parser, jsonBody);
            // 토스에게 결제 승인 요청할 때 필요한 authorizations
            String authorizations = getAuthorizationHeader();
            // 토스에게 결제 승인 요청
            HttpURLConnection connection = requestConfirm(authorizations, requestObj);
            int responseCode = connection.getResponseCode();
            log.info("responseCode: " + responseCode);

            InputStream responseStream = responseCode == 200
                ? connection.getInputStream()
                : connection.getErrorStream();

            // 결제 응답 파싱
            Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
            JSONObject responseObj = (JSONObject) parser.parse(reader);
            responseStream.close();

            if (responseCode != 200) {
                throw new TossPaymentException(ErrorCode.TOSS_PAYMENT_CONFIRM_REQUEST_ERROR);
            }

            return responseObj;

        } catch (IOException e) {
            throw new TossPaymentException(ErrorCode.TOSS_PAYMENT_CONFIRM_REQUEST_ERROR);
        } catch (ParseException e) {
            throw new TossPaymentException(ErrorCode.INVALID_PAYMENT_RESPONSE_JSON);
        }
    }

    private void createPaymentAndUpdateOrderStatus(Order order,
        TossPaymentResponse paymentResponse) {
        try {
            tossPaymentRepository.save(new TossPayment(order, paymentResponse));
            orderService.updateOrderStatus(order, OrderStatus.COMPLETED);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
    // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
    // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
    private String getAuthorizationHeader() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(
            (widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }

    // 결제 승인 API를 호출하세요.
    // 결제를 승인하면 결제수단에서 금액이 차감돼요.
    // @docs https://docs.tosspayments.com/guides/v2/payment-widget/integration#3-결제-승인하기
    private static HttpURLConnection requestConfirm(String authorizations, JSONObject obj)
        throws IOException {
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        return connection;
    }

    private JSONObject parsePaymentRequest(JSONParser parser, String jsonBody) {
        String orderId = null;
        String amount = null;
        String paymentKey = null;

        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new BadRequestException(ErrorCode.INVALID_PAYMENT_REQUEST_JSON);
        }

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        return obj;
    }

    private void requestPaymentCancel(String paymentKey, String cancelReason)
        throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel"))
            .header("Authorization", getAuthorizationHeader())
            .header("Content-Type", "application/json")
            .method("POST",
                HttpRequest.BodyPublishers.ofString("{\"cancelReason\":\"" + cancelReason + "\"}"))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, BodyHandlers.ofString());

        log.info("결제 취소 paymentKey: {}, response: {}", paymentKey, response.body());
    }

    public UserOrderInfo getUserOrderInfo(Long userId) {
        final User user = userRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_USER));

        final Order order = orderRepository.findByUserIdAndStatusIs(userId,
                OrderStatus.PENDING_PAYMENT)
            .orElseThrow(
                () -> new BadRequestException(ErrorCode.NONEXISTENT_PENDING_PAYMENT_ORDER));

        return TossPaymentMapper.INSTANCE.toUserOrderInfo(user, order);
    }
}