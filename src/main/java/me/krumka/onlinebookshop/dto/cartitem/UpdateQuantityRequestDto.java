package me.krumka.onlinebookshop.dto.cartitem;

import jakarta.validation.constraints.Min;

public record UpdateQuantityRequestDto(
        @Min(value = 1, message = "Quantity must be greater than or equal to 1")
        Integer quantity
) {
}
