package org.programmer.cafe.domain.orderdetail.service;

import java.util.NoSuchElementException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.order.entity.dto.OrderMapper;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailResponse findOrderDetail(Long orderId) {
        OrderDetail orderDetail = orderDetailRepository.findByOrder_Id(orderId);
        log.info("반환된 주문 상세 : {}",orderDetail.getId());
        return OrderDetailMapper.INSTANCE.toCreateResponseDto(orderDetail);
    }
}
