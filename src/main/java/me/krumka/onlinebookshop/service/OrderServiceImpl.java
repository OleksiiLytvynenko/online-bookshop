package me.krumka.onlinebookshop.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.order.OrderDto;
import me.krumka.onlinebookshop.dto.orderitem.OrderItemDto;
import me.krumka.onlinebookshop.exception.EmptyShoppingCartException;
import me.krumka.onlinebookshop.exception.EntityNotFoundException;
import me.krumka.onlinebookshop.mapper.OrderItemMapper;
import me.krumka.onlinebookshop.mapper.OrderMapper;
import me.krumka.onlinebookshop.model.CartItem;
import me.krumka.onlinebookshop.model.Order;
import me.krumka.onlinebookshop.model.OrderItem;
import me.krumka.onlinebookshop.model.ShoppingCart;
import me.krumka.onlinebookshop.repository.order.OrderRepository;
import me.krumka.onlinebookshop.repository.orderitem.OrderItemRepository;
import me.krumka.onlinebookshop.repository.shoppingcart.ShoppingCartRepository;
import me.krumka.onlinebookshop.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, String shippingAddress) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found by id: " + userId)
        );
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EmptyShoppingCartException("Shopping cart is empty");
        }
        Order order = buildOrder(userId, shippingAddress, cartItems);
        Order savedOrder = orderRepository.save(order);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
        return orderMapper.toOrderDto(savedOrder);
    }

    private Order buildOrder(Long userId, String shippingAddress, Set<CartItem> cartItems) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        order.setUser(userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found by id: " + userId)
        ));
        order.setStatus(Order.Status.PENDING);
        BigDecimal total = BigDecimal.ZERO;
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            total = total.add(cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        order.setOrderItems(orderItems);
        order.setTotal(total);
        return order;
    }

    @Override
    public Page<OrderDto> getAllOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toOrderDto);
    }

    @Override
    public OrderDto updateOrderStatusById(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found by id: " + orderId)
        );
        order.setStatus(Order.Status.valueOf(status));
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderDto(updatedOrder);
    }

    @Override
    public Page<OrderItemDto> getAllOrderItemsByOrderId(
            Long userId,
            Long orderId,
            Pageable pageable) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found by id: " + orderId)
        );
        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }
        Page<OrderItem> orderItems = orderItemRepository.findAllByOrderId(
                orderId, pageable);
        return orderItems.map(orderItemMapper::toOrderItemDto);
    }

    @Override
    @Transactional
    public OrderItemDto getOrderItemById(Long userId, Long orderId, Long orderItemId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found by id: " + orderId)
        );
        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to access this order");
        }
        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Order item not found by id: "
                                + orderItemId + " and order id: " + orderId)
                );
        return orderItemMapper.toOrderItemDto(orderItem);
    }
}
