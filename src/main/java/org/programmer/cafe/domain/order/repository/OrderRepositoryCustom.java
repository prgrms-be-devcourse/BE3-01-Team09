package org.programmer.cafe.domain.order.repository;

import org.programmer.cafe.domain.order.dto.UserOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<UserOrderResponse> getOrdersWithPagination(Pageable pageable);
}
