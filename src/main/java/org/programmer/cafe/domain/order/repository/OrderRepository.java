package org.programmer.cafe.domain.order.repository;

import java.util.Optional;
import java.util.List;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_Id(Long userId);
    List<Order> findAllByStatus(OrderStatus status);
    Optional<Order> findByUserIdAndStatusIs(Long userId, OrderStatus status);
}
