package com.arthur.picpay.service;

import com.arthur.picpay.controller.dto.CreateWalletDto;
import com.arthur.picpay.entity.Wallet;
import com.arthur.picpay.exception.WalletDataAlredyExistsException;
import com.arthur.picpay.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet createWallet(CreateWalletDto dto) {

        var walletDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if (walletDb.isPresent()) {
            throw new WalletDataAlredyExistsException();
        }

        return walletRepository.save(dto.toWallet());
    }
}
