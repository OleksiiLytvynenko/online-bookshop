package me.krumka.onlinebookshop.dto.cartitem;

import jakarta.validation.constraints.Min;

public record CartItemRequestDto(
        @Min(value = 1, message = "ID must be greater than or equal to 1")
        Long bookId,
        @Min(value = 1, message = "Quantity must be greater than or equal to 1")
        Integer quantity
) {
}
