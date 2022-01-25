package com.override.service.test;

import com.override.exception.AssertionError;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;

import java.util.concurrent.Callable;

public interface TaskTest {

    TestResultDTO test(Class mainClass);

    TaskIdentifierDTO getTaskIdentifier();

    Integer getTimeout();

    Callable[] getTestCases(Class mainClass);


    //TODO переделать в абстрактный класс
    default void assertEquals(String message, Object expected, Object actual) {
        if (!equalsRegardingNull(expected, actual)) {
            String cleanMessage = message == null ? "" : message;
            throw new AssertionError(cleanMessage, expected, actual);
        }
    }

    default boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        } else {
            return expected.equals(actual);
        }
    }
}
