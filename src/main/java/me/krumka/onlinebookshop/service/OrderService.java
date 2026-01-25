package me.krumka.onlinebookshop.service;

import me.krumka.onlinebookshop.dto.order.OrderDto;
import me.krumka.onlinebookshop.dto.orderitem.OrderItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(Long userId, String shippingAddress);

    Page<OrderDto> getAllOrdersByUserId(Long userId, Pageable pageable);

    OrderDto updateOrderStatusById(Long orderId, String status);

    Page<OrderItemDto> getAllOrderItemsByOrderId(Long userId, Long orderId, Pageable pageable);

    OrderItemDto getOrderItemById(Long userId, Long orderId, Long orderItemId);
}
