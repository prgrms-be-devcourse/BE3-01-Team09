package org.programmer.cafe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;

import org.programmer.cafe.domain.item.repository.ItemRepository;

import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateItemStockService {

    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;

    // 주문 수량 변경 서비스
    public UserOrderDetailRequest updateItemStock(Long orderDetailId, int newCount,
        UserOrderDetailRequest userOrderDetailRequest) {

        Item item = itemRepository.findById(userOrderDetailRequest.getItemId())
            .orElseThrow(() -> new BadRequestException(
                ErrorCode.NONEXISTENT_ITEM));
        int updatedStock = item.getStock() + (userOrderDetailRequest.getCount() - newCount);

        //재고 변경
        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
            .name(item.getName())
            .image(item.getImage())
            .price(item.getPrice())
            .stock(updatedStock)
            .status(item.getStatus())
            .build();

        // 수량 업데이트
        UserOrderDetailRequest updatedRequest = userOrderDetailRequest.toBuilder()
            .count(newCount)
            .build();

        return userOrderDetailRequest;
    }
}
