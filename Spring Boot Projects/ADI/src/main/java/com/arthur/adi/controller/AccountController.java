package com.arthur.adi.controller;

import com.arthur.adi.dto.AccountStockResponseDto;
import com.arthur.adi.dto.AssociateAccountStockDto;
import com.arthur.adi.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("{id}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable String id, @RequestBody @Valid AssociateAccountStockDto dto) {
        accountService.associateStocks(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listStocks(@PathVariable String id) {
        var stocks = accountService.listStocks(id);
        return ResponseEntity.ok(stocks);
    }
}
