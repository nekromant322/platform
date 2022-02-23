package com.override.exception;

public class UserAlreadyExistException extends IllegalArgumentException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}