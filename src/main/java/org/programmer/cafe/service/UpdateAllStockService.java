package org.programmer.cafe.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateAllStockService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;

    public void updateOrderStock(Long orderId){
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));

        List<OrderDetail> orderDetail = orderDetailRepository.findAllByOrder_Id(orderId);

        for(OrderDetail list : orderDetail){
            Item item = list.getItem();
            int count = list.getCount();    // 주문 수량
            int updatedStock = item.getStock() + count; // 바뀌는 재고

            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .name(item.getName())
                .image(item.getImage())
                .price(item.getPrice())
                .stock(updatedStock)
                .status(item.getStatus())
                .build();

            item.update(updateItemRequest);
        }
    }
}
