package org.programmer.cafe.domain.orderdetail.service2;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.entity.dto2.AdminDetailViewResponse;
import org.programmer.cafe.domain.orderdetail.entity.dto2.UserDetailViewResponse;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminDetailOrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public List<AdminDetailViewResponse> findAllOrderDetails(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(orderId);
        List<AdminDetailViewResponse> adminDetailViewResponses = new ArrayList<>();

        for(OrderDetail list : orderDetails) {
            // 아이템 가져오기
            Item item = itemRepository.findById(list.getItem().getId()).orElse(null);
            Long itemId = item.getId();
            String itemName = item.getName();
            String itemImage = item.getImage();
            int totalPrice = list.getTotalPrice();
            // 주문 가져오기
            Order order = orderRepository.findById(orderId).orElse(null);
            String address = order.getAddress();
            String addressDetail = order.getAddressDetail();
            String zipcode = order.getZipcode();

            AdminDetailViewResponse adminDetailViewResponse = AdminDetailViewResponse.builder()
                .itemId(itemId)
                .itemName(itemName)
                .itemImage(itemImage)
                .totalPrice(totalPrice)
                .address(address)
                .address_detail(addressDetail)
                .zipcode(zipcode)
                .orderId(orderId)
                .build();
            adminDetailViewResponses.add( adminDetailViewResponse);
        }
        return adminDetailViewResponses;
    }
}
