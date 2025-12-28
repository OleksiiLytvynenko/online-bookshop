package me.krumka.onlinebookshop.repository.shoppingcart;

import java.util.Optional;
import me.krumka.onlinebookshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @EntityGraph(attributePaths = {"cartItems", "cartItems.book", "cartItems.id"})
    Optional<ShoppingCart> findById(Long id);
}
