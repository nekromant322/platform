package com.override.service.test;

import com.override.exception.ExecutingCodeException;
import com.override.utils.TestUtils;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;

@Component
@Slf4j
public class HelloWorldTaskTest extends AbstractTaskTest {

    private final String ERROR_MESSAGE = "Output doesn't match, your output:\n %s";
    private final String EXPECTED_OUTPUT = "It's alive! It's alive!";

    @Override
    public TaskIdentifierDTO getTaskIdentifier() {
        return TaskIdentifierDTO.builder()
                .chapter(1)
                .step(2)
                .lesson(13)
                .build();
    }

    @Override
    protected Callable[] getTestCases(Class mainClass) {
        return new Callable[] {
                () -> testCase1(mainClass)
        };
    }

    private TestResultDTO testCase1(Class mainClass) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(out)) {
            System.setOut(printStream);
            Method main = TestUtils.getMethod(mainClass, "main",
                    new int[] {Modifier.PUBLIC | Modifier.STATIC | Modifier.TRANSIENT, Modifier.PUBLIC | Modifier.STATIC},
                    Void.TYPE,
                    String[].class);

            TestUtils.invokeMethod(mainClass, main, (Object) new String[0]);
            assertEquals(String.format(ERROR_MESSAGE, out.toString()), EXPECTED_OUTPUT, out.toString());

        } catch (Throwable throwable) {
            log.error("Error executing testcase");
            throw new ExecutingCodeException(throwable);
        }
        return TestResultDTO.builder().
                status(Status.OK).
                output("").
                build();

    }

    @Override
    public String getCodePrefix() {
        return "";
    }

    @Override
    public String getCodePostFix() {
        return "";
    }
}
