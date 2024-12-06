package org.programmer.cafe.domain.user.repository;

import static org.programmer.cafe.domain.deliveryaddress.entity.QDeliveryAddress.deliveryAddress;
import static org.programmer.cafe.domain.user.entity.QUser.user;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.user.entity.dto.PageUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    // DTO 로 반환받기 위해 만듦.
    private static final QBean<PageUserResponse> pageUserDto = Projections.fields(
        PageUserResponse.class,
        user.id,
        user.email,
        user.name,
        deliveryAddress.address,
        Expressions.stringTemplate(
                "DATE_FORMAT({0}, '%Y-%m-%d')", user.createdAt)
            .as("createdAt"),
        Expressions.stringTemplate(
                "DATE_FORMAT({0}, '%Y-%m-%d')", user.updatedAt)
            .as("updatedAt")
    );

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PageUserResponse> getUsersWithPagination(Pageable pageable) {
        final List<PageUserResponse> responses = queryFactory.select(pageUserDto).from(user)
            .leftJoin(deliveryAddress)
            .on(user.id.eq(deliveryAddress.user.id).and(deliveryAddress.defaultYn.eq(true)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(new OrderSpecifier<>(Order.DESC, user.id))
            .fetch();

        final Long count = queryFactory.select(user.count())
            .from(user)
            .fetchOne();
        return new PageImpl<>(responses, pageable, count != null ? count : 0);
    }
}
