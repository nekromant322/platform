package com.override.exception;

public class InvalidPersonalDataException extends IllegalArgumentException {
    public InvalidPersonalDataException(String message) {
        super(message);
    }
}
