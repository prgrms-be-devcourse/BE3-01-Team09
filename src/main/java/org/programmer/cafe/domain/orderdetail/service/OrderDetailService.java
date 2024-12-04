package org.programmer.cafe.domain.orderdetail.service;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailDTO;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailService {
    private OrderDetailDTO orderDetailDTO;
    private OrderDetailRepository orderDetailRepository;


    public OrderDetailDTO findOrderDetail(Long orderDetailId) {

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
            .orElseThrow(() -> new NoSuchElementException("OrderDetail not found with id: " + orderDetailId));
        return orderDetailDTO;
    }
}
