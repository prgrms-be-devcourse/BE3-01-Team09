package org.programmer.cafe.domain.item.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.ItemStatus;
import org.programmer.cafe.domain.item.entity.dto.CreateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.CreateItemResponse;
import org.programmer.cafe.domain.item.entity.dto.GetItemResponse;
import org.programmer.cafe.domain.item.entity.dto.GetItemsResponse;
import org.programmer.cafe.domain.item.entity.dto.ItemMapper;
import org.programmer.cafe.domain.item.entity.dto.PageItemResponse;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemRequest;
import org.programmer.cafe.domain.item.entity.dto.UpdateItemResponse;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.item.sort.ItemSortType;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Value("${file.upload-dir}")
    private String filePath;

    public GetItemsResponse getItems(ItemSortType sortType) {
        final List<Item> items = itemRepository.findAllOrderBy(sortType);
        return new GetItemsResponse(items);
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
     * @param id                수정할 상품 ID
     * @param updateItemRequest 수정할 상품의 세부 정보를 포함하는 요청 객체
     * @return 상품의 수정된 사항들만 포함한 응답 객체
     * @throws EntityNotFoundException 엔티티가 존재하지 않을 시 Exception 발생
     */
    @Transactional
    public UpdateItemResponse updateItem(Long id, UpdateItemRequest updateItemRequest) {
        final Item item = itemRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));
        updateItemStatus(updateItemRequest, item);
        return ItemMapper.INSTANCE.toUpdateItemResponse(item, updateItemRequest);
    }

    /**
     * 상품 삭제 메서드
     *
     * @param id 삭제할 상품 ID
     */
    @Transactional
    public void deleteItem(Long id) throws IOException {
        final Item item = itemRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));

        // 저장된 이미지 삭제
        Files.deleteIfExists(Paths.get(item.getImage()));
        itemRepository.delete(item);

    }

    /**
     * @param multipartFile 이미지 파일
     * @return 파일 저장 경로
     * @throws IOException 파일 저장 중 에러 발생
     */
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        final String originalFilename = multipartFile.getOriginalFilename();

        final byte[] bytes = multipartFile.getBytes();
        String temp = "";
        try {
            final Path path = Paths.get(filePath);
            final Path file = path.resolve(
                String.valueOf(UUID.randomUUID()) + "_" + originalFilename);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            if (Files.notExists(file)) {
                Files.createFile(file);
                Files.write(file, bytes, StandardOpenOption.WRITE);
                temp = file.toString();
            }
            return file.toString();
        } catch (IOException e) {
            // 저장 중 에러 생기면 파일 삭제
            Files.deleteIfExists(Paths.get(temp));
            throw e;
        }
    }

    private void updateItemStatus(UpdateItemRequest updateItemRequest, Item item) {
        if (updateItemRequest.getStatus() == ItemStatus.DISCONTINUED) {
            return; // 판매 중단이면 재고에 따라 상태를 바꾸지 않음.
        }
        final int newStock = updateItemRequest.getStock();
        final int currentStock = item.getStock();

        if (newStock == 0) {
            updateItemRequest.setStatusOutOfStock(); // 재고가 없을 경우 '품절'로 변경.
        } else if (currentStock == 0 && newStock > 0) {
            updateItemRequest.setStatusOnSale(); // 품절상태에서 재고가 들어올 경우 '판매중'으로 변경
        }
    }

    public Page<PageItemResponse> getItemsWithPagination(Pageable pageable) {
        return itemRepository.findAllWithPaging(pageable);
    }

    public Resource getImage(String filename) throws MalformedURLException {
        final Path path = Paths.get(filePath).resolve(filename);
        if (Files.notExists(path)) {
            return new ClassPathResource("static/noimage.png");
        }
        return new UrlResource(path.toUri());
    }

    public GetItemResponse getItem(long id) {
        final Item item = itemRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));
        return ItemMapper.INSTANCE.toGetItemResponse(item);
    }
}
