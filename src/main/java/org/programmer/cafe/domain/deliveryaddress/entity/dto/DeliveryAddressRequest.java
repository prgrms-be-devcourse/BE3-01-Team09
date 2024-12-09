package org.programmer.cafe.domain.deliveryaddress.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.programmer.cafe.domain.user.entity.User;

@Getter
@Setter
@ToString
public class DeliveryAddressRequest {
    private Long id;
    private String name;
    private String zipcode;
    private String address;
    private String addressDetail;
    private Boolean defaultYn;
    private Long userId;

    public DeliveryAddressRequest() {}

    public DeliveryAddressRequest(Long id, String name, String zipcode,
                                  String address, String addressDetail,
                                  String yesNo, Long userId) {
        this.id = id;
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.defaultYn = "Y".equalsIgnoreCase(yesNo);
        this.userId = userId;
    }

    public void setDefaultYn(String yesNo) {
        this.defaultYn = "Y".equalsIgnoreCase(yesNo);
    }

}
