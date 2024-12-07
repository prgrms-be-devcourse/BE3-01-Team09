package org.programmer.cafe.email;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.entity.QOrder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 주문 완료 찾기
    public List<Order> findOrdersByStatus(OrderStatus status) {
        QOrder order = QOrder.order;
        return queryFactory.selectFrom(order).where(order.status.eq(status)).fetch();
    }

    // 주문 상태 변경
    public long updateOrderStatus(OrderStatus status) {
        QOrder order = QOrder.order;
        return queryFactory.update(order)
            .set(order.status, status)
            .where(order.status.eq(OrderStatus.COMPLETED)).execute();
    }

}
