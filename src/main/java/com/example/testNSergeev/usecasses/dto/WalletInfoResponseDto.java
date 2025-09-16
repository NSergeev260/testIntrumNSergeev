package com.example.testNSergeev.usecasses.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record WalletInfoResponseDto(
        UUID walletId,
        OperationType lastOperation,
        BigDecimal amountOfOperation) {
}