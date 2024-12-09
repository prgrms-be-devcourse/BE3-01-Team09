package org.programmer.cafe.domain.item.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import org.programmer.cafe.domain.item.entity.dto.PageItemResponse;
import org.programmer.cafe.domain.item.sort.ItemSortType;
import org.programmer.cafe.domain.item.entity.Item;
import org.springframework.data.domain.Page;

public interface ItemRepositoryCustom {

    List<Item> findAllOrderBy(ItemSortType sortType);

    Page<PageItemResponse> findAllWithPaging(Pageable pageable);
}
