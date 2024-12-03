package org.programmer.cafe.domain.item.entity.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.programmer.cafe.domain.item.entity.Item;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toEntity(ItemDto itemDto);

    ItemDto toDto(Item item);
}
