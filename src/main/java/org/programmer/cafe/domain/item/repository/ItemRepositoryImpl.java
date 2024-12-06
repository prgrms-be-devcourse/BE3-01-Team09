package org.programmer.cafe.domain.item.repository;

import static org.programmer.cafe.domain.item.entity.QItem.item;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.PageItemResponse;
import org.programmer.cafe.domain.item.sort.ItemSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    // DTO 로 반환받기 위해 만듦.
    private static final QBean<PageItemResponse> pageItemDto = Projections.fields(
        PageItemResponse.class,
        item.id,
        item.price,
        item.stock,
        item.status,
        item.image,
        item.name,
        Expressions.stringTemplate(
            "DATE_FORMAT({0}, '%Y-%m-%d')", item.createdAt
        ).as("createdAt"),
        Expressions.stringTemplate(
            "DATE_FORMAT({0}, '%Y-%m-%d')", item.updatedAt
        ).as("updatedAt")
    );

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> findAllOrderBy(ItemSortType sortType) {
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(sortType);

        return queryFactory
            .selectFrom(item)
            .orderBy(orderSpecifier)
            .fetch();
    }

    @Override
    public Page<PageItemResponse> findAllWithPaging(Pageable pageable) {
        final List<PageItemResponse> responses =
            queryFactory.select(pageItemDto)
                .from(item)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(ItemSortType.NEW)) // 항상 최신순
                .fetch();
        final Long count = queryFactory.select(item.count())
            .from(item)
            .fetchOne();
        return new PageImpl<>(responses, pageable, count != null ? count : 0);
    }

    private OrderSpecifier<?> createOrderSpecifier(ItemSortType sortType) {
        return switch (sortType) {
            case HIGHEST_PRICE -> new OrderSpecifier<>(Order.DESC, item.price);
            case LOWEST_PRICE -> new OrderSpecifier<>(Order.ASC, item.price);
            default -> new OrderSpecifier<>(Order.DESC, item.id); // default: 최신순
        };
    }
}
