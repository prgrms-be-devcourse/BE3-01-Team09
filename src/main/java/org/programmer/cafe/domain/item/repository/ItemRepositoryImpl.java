package org.programmer.cafe.domain.item.repository;

import static org.programmer.cafe.domain.item.entity.QItem.item;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.sort.ItemSortType;
import org.programmer.cafe.domain.item.entity.Item;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> findAllOrderBy(ItemSortType sortType) {
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(sortType);

        return queryFactory
            .selectFrom(item)
            .orderBy(orderSpecifier)
            .fetch();
    }

    private OrderSpecifier<?> createOrderSpecifier(ItemSortType sortType) {
        return switch (sortType) {
            case HIGHEST_PRICE -> new OrderSpecifier<>(Order.DESC, item.price);
            case LOWEST_PRICE -> new OrderSpecifier<>(Order.ASC, item.price);
            default -> new OrderSpecifier<>(Order.DESC, item.id); // default: 최신순
        };
    }
}
