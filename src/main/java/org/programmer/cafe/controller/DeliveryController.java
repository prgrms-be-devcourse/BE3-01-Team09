package org.programmer.cafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressRequest;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressResponse;
import org.programmer.cafe.domain.deliveryaddress.service.DeliveryAddressService;
import org.programmer.cafe.domain.user.service.UserService;
import org.programmer.cafe.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryAddressService deliveryAddressService;

    private final UserService userService;

    public DeliveryController(DeliveryAddressService deliveryAddressService, UserService userService) {
        this.deliveryAddressService = deliveryAddressService;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getMyAddress(@PathVariable Long id) {
        String email = userService.getUserById(id).getEmail();
        try {
            List<DeliveryAddressResponse> response = deliveryAddressService.getAddressByEmail(email);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.createSuccess(response));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createError(e.getMessage()));
        }

    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Object>> addDeliveryAddress(@ModelAttribute DeliveryAddressRequest request) {
        System.out.println(request.toString());
        DeliveryAddressRequest addressRequest = new DeliveryAddressRequest(
                request.getId(),
                request.getName(),
                request.getZipcode(),
                request.getAddress(),
                request.getAddressDetail(),
                request.getDefaultYn() != null && request.getDefaultYn() ? "Y" : "N",
                request.getUserId()
        );
        boolean isSuccessful = deliveryAddressService.putAddress(addressRequest);

        try {
            if (isSuccessful) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.createSuccess(true));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.createErrorWithMsg("업데이트 실패"));
            }
        } catch (Exception e) {
            log.error("Error adding delivery address for userId: {}: {}", request.getUserId(), e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createError(e.getMessage()));
        }
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<Object>> updateDeliveryAddress(@RequestBody DeliveryAddressRequest request) {
        System.out.println(request.toString());
        DeliveryAddressRequest addressRequest = new DeliveryAddressRequest(
                request.getId(),
                request.getName(),
                request.getZipcode(),
                request.getAddress(),
                request.getAddressDetail(),
                request.getDefaultYn() != null && request.getDefaultYn() ? "Y" : "N",
                request.getUserId()
        );
        boolean isSuccessful = deliveryAddressService.updateAddress(addressRequest);

        try {
            if (isSuccessful) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.createSuccess(true));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.createErrorWithMsg("업데이트 실패"));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createError(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteDeliveryAddress(@PathVariable Long id) {
        boolean isSuccessful = deliveryAddressService.deleteAddress(id);

        try {
            if (isSuccessful) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.createSuccess(true));
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.createErrorWithMsg("삭제 실패"));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createError(e.getMessage()));
        }
    }


}
