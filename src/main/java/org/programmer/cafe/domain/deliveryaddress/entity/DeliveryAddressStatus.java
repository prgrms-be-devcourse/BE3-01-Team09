package org.programmer.cafe.domain.deliveryaddress.entity;

public enum DeliveryAddressStatus {
    SUCCESS("성공"),
    LOAD_FAILED("로드 실패");


    private final String message;

    private DeliveryAddressStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
