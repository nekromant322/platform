package com.override.service.test;

import com.override.exception.AssertionError;
import com.override.model.TaskIdentifier;
import com.override.model.TestResult;

import java.util.concurrent.Callable;

public interface TaskTest {

    TestResult test(Class mainClass);

    TaskIdentifier getTaskIdentifier();

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
