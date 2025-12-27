package me.krumka.onlinebookshop.repository.cartitem;

import java.util.Optional;
import me.krumka.onlinebookshop.model.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(
            attributePaths = {"shoppingCart", "shoppingCart.user", "shoppingCart.cartItems"}
    )
    Optional<CartItem> findById(Long id);
}
