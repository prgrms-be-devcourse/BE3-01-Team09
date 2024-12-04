package org.programmer.cafe.domain.item.entity.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.ItemStatus;

class ItemMapperTest {

    @Test
    void testDtoToEntity() {
        final CreateItemRequest createDto = CreateItemRequest.builder().name("커피콩").price(1000)
            .image("/img/asdsad.png").stock(15).status(ItemStatus.ON_SALE).build();

        final Item entity = ItemMapper.INSTANCE.toEntity(createDto);

        assertEquals(createDto.getName(), entity.getName());
        assertEquals(createDto.getPrice(), entity.getPrice());
        assertEquals(createDto.getImage(), entity.getImage());
        assertEquals(createDto.getStock(), entity.getStock());
        assertEquals(createDto.getStatus(), entity.getStatus());
    }

    @Test
    void testEntityToDto() {
        final Item entity = Item.builder().name("커피콩").price(1000).image("/img/asdsad.png")
            .stock(15).status(ItemStatus.ON_SALE).build();

        final CreateItemResponse createDto = ItemMapper.INSTANCE.toCreateResponseDto(entity);
        assertEquals(0, createDto.getId());
        assertEquals(createDto.getName(), entity.getName());
        assertEquals(createDto.getPrice(), entity.getPrice());
        assertEquals(createDto.getImage(), entity.getImage());
        assertEquals(createDto.getStock(), entity.getStock());

    }

    @Test
    void testUpdateFields() {
        final Item entity = Item.builder().name("커피콩").price(1000).image("/img/asdsad.png")
            .stock(15).status(ItemStatus.ON_SALE).build();

        final Map<String, Object> map = new HashMap<>();
        map.put("price", 100);
        map.put("stock", 20);

        final UpdateItemRequest request = UpdateItemRequest.builder().image("/img/asdsad.png")
            .status(ItemStatus.ON_SALE).stock(20).price(100).build();

        final UpdateItemResponse updateItemResponse = ItemMapper.INSTANCE.toUpdateItemResponse(
            entity, request);
        assertEquals(0, updateItemResponse.getId());
        assertEquals(map, updateItemResponse.getUpdatedFields());
    }
}