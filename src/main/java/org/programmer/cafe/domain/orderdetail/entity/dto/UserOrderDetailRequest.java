package org.programmer.cafe.domain.orderdetail.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class UserOrderDetailRequest {
    @Schema(description = "주문 상세 번호", defaultValue = "1")
    private Long id;
    @Schema(description = "주문 수량", defaultValue = "1")
    private int count;
    @Schema(description = "총액", defaultValue = "3000")
    private int totalPrice;
    @Schema(description = "주문 아이템", defaultValue = "1")
    private Long itemId;
    @Schema(description = "아이템 이름", defaultValue = "Item A")
    private String itemName;
    @Schema(description = "아이템 사진", defaultValue = "item1.jpg")
    private String itemImage;
    @Schema(description = "주소", defaultValue = "456 Elm St")
    private String address;
    @Schema(description = "상세주소", defaultValue = "Apt 202")
    private String address_detail;
    @Schema(description = "우편번호", defaultValue = "23456")
    private String zipcode;
    @Schema(description = "주문 번호", defaultValue = "7")
    private Long orderId;
}
