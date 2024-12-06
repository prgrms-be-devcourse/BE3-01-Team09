package org.programmer.cafe.domain.orderdetail.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.entity.dto.OrderDetailMapper;
import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailRequest;
import org.programmer.cafe.domain.orderdetail.entity.dto.UserOrderDetailResponse;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.programmer.cafe.service.UpdateItemStockService;
import org.programmer.cafe.service.UpdateOrderDetailPriceService;
import org.programmer.cafe.service.UpdateOrderPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailOrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    private final UpdateItemStockService updateItemStockService;
    private final UpdateOrderDetailPriceService updateOrderDetailPriceService;
    private final UpdateOrderPriceService updateOrderPriceService;

    public List<UserOrderDetailResponse> findAllOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));
        // 주문 상세 찾기 - 동일한 orderId 가진거 반환
        List<OrderDetail> orderDetail = orderDetailRepository.findAllByOrder_Id(orderId);
        //반환
        List<UserOrderDetailResponse> userOrderDetailRespons = new ArrayList<>();
        // 주문 상세 리스트 읽으며 DTO 조합하기 -
        for (OrderDetail list : orderDetail) {
            // 아이템 가져오기
            Item item = itemRepository.findById(list.getItem().getId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));
            Long itemId = item.getId();
            String itemName = item.getName();
            String itemImage = item.getImage();
            int totalPrice = list.getTotalPrice();
            // 주문 가져오기
            String address = order.getAddress();
            String addressDetail = order.getAddressDetail();
            String zipcode = order.getZipcode();

            UserOrderDetailResponse userOrderDetailResponse = UserOrderDetailResponse.builder()
                .itemId(itemId)
                .itemName(itemName)
                .itemImage(itemImage)
                .totalPrice(totalPrice)
                .address(address)
                .address_detail(addressDetail)
                .zipcode(zipcode)
                .orderId(orderId)
                .build();
            userOrderDetailRespons.add(userOrderDetailResponse);
        }
        return userOrderDetailRespons;
    }

    @Transactional
    public void updateOrderDetailCountService(Long orderDetailId, int newCount,
        UserOrderDetailRequest userOrderDetailRequest) {

        if (newCount <= 0) {
            throw new BadRequestException(ErrorCode.COUNT_BELOW_MINIMUM);
        }

        Order order = orderRepository.findById(userOrderDetailRequest.getOrderId())
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));

        int oldTotalPrice = userOrderDetailRequest.getTotalPrice();
        int oldPayment = order.getTotalPrice();
        UserOrderDetailRequest updatedRequest = updateItemStockService.updateItemStock(
            orderDetailId, newCount, userOrderDetailRequest);
        updatedRequest = updateOrderDetailPriceService.updateOrderDetailPriceService(orderDetailId,
            newCount, updatedRequest);
        updateOrderPriceService.updateOrderPrice(order.getId(), updatedRequest, oldTotalPrice,
            oldPayment);
        orderDetailRepository.save(OrderDetailMapper.INSTANCE.toEntity(updatedRequest));
    }
}
