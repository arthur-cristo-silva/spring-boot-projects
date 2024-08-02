package com.arthur.adi.service;

import com.arthur.adi.dto.CreateStockDto;
import com.arthur.adi.entity.Stock;
import com.arthur.adi.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Stock createStock(CreateStockDto dto) {
        return stockRepository.save(new Stock(dto.stock_id(), dto.description()));
    }
}
