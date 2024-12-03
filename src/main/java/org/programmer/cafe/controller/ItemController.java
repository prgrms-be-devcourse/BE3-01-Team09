package org.programmer.cafe.controller;

import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.global.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getItems() {
        List<Item> itemList = itemService.getItemList();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(itemList)); // dto만들어서 반환필요
    }
}
