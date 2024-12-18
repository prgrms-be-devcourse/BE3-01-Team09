package org.programmer.cafe.domain.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;

@Entity(name = "items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Builder
    public Item(String name, String image, int price, int stock, ItemStatus status) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public void update(UpdateItemRequest updateItemRequest) {
        if (updateItemRequest.getName() != null) {
            this.name = updateItemRequest.getName();
        }
        if (updateItemRequest.getImage() != null) {
            this.image = updateItemRequest.getImage();
        }
        if (updateItemRequest.getPrice() >= 0) {
            this.price = updateItemRequest.getPrice();
        }
        if (updateItemRequest.getStock() >= 0) {
            this.stock = updateItemRequest.getStock();
        }
        if (updateItemRequest.getStatus() != null) {
            this.status = updateItemRequest.getStatus();
        }
    }

    public void decreaseStock(int orderCount) {
        this.stock -= orderCount;

        if (stock == 0) {
            this.status = ItemStatus.OUT_OF_STOCK;
        }
    }

    public void increaseStock(int orderCount) {
        if (stock == 0) {
            this.status = ItemStatus.ON_SALE;
        }

        this.stock += orderCount;
    }
}
