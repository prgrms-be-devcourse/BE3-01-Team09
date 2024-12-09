package org.programmer.cafe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.service.ItemService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final ItemService itemService;

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
}
