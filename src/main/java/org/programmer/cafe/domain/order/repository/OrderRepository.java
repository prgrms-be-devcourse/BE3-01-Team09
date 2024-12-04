package org.programmer.cafe.domain.order.repository;

import java.util.List;
import org.programmer.cafe.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_Id(Long userId);
}
