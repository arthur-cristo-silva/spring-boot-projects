package com.arthur.picpay.service;

import com.arthur.picpay.controller.dto.TransferDto;
import com.arthur.picpay.entity.Transfer;
import com.arthur.picpay.entity.Wallet;
import com.arthur.picpay.exception.InsufficientBalanceException;
import com.arthur.picpay.exception.TransferNotAllowedForWalletTypeException;
import com.arthur.picpay.exception.TransferNotAuthorizedException;
import com.arthur.picpay.exception.WalletNotFoundException;
import com.arthur.picpay.repository.TransferRepository;
import com.arthur.picpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    @Transactional
    public Transfer transfer(TransferDto dto) {

        var sender = walletRepository.findById(dto.payer())
                .orElseThrow(() -> new WalletNotFoundException(dto.payer()));
        var receiver = walletRepository.findById(dto.payee())
                .orElseThrow(() -> new WalletNotFoundException(dto.payee()));

        validateTransfer(dto, sender);

        sender.debit(dto.value());
        receiver.credit(dto.value());

        var transfer = new Transfer(dto.value(), sender, receiver);

        var transferResult = transferRepository.save(transfer);
        walletRepository.save(sender);
        walletRepository.save(receiver);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(TransferDto dto, Wallet sender) {
        if (!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }
        if (!sender.isBalancerBiggerThan(dto.value())) {
            throw new InsufficientBalanceException();
        }
        if (!authorizationService.isAuthorized(dto)) {
            throw new TransferNotAuthorizedException();
        }
    }
}
