package com.example.testNSergeev.api.exeption;

public class WalletBadRequestException extends RuntimeException {
    public WalletBadRequestException(String message) {
        super(message);
    }
}
