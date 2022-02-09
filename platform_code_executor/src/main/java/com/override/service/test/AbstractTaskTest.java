package com.override.service.test;

import com.override.exception.AssertionError;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.Status;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public abstract class AbstractTaskTest {

    private final double FILE_MAX_SIZE_IN_MB = 16;
    private final String PATH_TO_TEST_CLASS = "customClasses/Main.java";

    @Value("${task-test.defaultTimeout}")
    protected Integer defaultTimeout;

    protected ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public TestResultDTO test(Class mainClass) {
        List<TestResultDTO> testResults = new ArrayList<>();
        testResults.add(getSpaceTestResult());
        for (Callable testCase : getTestCases(mainClass)) {
            Future<TestResultDTO> future = executorService.submit(testCase);

            try {
                TestResultDTO testResult = future.get(getTimeout(), TimeUnit.MILLISECONDS);
                testResults.add(testResult);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof AssertionError) {
                    testResults.add(TestResultDTO.builder()
                            .status(Status.ASSERTION_ERROR)
                            .output(e.getMessage())
                            .build());
                } else {
                    //убрать сообщение, когда будет отлажена проверка кода этим сервисом
                    testResults.add(TestResultDTO.builder()
                            .status(Status.RUNTIME_ERROR)
                            .output((
                                    "Ошибка может быть не связана с вашим кодом, вот её текст, сходите к ментору, если там какая-то лютая " +
                                            "непонятная дичь:\n\n" + e.getMessage()))
                            .build());
                }
            } catch (TimeoutException e) {
                future.cancel(true);
                testResults.add(TestResultDTO.builder()
                        .status(Status.OUT_OF_TIME)
                        .output(e.getMessage())
                        .build());
            } catch (InterruptedException e) {
                testResults.add(TestResultDTO.builder()
                        .status(Status.RUNTIME_ERROR)
                        .output((
                                "Техническая ошибка, обратись к ментору:\n\n" + e.getMessage()))
                        .build());
            }

        }

        return testResults.stream()
                .filter(x -> !x.getStatus().equals(Status.OK))
                .findFirst()
                .orElse(TestResultDTO.builder()
                        .status(Status.OK)
                        .output("")
                        .build());
    }

    public abstract TaskIdentifierDTO getTaskIdentifier();

    protected Integer getTimeout() {
        return defaultTimeout;
    }

    protected abstract Callable[] getTestCases(Class mainClass);


    protected void assertEquals(String message, Object expected, Object actual) {
        if (!equalsRegardingNull(expected, actual)) {
            String cleanMessage = message == null ? "" : message;
            throw new AssertionError(cleanMessage, expected, actual);
        }
    }

    protected void assertTrue(String message, boolean condition) {
        assertEquals(message, condition, true);
    }

    private boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        } else {
            return expected.equals(actual);
        }
    }

    private double getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024);
    }

    private TestResultDTO getSpaceTestResult() {
        File file = new File(PATH_TO_TEST_CLASS);
        try {
            assertTrue("File too fat", FILE_MAX_SIZE_IN_MB >= getFileSizeMegaBytes(file));
            return TestResultDTO.builder().
                    status(Status.OK).
                    output("").
                    build();
        } catch (AssertionError e) {
            return TestResultDTO.builder()
                    .status(Status.OUT_OF_SPACE)
                    .output((
                            "Ваш код занимает слишком много места, нужно его оптимизировать:\n\n" + e.getMessage()))
                    .build();
        }
    }

    public String getCodePrefix() {
        return "import java.util.*;\n" +
                "\n" +
                "public class Main {\n" +
                "    public Main() {\n" +
                "    }\n" +
                "\n" +
                "    //Stepik code: start\n";
    }

    public String getCodePostFix() {
        return "}\n";
    }
}
