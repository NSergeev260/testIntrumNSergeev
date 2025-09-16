package com.example.testNSergeev.api.exeption;

import java.time.ZonedDateTime;

public record ErrorResponse(
        int statusCode,
        String message) {
}