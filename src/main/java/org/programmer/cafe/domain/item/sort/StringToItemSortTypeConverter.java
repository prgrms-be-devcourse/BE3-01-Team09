package org.programmer.cafe.domain.item.sort;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToItemSortTypeConverter implements Converter<String, ItemSortType> {

    @Override
    public ItemSortType convert(String source) {
        try {
            return ItemSortType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            // 잘못된 값이 들어오면, default 값 들어가도록 null 리턴
            return null;
        }
    }
}
