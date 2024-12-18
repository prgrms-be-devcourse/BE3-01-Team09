package org.programmer.cafe.exception;

import lombok.Getter;

/**
 * 오류 HttpStatus 코드 메세지 정의 enum 클래스
 */
@Getter
public enum ErrorCode { // 예외 발생시, body에 실어 날려줄 상태, code, message 커스텀

    //-1000: USER
    USER_ALREADY_EXIST(400, -1001, "해당 이메일이 이미 존재합니다."),
    WRONG_SIGNUP(400, -1002, "올바르지 않은 회원가입입니다."),
    INVALID_PASSWORD(400, -1003, "비빌번호가 올바르지 않습니다."),
    USER_NOT_FOUND(404, -1004, "존재하지 않는 사용자입니다."),
    NONEXISTENT_USER(400, -1009, "존재하지 않는 회원입니다."),

    //-2000: JWT
    EMPTY_JWT_TOKEN(400, -2000, "JWT 토큰이 없습니다."),
    INVALID_ACCESS_TOKEN(400, -2001, "유효하지 않은 토큰입니다."),
    EXPIRED_ACCESS_TOKEN(400, -2002, "어세스 토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(400, -2003, "리프레시 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(400, -2004, "잘못된 리프레시 토큰입니다."),
    INVALID_AUTHORITY_TOKEN(400, -2005, "권한 정보가 없는 토큰입니다."),

    //-3000: COMMON
    NOT_FOUND(404, -3000, "잘못된 경로입니다."),
    BAD_REQUEST(400, -3001, "유효하지 않은 요청입니다."),
    INVALID_REQUEST_BODY(400, -3002, "전달된 데이터가 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(405, -3003,"잘못된 Http Method 입니다."),
    INTERNAL_SERVER_ERROR(500, -3004, "서버 내부 오류입니다."),
    UNAUTHORIZED(401, -3005, "토큰 정보가 만료되었거나 존재하지 않습니다."),
    FORBIDDEN(403, -3006, "접근 권한이 없습니다."),
    INVALID_SORT_TYPE(400, -3007, "올바르지 않은 정렬 타입입니다."),

    //-4000: 배송

    //-4000: 비즈니스 2
    ADDRESS_SAVE_FAILED(500, -4001, "주소 저장 실패"),
    ADDRESS_UPDATE_FAILED(500, -4002, "주소 변경 실패"),
    ADDRESS_NOT_EXIST(400, -4003, "주소 없음"),
    ADDRESS_DELETE_FAILED(400, -4004, "삭제 실패"),

    //-5000: 주문 조회
    ORDER_ALREADY_CANCELED(400, -5000, "이미 취소 된 주문입니다."),
    ORDER_ALREADY_STARTED(400, -5001, "이미 시작 된 배송입니다."),

    //-6000: 상품
    NONEXISTENT_ITEM(400, -6000, "존재하지 않는 상품입니다."),

    //-7000: 장바구니
    NONEXISTENT_CART(400, -7000, "장바구니에 상품이 존재하지 않습니다."),
    COUNT_BELOW_MINIMUM(400, -7001, "담은 수량이 1보다 작을 수 없습니다."),

    //-8000: 결제
    INVALID_PAYMENT_AMOUNT(400, -8000, "잘못된 결제 금액입니다."),
    INVALID_ORDER_FOR_PAYMENT(400, -8001, "결제할 수 없는 상태의 주문입니다."),
    TOSS_PAYMENT_CONFIRM_REQUEST_ERROR(500, -8002, "토스페이먼츠 통신 에러 발생 (결제 승인 요청)"),
    INVALID_PAYMENT_REQUEST_JSON(400, -8003, "결제 승인 컨트롤러에서 Request Body 파싱 에러 발생"),
    INVALID_PAYMENT_RESPONSE_JSON(500, -8004, "결제 승인 응답 값 파싱 에러 발생"),
    TOSS_PAYMENT_CANCEL_REQUEST_ERROR(500, -8005, "토스페이먼츠 통신 에러 발생 (결제 취소 요청)"),
    NONEXISTENT_PENDING_PAYMENT_ORDER(400, -8006, "결제 대기 중인 주문이 존재하지 않습니다."),

    //-9000: 주문
    INSUFFICIENT_STOCK(400, -9000, "재고가 충분하지 않습니다."),
    FAILED_TO_ACQUIRE_LOCK(500, -9001, "사용자가 많아, 주문에 실패했습니다. 다시 시도해주세요."),
    ITEM_NOT_FOR_SALE(400, -9002, "판매 중인 상품이 아닙니다.");


    // 1. status = 날려줄 상태코드
    // 2. code = 해당 오류가 어느부분과 관련있는지 카테고리화 해주는 코드. 예외 원인 식별하기 편하기에 추가
    // 3. message = 발생한 예외에 대한 설명.
    private final int status;
    private final int code;
    private final String message;

    ErrorCode(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}