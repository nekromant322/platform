package com.override.service.test;

import com.override.exception.AssertionError;
import dto.TaskIdentifierDTO;
import dto.TestResultDTO;
import enums.CodeExecutionStatus;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${task-test.defaultTimeout}")
    protected Integer defaultTimeout;

    protected ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public TestResultDTO test(Class mainClass) {
        List<TestResultDTO> testResults = new ArrayList<>();
        for (Callable testCase : getTestCases(mainClass)) {
            Future<TestResultDTO> future = executorService.submit(testCase);

            try {
                TestResultDTO testResult = future.get(getTimeout(), TimeUnit.MILLISECONDS);
                testResults.add(testResult);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof AssertionError) {
                    testResults.add(TestResultDTO.builder()
                            .codeExecutionStatus(CodeExecutionStatus.ASSERTION_ERROR)
                            .output(e.getMessage())
                            .build());
                } else {
                    //убрать сообщение, когда будет отлажена проверка кода этим сервисом
                    testResults.add(TestResultDTO.builder()
                            .codeExecutionStatus(CodeExecutionStatus.RUNTIME_ERROR)
                            .output((
                                    "Ошибка может быть не связана с вашим кодом, вот её текст, сходите к ментору, если там какая-то лютая " +
                                            "непонятная дичь:\n\n" + e.getMessage()))
                            .build());
                }
            } catch (TimeoutException e) {
                future.cancel(true);
                testResults.add(TestResultDTO.builder()
                        .codeExecutionStatus(CodeExecutionStatus.OUT_OF_TIME)
                        .output(e.getMessage())
                        .build());
            } catch (InterruptedException e) {
                testResults.add(TestResultDTO.builder()
                        .codeExecutionStatus(CodeExecutionStatus.RUNTIME_ERROR)
                        .output((
                                "Техническая ошибка, обратись к ментору:\n\n" + e.getMessage()))
                        .build());
            }

        }

        return testResults.stream()
                .filter(x -> !x.getCodeExecutionStatus().equals(CodeExecutionStatus.OK))
                .findFirst()
                .orElse(TestResultDTO.builder()
                        .codeExecutionStatus(CodeExecutionStatus.OK)
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

    private boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        } else {
            return expected.equals(actual);
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
