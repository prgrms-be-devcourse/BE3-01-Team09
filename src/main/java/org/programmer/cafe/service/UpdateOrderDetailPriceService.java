package org.programmer.cafe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailRequest;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;

// 주문 상세 총액 변경
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateOrderDetailPriceService {

    private final ItemRepository itemRepository;

    public UserOrderDetailRequest updateOrderDetailPriceService(Long orderDetailId, int newCount,
        UserOrderDetailRequest userOrderDetailRequest) {

        Item item = itemRepository.findById(userOrderDetailRequest.getItemId())
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));

        int newTotalPrice = item.getPrice() * newCount;

        UserOrderDetailRequest updatedRequest = userOrderDetailRequest.toBuilder()
            .totalPrice(newTotalPrice).build();

        return updatedRequest;
    }
}
