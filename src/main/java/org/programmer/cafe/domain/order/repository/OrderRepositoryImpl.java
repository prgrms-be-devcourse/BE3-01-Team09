package org.programmer.cafe.domain.order.repository;

import static org.programmer.cafe.domain.order.entity.QOrder.order;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.order.dto.UserOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private static final QBean<UserOrderResponse> orderDto = Projections.fields(
        UserOrderResponse.class,
        order.id,
        order.totalPrice,
        order.status,
        order.name,
        order.itemName,
        order.itemImage,
        order.address,
        order.addressDetail,
        Expressions.stringTemplate(
            "DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt
        ).as("createdAt"),
        Expressions.stringTemplate(
            "DATE_FORMAT({0}, '%Y-%m-%d')", order.updatedAt
        ).as("updatedAt")
    );

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserOrderResponse> getOrdersWithPagination(Pageable pageable) {
        final List<UserOrderResponse> responses = queryFactory.select(orderDto)
            .from(order)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(new OrderSpecifier<>(Order.DESC, order.id))
            .fetch();

        final Long count = queryFactory.select(order.count())
            .from(order)
            .fetchOne();

        return new PageImpl<>(responses, pageable, count != null ? count : 0);
    }
}
