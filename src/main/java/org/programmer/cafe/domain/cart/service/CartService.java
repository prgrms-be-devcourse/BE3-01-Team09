package org.programmer.cafe.domain.cart.service;

import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.cart.dto.CreateCartItemRequest;
import org.programmer.cafe.domain.cart.dto.GetCartItemsResponse;
import org.programmer.cafe.domain.cart.entity.Cart;
import org.programmer.cafe.domain.cart.entity.CartStatus;
import org.programmer.cafe.domain.cart.repository.CartRepository;
import org.programmer.cafe.domain.item.entity.Item;
import org.programmer.cafe.domain.item.repository.ItemRepository;
import org.programmer.cafe.domain.user.entity.User;
import org.programmer.cafe.domain.user.repository.UserRepository;
import org.programmer.cafe.exception.BadRequestException;
import org.programmer.cafe.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createCartItem(CreateCartItemRequest request, Long userId) {
        final Long itemId = request.getItemId();
        final Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_ITEM));
        final User user = userRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_USER));

        final Optional<Cart> cartOptional = cartRepository.findByUserIdAndItemId(userId, itemId);

        // 해당 아이템에 대한 장바구니 내역이 존재하는 경우 -> update
        cartOptional.ifPresentOrElse(cart -> {
            cartRepository.save(cart.addItem(item));
        }, () -> { // 해당 아이템에 대한 장바구니 내역이 존재하지 않는 경우 -> insert
            cartRepository.save(Cart.builder()
                .count(1)
                .totalPrice(item.getPrice())
                .user(user)
                .item(item)
                .build());
        });
    }

    public GetCartItemsResponse getCartItems(Long userId) {
        final List<Cart> carts = getCartsByUserId(userId);
        return new GetCartItemsResponse(carts);
    }

    public void updateCartItemCount(Long itemId, int count, Long userId) {
        throwIfInvalidCount(count);

        final Cart cart = cartRepository.findByUserIdAndItemId(userId, itemId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_CART));

        cartRepository.save(cart.updateCount(count));
    }

    /**
     * 장바구니에 담은 수량이 1보다 작은 경우 예외 처리
     */
    private void throwIfInvalidCount(int count) {
        if (count < 1) {
            throw new BadRequestException(ErrorCode.COUNT_BELOW_MINIMUM);
        }
    }

    public void deleteCartItem(Long itemId, Long userId) {
        final Cart cart = cartRepository.findByUserIdAndItemId(userId, itemId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_CART));

        cartRepository.delete(cart);
    }

    public List<Cart> getCartsByUserId(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    public int calculateTotalPrice(List<Cart> carts) {
        validateCartsNotEmpty(carts);

        return carts.stream()
            .mapToInt(Cart::getTotalPrice)
            .sum();
    }

    public Cart getMaxCountCart(List<Cart> carts) {
        validateCartsNotEmpty(carts);

        return carts.stream()
            .max(Comparator.comparingInt(Cart::getCount))
            .orElseThrow(() -> new BadRequestException(ErrorCode.NONEXISTENT_CART));
    }

    private void validateCartsNotEmpty(List<Cart> carts) {
        if (carts == null || carts.isEmpty()) {
            throw new BadRequestException(ErrorCode.NONEXISTENT_CART);
        }
    }

    public void updateCartsStatusToPendingPayment(List<Cart> carts) {
        for (Cart cart : carts) {
            cart.updateStatus(CartStatus.PENDING_PAYMENT);
        }

        // TODO: 배치 처리 필요
        cartRepository.saveAll(carts);
    }
}
