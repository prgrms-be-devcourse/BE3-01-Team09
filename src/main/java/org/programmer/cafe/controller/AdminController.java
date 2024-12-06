package org.programmer.cafe.controller;

import static org.programmer.cafe.global.response.ApiResponse.createError;
import static org.programmer.cafe.global.response.ApiResponse.createSuccess;
import static org.programmer.cafe.global.response.ApiResponse.createSuccessWithNoData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.dto.CreateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.CreateItemResponse;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.item.service.ItemService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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

    @PostMapping(value = "/items/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 업로드 API", description = "이미지를 서버에 업로드합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "업로드 성공", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<ApiResponse<Object>> uploadItem(
        @RequestParam("image") MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createError("이미지가 비어있습니다."));
        }
        // 파일 저장 경로
        final String filePath = itemService.uploadImage(image);
        // 성공 시 파일 경로 반환
        return ResponseEntity.status(HttpStatus.OK).body(createSuccess(filePath));
    }

    @PatchMapping("/items/{id}")
    @Operation(summary = "관리자 상품 수정 API", description = "상품 ID로 엔티티 찾아서 상품 업데이트 진행하는 API<br>(업데이트된 필드만 리턴)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 수정 성공")})
    public ResponseEntity<ApiResponse<UpdateItemResponse>> updateItem(@PathVariable Long id,
        @Validated @RequestBody UpdateItemRequest updateItemRequest) {
        validateId(id);
        final UpdateItemResponse updated = itemService.updateItem(id, updateItemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(createSuccess(updated));
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "관리자 상품 삭제 API", description = "상품 ID로 상품 삭제 진행하는 API<br>(리턴 값 없음)")
    public ResponseEntity<ApiResponse<Object>> deleteItem(@PathVariable Long id)
        throws IOException {
        validateId(id);
        itemService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(createSuccessWithNoData());
    }

    @GetMapping("/{filename}")
    @Operation(summary = "이미지 조회 API", description = "페이지에서 이미지를 조회할 수 있는 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이미지 파일 리턴")})
    public ResponseEntity<Resource> getImage(@PathVariable String filename)
        // ApiResponse 로 이미지를 어떻게 리턴받을지 몰라서 이것만 안 감싸고 하겠습니다...!
        throws MalformedURLException {
        final Resource image = itemService.getImage(filename);
        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID를 다시 확인해주세요.");
        }
    }
}
