package com.override.exception;

public class AssertionError extends RuntimeException {
    private String fExpected;
    private String fActual;
    public AssertionError(String message, String expected, String actual) {
        super(message);
        this.fExpected = expected;
        this.fActual = actual;
    }

}
