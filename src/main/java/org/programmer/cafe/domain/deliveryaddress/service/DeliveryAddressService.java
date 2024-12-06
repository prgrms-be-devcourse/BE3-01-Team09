package org.programmer.cafe.domain.deliveryaddress.service;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.deliveryaddress.entity.dto.DeliveryAddressRequest;
import org.programmer.cafe.domain.deliveryaddress.repository.DeliveryRepository;
import org.programmer.cafe.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public EntityManagerFactory emf;

    @Autowired
    public DeliveryService(final DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public DeliveryAddressRequest getAddressByEmail(DeliveryAddressRequest request) {
        if (request == null) {
            
        }
    }

}
