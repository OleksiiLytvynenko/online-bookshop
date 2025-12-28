package me.krumka.onlinebookshop.dto.shoppingcart;

import java.util.List;
import lombok.Data;
import me.krumka.onlinebookshop.dto.cartitem.CartItemDto;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
