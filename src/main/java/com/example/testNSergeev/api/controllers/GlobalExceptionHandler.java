package com.example.testNSergeev.api.controllers;

import com.example.testNSergeev.api.exeption.ErrorResponse;
import com.example.testNSergeev.api.exeption.WalletBadRequestException;
import com.example.testNSergeev.api.exeption.WalletNoContentException;
import com.example.testNSergeev.api.exeption.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWalletBadRequestException(WalletBadRequestException exception) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWalletNotFoundException(WalletNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value()
                ,exception.getMessage());
    }

    @ExceptionHandler(WalletNoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleWalletNoContentException(WalletNoContentException exception) {
        return new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage());
    }
}
