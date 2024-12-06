package org.programmer.cafe.domain.deliveryaddress.repository;

import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryAddress, Long> {

}
