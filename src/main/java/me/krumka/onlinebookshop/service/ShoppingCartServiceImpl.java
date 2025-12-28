package me.krumka.onlinebookshop.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.shoppingcart.ShoppingCartDto;
import me.krumka.onlinebookshop.exception.EntityNotFoundException;
import me.krumka.onlinebookshop.mapper.ShoppingCartMapper;
import me.krumka.onlinebookshop.model.Book;
import me.krumka.onlinebookshop.model.CartItem;
import me.krumka.onlinebookshop.model.ShoppingCart;
import me.krumka.onlinebookshop.repository.book.BookRepository;
import me.krumka.onlinebookshop.repository.cartitem.CartItemRepository;
import me.krumka.onlinebookshop.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getShoppingCartByUserId(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found by id: " + userId)
        );
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addBookToShoppingCart(Long userId, Long bookId, Integer quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found by id: " + userId)
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found by id: " + bookId)
        );
        Optional<CartItem> existingItem = shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(bookId))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            shoppingCart.getCartItems().add(cartItem);
        }
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId, Integer quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found for user id: " + userId)
        );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Cart item not found for id: " + cartItemId
                        )
                );
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toShoppingCartDto(cartItem.getShoppingCart());
    }

    @Override
    @Transactional
    public void removeBookFromShoppingCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found for user id: " + userId)
        );
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Cart item not found for id: " + cartItemId
                        )
                );
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }
}
