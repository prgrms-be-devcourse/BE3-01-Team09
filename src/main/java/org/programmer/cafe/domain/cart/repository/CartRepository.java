package org.programmer.cafe.domain.cart.repository;

import java.util.Optional;
import org.programmer.cafe.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndItemId(Long userId, Long itemId);

}
