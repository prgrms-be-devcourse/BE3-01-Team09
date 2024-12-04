package org.programmer.cafe.domain.item.repository;

import java.util.List;
import org.programmer.cafe.domain.item.sort.ItemSortType;
import org.programmer.cafe.domain.item.entity.Item;

public interface ItemRepositoryCustom {

    List<Item> findAllOrderBy(ItemSortType sortType);
}
