package com.override.exception;

public class AssertionError extends RuntimeException {
    private Object fExpected;
    private Object fActual;
    public AssertionError(String message, Object expected, Object actual) {
        super(message);
        this.fExpected = expected;
        this.fActual = actual;
    }

}
