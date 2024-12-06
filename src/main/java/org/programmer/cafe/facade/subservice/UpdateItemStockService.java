package org.programmer.cafe.facade.subservice;

// 아이템 개별 수량 변경 서비스

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.ItemMapper;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.order.entity.dto.OrderRequest;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateItemStockService {

    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;

    // 주문 수량 변경 서비스
    public UpdateItemResponse updateItemStock(Long orderDetailId,int newCount, OrderDetailRequest orderDetailRequest) {
        log.info("주문 수량 변경 시작");

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
            .orElseThrow(() -> new IllegalArgumentException("OrderDetail not found for id: " + orderDetailId));
        //변경할 아이템
        Long itemId = orderDetailRequest.getItemId();
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found for id: " + itemId));
        // 변경 전 주문 수량
        int count = orderDetailRequest.getCount();
        log.info("변경 ItemID: {} 입력된 수량: {} 기존 주문 수량: {}", itemId,newCount,count);
        // 수량 변경하기
        orderDetail.updateCount(newCount);
        // 변경할 재고 값
        int updatedStock = item.getStock()+ (count - newCount);
        // 재고 변경하기
        log.info("재고 업데이트 itemId: {} 변경할 재고 값: {} 변경 전 재고: {}", itemId,updatedStock, item.getStock());

        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
            .name(item.getName())
            .image(item.getImage())
            .price(item.getPrice())
            .stock(updatedStock) // 업데이트된 재고
            .status(item.getStatus())
            .build();

        UpdateItemResponse updateItemResponse = ItemMapper.INSTANCE.toUpdateItemResponse(item, updateItemRequest);
        item.update(updateItemRequest);
        log.info(" 변경 후 재고: {}", item.getStock());
        log.info("단가: {}", item.getPrice());
        log.info("주문수량 변경 종료");
    return updateItemResponse;
    }
}
