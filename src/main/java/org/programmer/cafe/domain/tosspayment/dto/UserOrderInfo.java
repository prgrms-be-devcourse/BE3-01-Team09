package org.programmer.cafe.domain.tosspayment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOrderInfo {

    private int amountValue;
    private String orderName;
    private String customerEmail;
    private String customerName;
    private String customerMobilePhone;

    @Builder
    public UserOrderInfo(int amountValue, String orderName, String customerEmail,
        String customerName, String customerMobilePhone) {
        this.amountValue = amountValue;
        this.orderName = orderName;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerMobilePhone = customerMobilePhone;
    }
}
