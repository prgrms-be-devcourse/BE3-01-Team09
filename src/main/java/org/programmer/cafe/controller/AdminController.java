package org.programmer.cafe.controller;

import static org.programmer.cafe.global.response.ApiResponse.createSuccess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.dto.ItemDto;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 페이지")
public class AdminController {

    private final ItemService itemService;

    @PostMapping("/items")
    @Operation(summary = "관리자 상품 등록 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "등록 성공")})
    public ResponseEntity<ApiResponse<ItemDto>> addItem(@Validated @RequestBody ItemDto itemDto) {
        final ItemDto saved = itemService.save(itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSuccess(saved));
    }
}
