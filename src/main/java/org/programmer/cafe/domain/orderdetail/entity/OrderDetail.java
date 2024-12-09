package org.programmer.cafe.domain.orderdetail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;

@Entity(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 수량 수정
    public void updateCount(int count) {
        this.count = count;
    }
    // 총액 수정
    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Builder
    public OrderDetail(int count, int totalPrice, Item item, Order order) {
        this.count = count;
        this.totalPrice = totalPrice;
        this.item = item;
        this.order = order;
    }
}
