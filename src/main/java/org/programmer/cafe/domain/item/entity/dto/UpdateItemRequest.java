package org.programmer.cafe.domain.item.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.item.entity.ItemStatus;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateItemRequest {

    @Schema(description = "상품 ID", requiredMode = RequiredMode.REQUIRED, defaultValue = "1")
    @NotNull(message = "상품 ID 값을 입력해주세요.")
    @Positive(message = "상품 ID 값을 확인해주세요.") // id 값은 항상 양수
    private long id;

    @Schema(description = "상품명", requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "커피콩")
    private String name;

    @Schema(description = "이미지 경로", requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "/img/coffee.png")
    private String image;

    @Schema(description = "가격", requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "10000")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    private int price;

    @Schema(description = "재고", requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "15")
    @PositiveOrZero(message = "재고는 0 이상이어야 합니다.")
    private int stock;

    @Schema(description = "상태", requiredMode = RequiredMode.NOT_REQUIRED)
    private ItemStatus status;

    public void setStatusOutOfStock() {
        this.status = ItemStatus.OUT_OF_STOCK;
    }

    public void setStatusOnSale() {
        this.status = ItemStatus.ON_SALE;
    }
}
