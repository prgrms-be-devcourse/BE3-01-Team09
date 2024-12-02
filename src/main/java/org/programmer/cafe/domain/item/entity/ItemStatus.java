package org.programmer.cafe.domain.item.entity;

import lombok.Getter;

@Getter
public enum ItemStatus {

    ON_SALE("판매중"),
    OUT_OF_STOCK("품절"),
    DISCONTINUED("판매중단");

    private final String status;

    ItemStatus(String status) {
        this.status = status;
    }
}
