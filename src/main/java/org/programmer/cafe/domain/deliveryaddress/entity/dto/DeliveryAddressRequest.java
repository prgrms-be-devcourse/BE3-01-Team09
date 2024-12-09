package org.programmer.cafe.domain.deliveryaddress.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programmer.cafe.domain.user.entity.User;

@AllArgsConstructor
@Getter
public class DeliveryAddressRequest {
    private Long id;
    private String name;
    private String zipcode;
    private String address;
    private String addressDetail;
    private Boolean defaultYn;
    private Long userId;
}
