package com.example.testNSergeev.api.controllers;

import com.example.testNSergeev.usecasses.WalletService;
import com.example.testNSergeev.usecasses.dto.WalletInfoResponseDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;
import com.example.testNSergeev.util.WalletTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private final UUID walletId = WalletTestData.WALLET_ID;
    private final BigDecimal amount = WalletTestData.AMOUNT_OF_OPERATION;

    @Test
    void insertWallet_ShouldReturnCreatedResponse() {
        WalletResponseDto expectedResponse = WalletTestData.getWalletResponseDto();

        Mockito.when(walletService.insertWallet()).thenReturn(expectedResponse);

        ResponseEntity<WalletResponseDto> walletResponse = walletController.insertWallet();

        Assertions.assertNotNull(walletResponse);
        Assertions.assertEquals(HttpStatus.CREATED, walletResponse.getStatusCode());
        Assertions.assertEquals(expectedResponse, walletResponse.getBody());
        Mockito.verify(walletService, times(1)).insertWallet();
    }

    @Test
    void getWalletBalance_ShouldReturnOkResponse() {
        WalletResponseDto expectedResponse = WalletTestData.getWalletResponseDto();

        Mockito.when(walletService.getWallet(walletId)).thenReturn(expectedResponse);

        ResponseEntity<WalletResponseDto> response = walletController.getWalletBalance(walletId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
        Mockito.verify(walletService, times(1)).getWallet(walletId);
    }

    @Test
    void getWalletInfo_ShouldReturnOkResponse() {
        WalletInfoResponseDto expectedResponse = new WalletInfoResponseDto(walletId,
                WalletTestData.LAST_OPERATION,
                WalletTestData.AMOUNT_OF_OPERATION);

        Mockito.when(walletService.getWalletInfo(walletId)).thenReturn(expectedResponse);

        ResponseEntity<WalletInfoResponseDto> response = walletController.getWalletInfo(walletId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
        Mockito.verify(walletService, times(1)).getWalletInfo(walletId);
    }

    @Test
    void updateWalletDeposit_ShouldReturnCreatedResponse() {
        WalletResponseDto expectedResponse = WalletTestData.getWalletResponseDto();

        Mockito.when(walletService.updateWalletDeposit(walletId, amount)).thenReturn(expectedResponse);

        ResponseEntity<WalletResponseDto> response = walletController.updateWalletDeposit(walletId, amount);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
        Mockito.verify(walletService, times(1)).updateWalletDeposit(walletId, amount);
    }

    @Test
    void updateWalletWithdraw_ShouldReturnCreatedResponse() {
        WalletResponseDto expectedResponse = WalletTestData.getWalletResponseDto();

        Mockito.when(walletService.updateWalletWithdraw(walletId, amount)).thenReturn(expectedResponse);

        ResponseEntity<WalletResponseDto> response = walletController.updateWalletWithdraw(walletId, amount);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
        Mockito.verify(walletService, times(1)).updateWalletWithdraw(walletId, amount);
    }

    @Test
    void deleteWallet_ShouldReturnNoContentResponse() {
        Mockito.doNothing().when(walletService).deleteWallet(walletId);

        ResponseEntity<Void> response = walletController.deleteWallet(walletId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        Mockito.verify(walletService, times(1)).deleteWallet(walletId);
    }

    @Test
    void deleteWallet_ShouldCallServiceWithCorrectParameters() {
        Mockito.doNothing().when(walletService).deleteWallet(walletId);

        walletController.deleteWallet(walletId);

        Mockito.verify(walletService, times(1)).deleteWallet(walletId);
    }
}