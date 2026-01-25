package me.krumka.onlinebookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import me.krumka.onlinebookshop.dto.order.CreateOrderRequestDto;
import me.krumka.onlinebookshop.dto.order.OrderDto;
import me.krumka.onlinebookshop.dto.order.UpdateOrderStatusRequestDto;
import me.krumka.onlinebookshop.dto.orderitem.OrderItemDto;
import me.krumka.onlinebookshop.model.User;
import me.krumka.onlinebookshop.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/orders")
@Tag(name = "Order API", description = "Endpoints for managing an order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order", description = "Creates a new order")
    public OrderDto createOrder(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateOrderRequestDto order) {
        return orderService.createOrder(user.getId(), order.shippingAddress());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Retrieve user's order history",
            description = "Returns all orders ever place by user")
    public Page<OrderDto> getAllOrdersByUser(
            @AuthenticationPrincipal User user,
            Pageable pageable) {
        return orderService.getAllOrdersByUserId(user.getId(), pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status", description = "Updates order status")
    public OrderDto updateOrderStatus(
            @PathVariable
            @Positive(message = "ID must be greater than or equal to 1")
            @NotNull
            Long id,
            @Valid @RequestBody UpdateOrderStatusRequestDto orderStatusRequest) {
        return orderService.updateOrderStatusById(id, orderStatusRequest.status());
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Retrieve all order items for a specific order",
            description = "Retrieves all order items for a specific order")
    public Page<OrderItemDto> getOrderItemsByOrderId(
            @AuthenticationPrincipal User user,
            @PathVariable
            @Positive(message = "ID must be greater than or equal to 1")
            @NotNull
            Long orderId,
            Pageable pageable) {
        return orderService.getAllOrderItemsByOrderId(user.getId(), orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Retrieve a specific order item within an order",
            description = "Retrieves a specific order item within an order")
    public OrderItemDto getOrderItemById(
            @PathVariable
            @Positive(message = "ID must be greater than or equal to 1")
            @NotNull
            Long orderId,
            @PathVariable
            @Positive(message = "ID must be greater than or equal to 1")
            @NotNull
            Long itemId,
            @AuthenticationPrincipal User user) {
        return orderService.getOrderItemById(user.getId(), orderId, itemId);
    }
}
