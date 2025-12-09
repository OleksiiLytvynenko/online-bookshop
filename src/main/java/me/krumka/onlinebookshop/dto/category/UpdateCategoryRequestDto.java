package me.krumka.onlinebookshop.dto.category;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequestDto(
        @NotBlank String name,
        @NotBlank String description
) {
}
