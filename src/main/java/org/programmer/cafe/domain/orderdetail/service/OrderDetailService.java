package org.programmer.cafe.domain.orderdetail.service;

import java.util.List;
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

    // 주문 상세 가져오기
    public List<OrderDetailResponse> findOrderDetails(Long orderId) {
       List<OrderDetail> orderDetail = orderDetailRepository.findAllByOrder_Id(orderId);
        log.info("반환된 주문 상세 개수 : {}",orderDetail.size());

        List<OrderDetailResponse> lists = orderDetail.stream()
            .map(OrderDetailMapper.INSTANCE::toCreateResponseDto)
            .toList();

        return lists;
    }


    // 주문 수정 - 수량

    // 주문 수정 - 배송지
}
