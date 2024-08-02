package com.arthur.adi.service;

import com.arthur.adi.client.BrapiClient;
import com.arthur.adi.dto.AccountResponseDto;
import com.arthur.adi.dto.AccountStockResponseDto;
import com.arthur.adi.dto.AssociateAccountStockDto;
import com.arthur.adi.dto.CreateAccountDto;
import com.arthur.adi.entity.Account;
import com.arthur.adi.entity.AccountStock;
import com.arthur.adi.entity.AccountStockId;
import com.arthur.adi.entity.BillingAddress;
import com.arthur.adi.repository.AccountRepository;
import com.arthur.adi.repository.AccountStockRepository;
import com.arthur.adi.repository.BillingAddressRepository;
import com.arthur.adi.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Value("#{environment.BRAPI_TOKEN}")
    private String BRAPI_TOKEN;
    private final BrapiClient brapiClient;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final UserService userService;

    public Account createAccount(String userId, CreateAccountDto accountDto) {
        var user = userService.getUserById(userId);
        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                accountDto.description(),
                new ArrayList<>()
        );
        var createdAccount = accountRepository.save(account);
        var billingAddress = new BillingAddress(
                account.getId(),
                account,
                accountDto.street(),
                accountDto.number()
        );
        billingAddressRepository.save(billingAddress);
        return createdAccount;
    }


    public List<AccountResponseDto> getAllAccounts(String id) {
        var user = userService.getUserById(id);
        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDto(ac.getId().toString(), ac.getDescription()))
                .toList();
    }

    public void associateStocks(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(dto.stock_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var id = new AccountStockId(account.getId(), stock.getStockId());
        var entity = new AccountStock(id, account, stock, dto.quantity());
        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String id) {
        var account = accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStockResponseDto(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotalPrice(as.getQuantity(), as.getStock().getStockId())))
                .toList();
    }

    private double getTotalPrice(Long quantity, String stockId) {
        var response = brapiClient.getQuote(BRAPI_TOKEN, stockId);
        var price = response.results().getFirst().regularMarketPrice();
        return price * quantity;
    }
}
