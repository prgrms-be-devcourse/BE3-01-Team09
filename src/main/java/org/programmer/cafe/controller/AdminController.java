package org.programmer.cafe.controller;

import static org.programmer.cafe.global.response.ApiResponse.createSuccess;
import static org.programmer.cafe.global.response.ApiResponse.createSuccessWithNoData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.dto.CreateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.CreateItemResponse;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admins")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 페이지")
public class AdminController {

    private final ItemService itemService;

    @PostMapping("/items")
    @Operation(summary = "관리자 상품 등록 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "상품 등록 성공")})
    public ResponseEntity<ApiResponse<CreateItemResponse>> createItem(
        @Validated @RequestBody CreateItemRequest createItemRequest) {
        final CreateItemResponse created = itemService.createItem(createItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccess(created));
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "관리자 상품 수정 API", description = "상품 ID로 엔티티 찾아서 상품 업데이트 진행하는 API<br>(업데이트된 필드만 리턴)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 수정 성공")})
    public ResponseEntity<ApiResponse<UpdateItemResponse>> updateItem(@PathVariable Long id,
        @Validated @RequestBody UpdateItemRequest updateItemRequest) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID를 다시 확인해주세요.");
        }
        final UpdateItemResponse updated = itemService.updateItem(id, updateItemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(createSuccess(updated));
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "관리자 상품 삭제 API", description = "상품 ID로 상품 삭제 진행하는 API<br>(리턴 값 없음)")
    public ResponseEntity<ApiResponse<Object>> deleteItem(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID를 다시 확인해주세요.");
        }
        itemService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(createSuccessWithNoData());
    }
}
