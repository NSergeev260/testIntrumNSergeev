package com.example.testNSergeev.util;

import com.example.testNSergeev.persistence.model.WalletEntity;
import com.example.testNSergeev.usecasses.dto.OperationType;
import com.example.testNSergeev.usecasses.dto.WalletRequestDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletTestData {

    public static final UUID WALLET_ID = UUID.fromString("54932c93-3482-446b-b52f-337f71d7a764");
    public static final BigDecimal BALANCE = BigDecimal.valueOf(1000);
    public static final OperationType LAST_OPERATION = OperationType.DEPOSIT;
    public static final BigDecimal AMOUNT_OF_OPERATION = BigDecimal.valueOf(500);

    private WalletTestData() {
    }

    public static WalletRequestDto getWalletRequestDto() {
        return WalletRequestDto.builder()
                .withWalletId(WALLET_ID)
                .withBalance(BALANCE)
                .withLastOperation(LAST_OPERATION)
                .withAmountOfOperation(AMOUNT_OF_OPERATION)
                .build();
    }

    public static WalletResponseDto getWalletResponseDto() {
        return WalletResponseDto.builder()
                .withWalletId(WALLET_ID)
                .withBalance(BALANCE)
                .withLastOperation(LAST_OPERATION)
                .withAmountOfOperation(AMOUNT_OF_OPERATION)
                .build();
    }

    public static WalletEntity getWalletEntity() {
        return WalletEntity.builder()
                .withWalletId(WALLET_ID)
                .withBalance(BALANCE)
                .withLastOperation(LAST_OPERATION)
                .withAmountOfOperation(AMOUNT_OF_OPERATION)
                .build();
    }

    public static WalletEntity getWalletEntityZeroBalance() {
        return WalletEntity.builder()
                .withWalletId(WALLET_ID)
                .withBalance(BigDecimal.ZERO)
                .withLastOperation(OperationType.CREATED)
                .withAmountOfOperation(BigDecimal.ZERO)
                .build();
    }

    public static WalletResponseDto getWalletResponseZeroBalance() {
        return WalletResponseDto.builder()
                .withWalletId(WALLET_ID)
                .withBalance(BigDecimal.ZERO)
                .withLastOperation(OperationType.CREATED)
                .withAmountOfOperation(BigDecimal.ZERO)
                .build();
    }
}
