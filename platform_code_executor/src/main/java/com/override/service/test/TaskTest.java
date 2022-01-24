package com.override.service.test;

import com.override.exception.AssertionError;
import com.override.model.TaskIdentifier;
import com.override.model.TestResult;
import org.junit.ComparisonFailure;

import java.util.*;
import java.util.concurrent.Callable;

public interface TaskTest {

    TestResult test (Class mainClass);

    TaskIdentifier getTaskIdentifier();

    Integer getTimeout();

    Callable[] getTestCases(Class mainClass);

    //todo переделать на абстрактный класс, это зашло слишком далеко для интерфейса
    //переделать для удобного тестирования чтоб возвращало TestResult или вообще как-то отдельно вынести
    default  void assertEquals(String message, Object expected, Object actual) {
        if (!equalsRegardingNull(expected, actual)) {
            if (expected instanceof String && actual instanceof String) {
                String cleanMessage = message == null ? "" : message;
                throw new AssertionError(cleanMessage, (String)expected, (String)actual);
            }
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
