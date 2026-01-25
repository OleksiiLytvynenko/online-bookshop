package me.krumka.onlinebookshop.mapper;

import me.krumka.onlinebookshop.config.MapperConfig;
import me.krumka.onlinebookshop.dto.order.OrderDto;
import me.krumka.onlinebookshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfig.class,
        uses = OrderItemMapper.class
)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toOrderDto(Order order);
}
