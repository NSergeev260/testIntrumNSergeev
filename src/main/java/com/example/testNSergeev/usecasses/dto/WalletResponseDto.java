package com.example.testNSergeev.usecasses.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record WalletResponseDto(
        UUID walletId,
        BigDecimal balance,
        OperationType lastOperation,
        BigDecimal amountOfOperation) {
}
