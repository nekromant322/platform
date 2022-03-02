package com.override.exception;

public class AuthException extends IllegalArgumentException {
    public AuthException(String message) {
        super(message);
    }
}