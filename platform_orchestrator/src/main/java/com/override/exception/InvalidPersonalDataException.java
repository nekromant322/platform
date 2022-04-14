package com.override.exception;

public class InvalidPersonalDataException extends NullPointerException {
    public InvalidPersonalDataException(String message) {
        super(message);
    }
}
