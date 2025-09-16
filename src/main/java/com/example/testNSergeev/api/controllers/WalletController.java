package com.example.testNSergeev.api.controllers;

import com.example.testNSergeev.usecasses.WalletService;
import com.example.testNSergeev.usecasses.dto.WalletInfoResponseDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponseDto> insertWallet() {
        WalletResponseDto walletResponseDto = walletService.insertWallet();
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(walletResponseDto);
    }

    @GetMapping("/{WALLET_UUID}")
    public ResponseEntity<WalletResponseDto> getWalletBalance(@PathVariable("WALLET_UUID")UUID walletId) {
        WalletResponseDto walletResponseDto = walletService.getWallet(walletId);
        return ResponseEntity.
                ok().
                body(walletResponseDto);
    }

    @GetMapping
    public ResponseEntity<WalletInfoResponseDto> getWalletInfo(@RequestParam UUID walletId) {
        WalletInfoResponseDto walletResponseDto = walletService.getWalletInfo(walletId);
        return ResponseEntity.
                ok().
                body(walletResponseDto);
    }

    @PutMapping
    public ResponseEntity<WalletResponseDto> updateWalletDeposit(@RequestParam UUID walletId,
                                                                 @RequestParam BigDecimal amountOfOperation) {
       WalletResponseDto walletResponseDto = walletService.updateWalletDeposit(walletId, amountOfOperation);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(walletResponseDto);
    }

    @PatchMapping
    public ResponseEntity<WalletResponseDto> updateWalletWithdraw(@RequestParam UUID walletId,
                                                                  @RequestParam BigDecimal amountOfOperation) {
        WalletResponseDto walletResponseDto = walletService.updateWalletWithdraw(walletId, amountOfOperation);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(walletResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWallet(@RequestParam UUID walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
