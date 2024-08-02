package com.arthur.adi.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountDto(
        @NotBlank String description,
        @NotBlank String street,
        @NotBlank String number
) {
}
