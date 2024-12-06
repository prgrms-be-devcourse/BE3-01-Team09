package org.programmer.cafe.facade.subservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.dto.ItemMapper;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.dto.OrderMapper;
import org.programmer.cafe.domain.order.entity.dto.OrderRequest;
import org.programmer.cafe.domain.order.entity.dto.OrderResponse;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

// 주문 금액 변경
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateOrderPriceService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    public OrderResponse updateOrderPrice(Long orderDetailId, OrderDetailRequest orderDetailRequest) {
        log.info("결제 금액 변경 시작");
        //해당하는 주문 찾기
        Long orderId = orderDetailRequest.getOrderId();
        log.info("받아온 주문 번호: {}", orderId);

        Order order = orderRepository.findById(orderId).orElse(null);
        log.info("주문 번호: {} 변경 전 가격: {}", orderId, order.getTotalPrice());

        List<OrderDetail> OrderDetailLists =  orderDetailRepository.findAllByOrder_Id(orderId);
        int changedPrice = 0;
        for(OrderDetail list : OrderDetailLists) {
            changedPrice+= list.getTotalPrice();
        }

        OrderRequest orderRequest = OrderRequest.builder()
            .totalPrice(changedPrice)
            .status(order.getStatus())
            .name(order.getName())
            .zipcode(order.getZipcode())
            .address(order.getAddress())
            .addressDetail(order.getAddressDetail())
            .itemName(order.getItemName())
            .itemImage(order.getItemImage())
            .user(order.getUser().getId())
            .build();

        OrderResponse orderResponse = OrderMapper.INSTANCE.toResponseDto(orderRequest);
        order.updateTotalPrice(changedPrice);

        log.info("업데이트 된 총 주문 금액: {}", orderResponse.getTotalPrice());

        return orderResponse;
    }
}
