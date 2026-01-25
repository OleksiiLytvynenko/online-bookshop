package me.krumka.onlinebookshop.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateOrderStatusRequestDto(
        @NotBlank(message = "Status is required")
        @Pattern(
                regexp = "^(PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED)$",
                message = "Status must be one of: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED"
        )
        String status
) {
}
