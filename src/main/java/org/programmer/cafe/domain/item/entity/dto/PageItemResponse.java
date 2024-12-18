package org.programmer.cafe.domain.item.entity.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.item.entity.ItemStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class PageItemResponse {

    private Long id;

    private String name;

    private String image;

    private int price;

    private int stock;

    private ItemStatus status;

    private String createdAt;

    private String updatedAt;
}
