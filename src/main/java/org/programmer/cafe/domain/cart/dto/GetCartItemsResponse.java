package org.programmer.cafe.domain.cart.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmer.cafe.domain.cart.entity.Cart;
import org.programmer.cafe.domain.item.entity.ItemStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCartItemsResponse {
    private List<CartItemInfo> cartItems;

    public GetCartItemsResponse(List<Cart> carts) {
        this.cartItems = carts.stream()
            .map(CartItemInfo::new)
            .toList();
    }

    @Getter
    static class CartItemInfo {
        private Long id;
        private String name;
        private String image;
        private int price;
        private ItemStatus status;
        private int count;
        private int totalPrice;

        public CartItemInfo(Cart cart) {
            this.id = cart.getItem().getId();
            this.name = cart.getItem().getName();
            this.image = cart.getItem().getImage();
            this.price = cart.getItem().getPrice();
            this.status = cart.getItem().getStatus();
            this.count = cart.getCount();
            this.totalPrice = cart.getTotalPrice();
        }
    }
}