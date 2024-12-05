package org.programmer.cafe.domain.orderdetail.repository;

import java.util.List;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrder_Id(Long orderId);

}
