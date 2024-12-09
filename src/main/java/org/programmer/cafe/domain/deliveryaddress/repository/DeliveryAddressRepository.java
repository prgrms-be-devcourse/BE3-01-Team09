package org.programmer.cafe.domain.deliveryaddress.repository;

import jakarta.transaction.Transactional;
import org.programmer.cafe.domain.deliveryaddress.entity.DeliveryAddress;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByUserEmail(String email);

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO delivery_addresses (name, zipcode, address, address_detail, default_yn, user_id) " +
                    "VALUES (:#{#request.name}, :#{#request.zipcode}, :#{#request.address}, :#{#request.addressDetail}, :#{#request.defaultYn}, :#{#request.userId})",
            nativeQuery = true
    )
    int saveDeliveryAddress(@Param("request") DeliveryAddressRequest request);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE delivery_addresses  "+
                    "SET name = :#{#request.name}, zipcode = :#{#request.zipcode}, address = :#{#request.address}, address_detail = :#{#request.addressDetail}, default_yn = :#{#request.defaultYn}, user_id = :#{#request.userId} " +
                    " WHERE id = :#{#request.id}",
            nativeQuery = true
    )
    int updateDeliveryAddress(@Param("request") DeliveryAddressRequest request);


    int deleteDeliveryAddressById(Long id);

    boolean existsByUserIdAndDefaultYn(Long userId, boolean defaultYn);

    Long getUserIdById(Long id);
}
