package org.programmer.cafe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.dto.GetItemsResponse;
import org.programmer.cafe.domain.item.sort.ItemSortType;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 컨트롤러 클래스<br>
 * 반환값 ResponseEntity<ApiResponse> 통일
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "상품 목록 조회 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 조회 성공")})
    @GetMapping()
    public ResponseEntity<ApiResponse<GetItemsResponse>> getItems(
        @RequestParam(defaultValue = "NEW") ItemSortType sortType) {
        return ResponseEntity.ok()
            .body(ApiResponse.createSuccess(itemService.getItems(sortType)));
    }
}
