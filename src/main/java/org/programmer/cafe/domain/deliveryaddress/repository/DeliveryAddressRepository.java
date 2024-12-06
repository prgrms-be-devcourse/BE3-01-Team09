package org.programmer.cafe.domain.deliveryaddress.repository;

import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByUserEmail(String email);

    Boolean saveDeliveryAddress(DeliveryAddressRequest deliveryAddressRequest);

    Boolean updateDeliveryAddress(DeliveryAddressRequest deliveryAddressRequest);

    Boolean deleteDeliveryAddressById(Long id);
}
