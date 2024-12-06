package org.programmer.cafe.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {

    @Schema(description = "결제금액", defaultValue = "10000")
    private int totalPrice;
    @Schema(description = "주문현황", defaultValue = "COMPLETED", allowableValues = {"COMPLETED", "CANCEL", "SHIPPING_STARTED"})
    private OrderStatus status;
    @Schema(description = "주문인", defaultValue = "홍길동")
    private String name;
    @Schema(description = "우편번호", defaultValue = "12345")
    private String zipcode;
    @Schema(description = "주소", defaultValue = "서울특별시 강남구 테헤란로 123")
    private String address;
    @Schema(description = "상세 주소", defaultValue = "빌딩 501호")
    private String addressDetail;
    @Schema(description = "아이템 이름", defaultValue = "커피콩")
    private String itemName;
    @Schema(description = "아이템 이미지 URL", defaultValue = "/images/coffee_machine.png")
    private String itemImage;
    @Schema(description = "사용자 ID", defaultValue = "1")
    private Long user;

}
