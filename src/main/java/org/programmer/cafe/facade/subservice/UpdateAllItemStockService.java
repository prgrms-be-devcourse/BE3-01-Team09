package org.programmer.cafe.facade.subservice;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.ItemMapper;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 재고 수정 서비스
@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateAllItemStockService {
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;

    public List<UpdateItemResponse> updateAllItemStock(Long orderId){
        List<UpdateItemResponse> responseList = new ArrayList<>();

        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(orderId);

        for (OrderDetail orderDetail : orderDetails) {
            Item item = orderDetail.getItem();
            int count = orderDetail.getCount(); // 주문 수량
            int updatedStock = item.getStock() + count;

            // UpdateItemRequest 생성
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .name(item.getName())
                .image(item.getImage())
                .price(item.getPrice())
                .stock(updatedStock) // 업데이트된 재고
                .status(item.getStatus())
                .build();

            // Item 업데이트 및 Response 생성
            UpdateItemResponse response = ItemMapper.INSTANCE.toUpdateItemResponse(item, updateItemRequest);
            responseList.add(response);

            log.info("재고 업데이트 itemId: {}, 변경된 필드: {}", item.getId(), response.getUpdatedFields());
        }

        log.info("재고 업데이트 완료, 총 {}개 아이템 처리됨", responseList.size());
        return responseList; // 업데이트 결과 반환
    }

}
