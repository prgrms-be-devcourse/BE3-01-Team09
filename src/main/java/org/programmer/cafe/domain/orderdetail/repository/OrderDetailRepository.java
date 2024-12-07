package org.programmer.cafe.domain.orderdetail.repository;

import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
