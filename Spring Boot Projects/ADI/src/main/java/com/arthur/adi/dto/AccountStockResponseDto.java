package com.arthur.adi.dto;

public record AccountStockResponseDto(
        String stock_id,
        long quantity,
        double total
) {
}
