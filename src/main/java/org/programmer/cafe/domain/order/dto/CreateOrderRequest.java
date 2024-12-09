package org.programmer.cafe.domain.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateOrderRequest {

    private String name; // 받는 분 성함
    private String zipcode; // 우편번호
    private String address; // 받는 분 주소
    private String addressDetail; // 상세 주소
}
