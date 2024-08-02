package com.arthur.adi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssociateAccountStockDto(
        @NotBlank String stock_id,
        @NotNull long quantity
) {
}
