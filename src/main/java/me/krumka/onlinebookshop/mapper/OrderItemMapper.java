package me.krumka.onlinebookshop.mapper;

import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.orderitem.OrderItemDto;
import me.krumka.onlinebookshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
