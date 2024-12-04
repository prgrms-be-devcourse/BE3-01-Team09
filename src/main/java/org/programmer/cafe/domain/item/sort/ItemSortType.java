package org.programmer.cafe.domain.item.sort;

import lombok.Getter;

@Getter
public enum ItemSortType {

    NEW("최신 순"),
    HIGHEST_PRICE("높은 가격 순"),
    LOWEST_PRICE("낮은 가격 순");

    private final String description;

    ItemSortType(String description) {
        this.description = description;
    }
}
