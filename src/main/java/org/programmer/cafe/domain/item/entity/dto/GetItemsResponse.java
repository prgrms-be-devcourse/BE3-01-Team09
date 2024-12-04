package org.programmer.cafe.domain.item.entity.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.ItemStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetItemsResponse {

    private List<ItemInfo> itemInfos;

    public GetItemsResponse(List<Item> items) {
        this.itemInfos = items.stream()
            .map(ItemInfo::new)
            .toList();
    }

    @Getter
    static class ItemInfo {
        private Long id;
        private String name;
        private String image;
        private int price;
        private int stock;
        private ItemStatus status;

        public ItemInfo(Item item) {
            this.id = item.getId();
            this.name = item.getName();
            this.image = item.getImage();
            this.price = item.getPrice();
            this.stock = item.getStock();
            this.status = item.getStatus();
        }
    }
}
