package org.programmer.cafe.domain.tosspayment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.basetime.entity.BaseTimeEntity;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.tosspayment.dto.TossPaymentResponse;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TossPayment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tossPaymentKey;

    @Column(nullable = false)
    private String tossOrderId;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    long totalAmount;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentMethod tossPaymentMethod;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentStatus tossPaymentStatus;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    public TossPayment(Order order, TossPaymentResponse paymentResponse) {
        this.tossPaymentKey = paymentResponse.getPaymentKey();
        this.tossOrderId = paymentResponse.getTossOrderId();
        this.order = order;
        this.totalAmount = paymentResponse.getTotalAmount();
        this.tossPaymentMethod = TossPaymentMethod.valueOf(paymentResponse.getPaymentMethod());
        this.tossPaymentStatus = TossPaymentStatus.valueOf(paymentResponse.getPaymentStatus());
        this.requestedAt = paymentResponse.getRequestedAt();
        this.approvedAt = paymentResponse.getApprovedAt();
    }
}