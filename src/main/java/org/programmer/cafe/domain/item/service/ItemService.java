package org.programmer.cafe.domain.item.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.dto.CreateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.CreateItemResponse;
import org.programmer.cafe.domain.item.entity.dto.ItemMapper;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

/**
 * 상품 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 전체 목록 반환 메서드
     *
     * @return List<Item>
     */
    public List<Item> getItemList() {
        return itemRepository.findAll();
    }

    /**
     * 새로운 상품을 등록하는 메서드
     *
     * @param createItemRequest 생성할 상품의 세부 정보를 포함하는 요청 객체
     * @return 생성된 상품의 세부 정보를 포함한 응답 객체
     */
    public CreateItemResponse createItem(CreateItemRequest createItemRequest) {
        final Item item = ItemMapper.INSTANCE.toEntity(createItemRequest);
        final Item saved = itemRepository.save(item);
        return ItemMapper.INSTANCE.toCreateResponseDto(saved);
    }

    /**
     * 상품을 수정하는 메서드
     *
     * @param updateItemRequest 수정할 상품의 세부 정보를 포함하는 요청 객체
     * @return 상품의 수정된 사항들만 포함한 응답 객체
     * @throws EntityNotFoundException 엔티티가 존재하지 않을 시 Exception 발생
     */
    @Transactional
    public UpdateItemResponse updateItem(UpdateItemRequest updateItemRequest) {
        final long id = updateItemRequest.getId();
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }

        final Item item = itemRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found item with id: " + id));
        if (updateItemRequest.getStock() == 0) {
            updateItemRequest.setStatusOutOfStock(); // 재고가 없을 경우 '품절'로 변경.
        }
        if (item.getStock() == 0 && updateItemRequest.getStock() > 0) {
            updateItemRequest.setStatusOnSale(); // 품절상태에서 재고가 들어올 경우 '판매중'으로 변경
        }

        return ItemMapper.INSTANCE.toUpdateItemResponse(item, updateItemRequest);
    }
}
