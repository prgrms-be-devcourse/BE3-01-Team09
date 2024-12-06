package org.programmer.cafe.domain.order.repository;

import java.util.Optional;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndStatusIs(Long userId, OrderStatus status);
}
