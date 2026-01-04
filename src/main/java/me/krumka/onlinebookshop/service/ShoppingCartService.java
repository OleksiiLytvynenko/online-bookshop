package me.krumka.onlinebookshop.service;

import me.krumka.onlinebookshop.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCartByUserId(Long userId);

    ShoppingCartDto addBookToShoppingCart(Long userId, Long bookId, Integer quantity);

    ShoppingCartDto updateBookQuantity(Long userId, Long cartItemId, Integer quantity);

    void removeBookFromShoppingCart(Long userId, Long bookId);
}
