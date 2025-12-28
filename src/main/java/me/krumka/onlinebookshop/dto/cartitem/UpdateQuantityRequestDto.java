package me.krumka.onlinebookshop.dto.cartitem;

import jakarta.validation.constraints.Positive;

public record UpdateQuantityRequestDto(
        @Positive(message = "Quantity must be greater than or equal to 1")
        Integer quantity
) {
}
