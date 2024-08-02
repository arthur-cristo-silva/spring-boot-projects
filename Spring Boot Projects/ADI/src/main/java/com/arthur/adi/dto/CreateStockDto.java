package com.arthur.adi.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStockDto(
        @NotBlank String stock_id,
        @NotBlank String description
) {
}
