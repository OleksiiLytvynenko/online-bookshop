package me.krumka.onlinebookshop.mapper;

import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.shoppingcart.ShoppingCartDto;
import me.krumka.onlinebookshop.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfig.class,
        uses = CartItemMapper.class
)
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);
}
