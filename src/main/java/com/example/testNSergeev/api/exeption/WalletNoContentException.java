package com.example.testNSergeev.api.exeption;

public class WalletNoContentException extends RuntimeException {
    public WalletNoContentException(String message) {
        super(message);
    }
}
