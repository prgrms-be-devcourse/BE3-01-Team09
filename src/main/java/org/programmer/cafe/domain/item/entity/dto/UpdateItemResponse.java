package org.programmer.cafe.domain.item.entity.dto;

import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateItemResponse {

    private long id;

    private Map<String, Object> updatedFields; // 변경된 필드와 값

}
