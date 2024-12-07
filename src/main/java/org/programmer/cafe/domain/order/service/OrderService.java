package org.programmer.cafe.domain.order.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmer.cafe.domain.cart.entity.Cart;
import org.programmer.cafe.domain.cart.service.CartService;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.entity.ItemStatus;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.order.dto.CreateOrderRequest;
import org.programmer.cafe.domain.order.dto.OrderMapper;
import org.programmer.cafe.domain.order.entity.Order;
import org.programmer.cafe.domain.order.entity.OrderStatus;
import org.programmer.cafe.domain.order.repository.OrderRepository;
import org.programmer.cafe.domain.orderdetail.dto.OrderDetailMapper;
import org.programmer.cafe.domain.orderdetail.entity.OrderDetail;
import org.programmer.cafe.domain.orderdetail.repository.OrderDetailRepository;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemRepository itemRepository;
    private final CartService cartService;

    public Optional<Order> getPendingPaymentOrderByUserId(Long userId) {
        return orderRepository.findByUserIdAndStatusIs(userId, OrderStatus.PENDING_PAYMENT);
    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        orderRepository.save(order.updateStatus(status));
    }

    @Transactional
    public void createOrder(Long userId, CreateOrderRequest createOrderRequest) {
        final User user = userRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_USER));

        List<Cart> carts = cartService.getCartsByUserId(userId);

        for (Cart cart : carts) {
            int stock = cart.getItem().getStock();

            // 판매 중인 상태가 아닌 경우 예외 처리
            if (cart.getItem().getStatus() != ItemStatus.ON_SALE) {
                throw new BadRequestException(ErrorCode.ITEM_NOT_FOR_SALE);
            }

            // 재고가 충분하지 않은 경우 예외 처리
            if (cart.getCount() > stock) {
                throw new BadRequestException(ErrorCode.INSUFFICIENT_STOCK);
            }
        }

        final int totalPrice = cartService.calculateTotalPrice(carts);
        final Cart maxCountCart = cartService.getMaxCountCart(carts);
        final String displayItemName = maxCountCart.getItem().getName();
        final String displayItemImage = maxCountCart.getItem().getImage();

        Order order = OrderMapper.INSTANCE.toOrder(createOrderRequest, totalPrice,
            displayItemName,
            displayItemImage, user);

        orderRepository.save(order);

        List<OrderDetail> orderDetails = carts.stream()
            .map(cart -> OrderDetailMapper.INSTANCE.orderAndCartToOrderDetail(order, cart))
            .collect(Collectors.toList());

        // TODO: 배치 처리 필요
        orderDetailRepository.saveAll(orderDetails);

        // 장바구니 상태 변경 (바로 삭제하지 않음. 결제 도중 에러 발생시 차감시킨 재고를 복원하기 위함)
        cartService.updateCartsStatusToPendingPayment(carts);

        List<Item> items = carts.stream()
            .peek(cart -> cart.getItem().decreaseStock(cart.getCount()))
            .map(Cart::getItem)
            .toList();

        // TODO: 배치 처리 필요
        itemRepository.saveAll(items);
    }
}