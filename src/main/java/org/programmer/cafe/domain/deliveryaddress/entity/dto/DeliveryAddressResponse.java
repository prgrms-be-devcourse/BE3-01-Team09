package org.programmer.cafe.domain.deliveryaddress.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.programmer.cafe.domain.user.entity.User;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DeliveryAddressResponse {
    private Long id;
    private String name;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String defaultYn;
    private User user;
}
