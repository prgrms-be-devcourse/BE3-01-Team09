package org.programmer.cafe.domain.item.entity.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.item.entity.Item;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toEntity(CreateItemRequest createItemRequest);

    CreateItemResponse toCreateResponseDto(Item item);

    // Item, UpdateItemRequest 두 객체에 id 필드가 동시에 존재.
    // @Mapping source 속성을 사용해 어떤 객체의 필드인지 명확히 지정.
    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "updatedFields", expression = "java(mapUpdatedFields(item, request))")
    UpdateItemResponse toUpdateItemResponse(Item item, UpdateItemRequest request);

    default Map<String, Object> mapUpdatedFields(Item item, UpdateItemRequest request) {
        final Map<String, Object> updatedFields = new HashMap<>();

        if (!Objects.equals(item.getName(), request.getName())) {
            updatedFields.put("name", request.getName());
        }
        if (!Objects.equals(item.getImage(), request.getImage())) {
            updatedFields.put("image", request.getImage());
        }
        if (item.getPrice() != request.getPrice()) {
            updatedFields.put("price", request.getPrice());
        }
        if (item.getStock() != request.getStock()) {
            updatedFields.put("stock", request.getStock());
        }
        if (!Objects.equals(item.getStatus(), request.getStatus())) {
            updatedFields.put("status", request.getStatus());
        }
        item.update(request);

        return updatedFields;
    }
}
