package org.programmer.cafe.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.user.entity.User;

@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    // 상태 수정
    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
    // 가격 수정
    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Builder
    public Order(int totalPrice, String name, String zipcode,
        String address, String addressDetail, String itemName, String itemImage, User user) {
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PENDING_PAYMENT;
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.user = user;
    }
}
