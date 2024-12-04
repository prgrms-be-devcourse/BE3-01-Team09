package org.programmer.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 상품 컨트롤러 클래스<br>
 * 반환값 ResponseEntity<ApiResponse> 통일
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 전체 목록 호출 메서드<br>
     * Response 테스트용 작성
     */
    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getItems() {
        List<Item> itemList = itemService.getItemList();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(itemList)); // dto만들어서 반환필요
    }
}
