package org.programmer.cafe.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.user.entity.User;

@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 5)
    private String zipcode;

    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String itemName; // 대표 상품명

    @Column(nullable = false)
    private String itemImage; // 대표 상품 이미지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 상태 수정
    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
    // 가격 수정
    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
