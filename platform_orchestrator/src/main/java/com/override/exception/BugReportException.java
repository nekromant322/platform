package com.override.exception;

public class BugReportException extends  IllegalArgumentException {
    public BugReportException(String message) {
        super(message);
    }
}
