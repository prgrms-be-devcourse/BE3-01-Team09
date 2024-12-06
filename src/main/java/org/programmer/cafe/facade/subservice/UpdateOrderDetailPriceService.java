package org.programmer.cafe.facade.subservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

// 주문 상세 총액 변경
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateOrderDetailPriceService {

    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;

    public OrderDetailResponse UpdateOrderDetailPriceService(Long orderDetailId, OrderDetailRequest orderDetailRequest) {
        log.info("주문 상세 총액 변경 시작");
        // 총액을 변경할 주문 상세 가져오기
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        // 주문상세에 해당하는 아이템 가져오기
        Long itemId = orderDetailRequest.getItemId();
        Item item = itemRepository.findById(itemId).orElse(null);
        // 주문금액 계산
        int price = item.getPrice();    //  단가
        int count = orderDetail.getCount(); // 주문 수량
        int totalPrice = price * count; // 총액

        orderDetail.updateTotalPrice(totalPrice);
        log.info("*****주문번호: {}", orderDetailRequest.getOrderId());

        OrderDetailRequest orderDetailRequest1 = OrderDetailRequest.builder()
            .count(orderDetail.getCount())
            .totalPrice(orderDetail.getTotalPrice())
            .itemId(orderDetailRequest.getItemId())
            .orderId(orderDetailRequest.getOrderId())
            .build();

        log.info("주문 상세ID: {} 단가: {} 수량: {} 변경 후: {}", orderDetailId, price, count, orderDetail.getTotalPrice());
        log.info("주문 상세 총액 변경 종료");

        return OrderDetailMapper.INSTANCE.toDto(orderDetailRequest1);
    }
}
