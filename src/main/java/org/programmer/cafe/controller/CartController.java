package org.programmer.cafe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.cart.dto.CreateCartItemRequest;
import org.programmer.cafe.domain.cart.service.CartService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 상품 등록 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "등록 성공")})
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createCartItem(@Valid @RequestBody CreateCartItemRequest request) {
        // TODO: SecurityContextHolder에서 인증된 유저의 userId를 가져와야 함.
        Long userId = 1L;
        cartService.createCartItem(request, userId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoData());
    }
}
