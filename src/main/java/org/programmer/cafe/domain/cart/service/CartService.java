package org.programmer.cafe.domain.cart.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.programmer.cafe.domain.cart.dto.CreateCartItemRequest;
import org.programmer.cafe.domain.cart.entity.Cart;
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
}