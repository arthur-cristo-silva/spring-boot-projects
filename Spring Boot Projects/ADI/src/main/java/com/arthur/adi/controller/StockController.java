package com.arthur.adi.controller;

import com.arthur.adi.dto.CreateStockDto;
import com.arthur.adi.entity.Stock;
import com.arthur.adi.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody @Valid CreateStockDto stock) {
        return ResponseEntity.ok(stockService.createStock(stock));
    }
}
