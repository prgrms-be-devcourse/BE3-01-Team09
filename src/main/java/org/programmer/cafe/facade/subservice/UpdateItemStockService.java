package org.programmer.cafe.facade.subservice;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

// 재고 수정 서비스
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateItemStockService {
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;


    public void updateItemStock(Long orderId){
        // order_id 가 같은 모든 상풍 list로 가져오기
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(orderId);
        // list의 값을 읽으면서 list의 수량 + item의 수량 더하기
        for(OrderDetail orderDetail : orderDetails){
            // 해당 상품에 해당하는 ItemId 가져오기
            Long itemId = orderDetail.getItem().getId();
            Item item = itemRepository.findById(itemId).get();
            // 주문 수량 가져오기
            int count = orderDetail.getCount();
            // 아이템 재고 가져오기
            int stock = item.getStock();

            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .stock(stock+count)
                .build();

            item.update(updateItemRequest);
            log.info("재고 업데이트 itemId: {},주문수량: {},기존 Stock: {}, updatedStock: {}", itemId,count,stock ,item.getStock());

        }
        log.info("재고 업데이트 완료");
    }
}
