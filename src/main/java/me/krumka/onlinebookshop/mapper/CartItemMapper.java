package me.krumka.onlinebookshop.mapper;

import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.cartitem.CartItemDto;
import me.krumka.onlinebookshop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toCartItemDto(CartItem cartItem);
}
