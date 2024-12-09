package org.programmer.cafe.domain.item.entity.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.item.entity.ItemStatus;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateItemResponse {

    private long id;

    private String name;

    private String image;

    private int price;

    private int stock;

    private ItemStatus status;
}
