package me.krumka.onlinebookshop.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequestDto(
        @Positive(message = "ID must be greater than or equal to 1")
        @NotNull
        Long bookId,
        @Positive(message = "Quantity must be greater than or equal to 1")
        Integer quantity
) {
}
