package com.example.testNSergeev.usecasses.impl;

import com.example.testNSergeev.api.exeption.WalletBadRequestException;
import com.example.testNSergeev.api.exeption.WalletNotFoundException;
import com.example.testNSergeev.persistence.model.WalletEntity;
import com.example.testNSergeev.persistence.repository.WalletRepository;
import com.example.testNSergeev.usecasses.dto.OperationType;
import com.example.testNSergeev.usecasses.dto.WalletRequestDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;
import com.example.testNSergeev.usecasses.mapper.WalletMapper;
import com.example.testNSergeev.util.WalletTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImpTest {

    @Mock
    private WalletRepository walletRepo;

    @Mock
    private WalletMapper walletMapper;

    @InjectMocks
    private WalletServiceImp walletService;

    private UUID walletId = WalletTestData.WALLET_ID;
    private WalletEntity walletEntity = WalletTestData.getWalletEntity();
    private WalletEntity walletEntityZeroBalance = WalletTestData.getWalletEntityZeroBalance();
    private WalletResponseDto walletResponseDto = WalletTestData.getWalletResponseDto();
    private WalletResponseDto walletResponseZeroBalance = WalletTestData.getWalletResponseZeroBalance();
    private WalletRequestDto walletRequestDto = WalletTestData.getWalletRequestDto();

    @Test
    void methodShouldInsertNewWalletTest() {
        Mockito.when(walletRepo.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        WalletRequestDto newWalletRequest = WalletRequestDto.builder()
                .withWalletId(WalletTestData.WALLET_ID)
                .withBalance(BigDecimal.ZERO)
                .withLastOperation(OperationType.CREATED)
                .withAmountOfOperation(BigDecimal.ZERO)
                .build();

        Mockito.when(walletMapper.fromDtoToEntity(any(WalletRequestDto.class)))
                .thenReturn(walletEntityZeroBalance);
        Mockito.when(walletRepo.save(any(WalletEntity.class)))
                .thenReturn(walletEntityZeroBalance);
        Mockito.when(walletMapper.fromEntityToDto(any(WalletEntity.class)))
                .thenReturn(walletResponseZeroBalance);

        WalletResponseDto insertWalletDto = walletService.insertWallet();

        Assertions.assertNotNull(insertWalletDto);
        Assertions.assertNotNull(insertWalletDto.walletId());
        Assertions.assertEquals(BigDecimal.ZERO, insertWalletDto.balance());
        Assertions.assertEquals(OperationType.CREATED, insertWalletDto.lastOperation());
        Mockito.verify(walletRepo, times(1))
                .save(any(WalletEntity.class));
        Mockito.verify(walletRepo, times(1))
                .findById(any(UUID.class));
    }

    @Test
    void methodShouldThrowExceptionWhenWalletExistsTest() {
        Mockito.when(walletRepo.findById(any(UUID.class)))
                .thenReturn(Optional.ofNullable(walletEntity));
        WalletBadRequestException exception = assertThrows(
                WalletBadRequestException.class,
                () -> walletService.insertWallet());

        Assertions.assertEquals("Wallet already exists. FAIL", exception.getMessage());
        Mockito.verify(walletRepo).findById(any(UUID.class));
        Mockito.verify(walletRepo, never()).save(any(WalletEntity.class));
    }

    @Test
    void methodShouldReturnWalletTest() {
        Mockito.when(walletRepo.findById(walletId))
                .thenReturn(Optional.of(walletEntity));
        Mockito.when(walletMapper.fromEntityToDto(walletEntity))
                .thenReturn(WalletTestData.getWalletResponseDto());

        WalletResponseDto walletResponseDto = walletService.getWallet(walletId);

        Assertions.assertNotNull(walletResponseDto);
        Assertions.assertEquals(walletId, walletResponseDto.walletId());
        Assertions.assertEquals(BigDecimal.valueOf(1000), walletResponseDto.balance());
    }

    @Test
    void methodShouldThrowExceptionWhenWalletNotExistsTest() {
        Mockito.when(walletRepo.findById(walletId))
                .thenReturn(Optional.empty());
        WalletNotFoundException exception = assertThrows(
                WalletNotFoundException.class,
                () -> walletService.getWallet(walletId));

        Assertions.assertEquals("Data with id %s not found".formatted(walletId)
                , exception.getMessage());
        Mockito.verify(walletRepo, times(1)).findById(walletId);
    }

    @Test
    void methodShouldIncreaseBalanceWhenUpdateWalletDepositTest() {
        BigDecimal depositAmount = WalletTestData.AMOUNT_OF_OPERATION;
        BigDecimal expectedBalance = WalletTestData.BALANCE.add(depositAmount);

        WalletEntity updatedWalletEntity = WalletEntity.builder()
                .withWalletId(walletId)
                .withBalance(expectedBalance)
                .withLastOperation(OperationType.DEPOSIT)
                .withAmountOfOperation(depositAmount)
                .build();

        WalletResponseDto expectedResponse = WalletResponseDto.builder()
                .withWalletId(walletId)
                .withBalance(expectedBalance)
                .withLastOperation(OperationType.DEPOSIT)
                .withAmountOfOperation(depositAmount)
                .build();

        Mockito.when(walletRepo.findById(walletId))
                .thenReturn(Optional.of(walletEntity));
        Mockito.when(walletRepo.save(updatedWalletEntity))
                .thenReturn(updatedWalletEntity);
        Mockito.when(walletMapper.fromEntityToDto(updatedWalletEntity))
                .thenReturn(expectedResponse);

        WalletResponseDto updateResponseDto = walletService
                .updateWalletDeposit(walletId, depositAmount);

        Assertions.assertNotNull(updateResponseDto);
        Assertions.assertEquals(walletId, updateResponseDto.walletId());
        Assertions.assertEquals(expectedBalance, updateResponseDto.balance());
        Assertions.assertEquals(OperationType.DEPOSIT, updateResponseDto.lastOperation());
        Assertions.assertEquals(depositAmount, updateResponseDto.amountOfOperation());

        Mockito.verify(walletRepo).findById(walletId);
        Mockito.verify(walletRepo).save(updatedWalletEntity);
        Mockito.verify(walletMapper).fromEntityToDto(updatedWalletEntity);
    }

    @Test
    void methodShouldDecreaseBalanceWhenUpdateWalletWithdrawTest() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(200);
        BigDecimal expectedBalance = WalletTestData.BALANCE.subtract(withdrawAmount);

        WalletEntity updatedWalletEntity = WalletEntity.builder()
                .withWalletId(walletId)
                .withBalance(expectedBalance)
                .withLastOperation(OperationType.WITHDRAW)
                .withAmountOfOperation(withdrawAmount)
                .build();

        WalletResponseDto expectedResponse = WalletResponseDto.builder()
                .withWalletId(walletId)
                .withBalance(expectedBalance)
                .withLastOperation(OperationType.WITHDRAW)
                .withAmountOfOperation(withdrawAmount)
                .build();

        when(walletRepo.findById(walletId))
                .thenReturn(Optional.of(walletEntity));
        when(walletRepo.save(updatedWalletEntity))
                .thenReturn(updatedWalletEntity);
        when(walletMapper.fromEntityToDto(updatedWalletEntity))
                .thenReturn(expectedResponse);

        WalletResponseDto updateResponseDto = walletService.updateWalletWithdraw(walletId, withdrawAmount);

        Assertions.assertNotNull(updateResponseDto);
        Assertions.assertEquals(expectedBalance, updateResponseDto.balance()); // 800
        Assertions.assertEquals(OperationType.WITHDRAW, updateResponseDto.lastOperation());
        Assertions.assertEquals(withdrawAmount, updateResponseDto.amountOfOperation());

        verify(walletRepo).findById(walletId);
        verify(walletRepo).save(updatedWalletEntity);
        verify(walletMapper).fromEntityToDto(updatedWalletEntity);
    }

    @Test
    void methodShouldThrowExceptionWhenUpdateWalletWithdrawTest() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(2000);

        Mockito.when(walletRepo.findById(walletId))
                .thenReturn(Optional.of(walletEntity));

        WalletBadRequestException exception = assertThrows(
                WalletBadRequestException.class,
                () -> walletService.updateWalletWithdraw(walletId, withdrawAmount));

        Assertions.assertEquals("Not enough funds. Id is %s"
                .formatted(walletId), exception.getMessage());

        Mockito.verify(walletRepo, times(1)).findById(walletId);
    }

    @Test
    void methodShouldDeleteWalletTest() {
        Mockito.when(walletRepo.findById(walletId))
                .thenReturn(Optional.of(walletEntity));
        Mockito.doNothing().when(walletRepo).deleteById(walletId);

        walletService.deleteWallet(walletId);

        Mockito.verify(walletRepo, times(1)).findById(walletId);
        Mockito.verify(walletRepo, times(1)).deleteById(walletId);
    }

    @Test
    void methodShouldNotThrowExceptionWhenDeleteWalletTest() {
        Mockito.when(walletRepo.findById(walletId)).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> walletService.deleteWallet(walletId));
        Mockito.verify(walletRepo, times(1)).findById(walletId);
    }
}