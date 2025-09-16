package com.example.testNSergeev.usecasses;
import com.example.testNSergeev.usecasses.dto.WalletInfoResponseDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    WalletResponseDto insertWallet();

    WalletInfoResponseDto getWalletInfo(UUID walletId);

    WalletResponseDto getWallet(UUID walletId);

    WalletResponseDto updateWalletWithdraw(UUID walletId, BigDecimal amountOfOperation);

    WalletResponseDto updateWalletDeposit(UUID walletId, BigDecimal amountOfOperation);

    void deleteWallet(UUID walletId);

}
