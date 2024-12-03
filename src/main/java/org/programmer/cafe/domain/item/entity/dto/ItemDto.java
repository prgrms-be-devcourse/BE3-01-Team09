package org.programmer.cafe.domain.item.entity.dto;


import lombok.*;
import org.programmer.cafe.domain.item.entity.ItemStatus;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemDto {

    private String name;
    private String image;
    private int price;
    private int stock;
    private ItemStatus status;
}
