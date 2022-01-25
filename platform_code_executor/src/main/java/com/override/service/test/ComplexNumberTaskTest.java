package com.override.service.test;

import com.override.exception.AssertionError;
import com.override.exception.TooLongCodeExecutionException;
import com.override.utils.TestUtils;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Service
@RequiredArgsConstructor
public class ComplexNumberTaskTest implements TaskTest {

    private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private static final String MESSAGE_TEMPLATE_HASHCODE = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.hashCode() == b.hashCode()";
    private static final String MESSAGE_TEMPLATE_EQUALS = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.equals(b)";


    @Override
    public TestResultDTO test(Class mainClass) {
        List<TestResultDTO> testResultDTOS = new ArrayList<>();
        for (Callable testCase : getTestCases(mainClass)) {
            Future<TestResultDTO> future = executorService.submit(testCase);
            try {
                TestResultDTO testResultDTO = future.get(getTimeout(), TimeUnit.MILLISECONDS);
                testResultDTOS.add(testResultDTO);
            } catch (TimeoutException e) {
                future.cancel(true);
                throw new TooLongCodeExecutionException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }


        return testResultDTOS.get(0);
    }

    @Override
    public TaskIdentifierDTO getTaskIdentifier() {
        return TaskIdentifierDTO.builder()
                .chapter(3)
                .step(4)
                .lesson(8)
                .build();
    }

    @Override
    public Integer getTimeout() {
        return 8000;
    }

    @Override
    public Callable[] getTestCases(Class mainClass) {
        return new Callable[] {
                () -> testCase1(mainClass)
        };
    }

    @SneakyThrows
    public TestResultDTO testCase1(Class mainClass) {
        Class<?> complexNumberClass = TestUtils.getInnerClass(mainClass, "ComplexNumber");

        Constructor<?> constructor = TestUtils.getConstructor(complexNumberClass,
                new int[] {Modifier.PUBLIC, 0},
                mainClass,
                Double.TYPE,
                Double.TYPE);


        Object mainInstance = mainClass.newInstance();

        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 1, 1);

        try {
            String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 1.0, 1.0);
            assertEquals(message, a.hashCode(), b.hashCode());
            message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 1.0, 1.0);
            assertEquals(message, a, b);
        } catch (AssertionError e) {
            return TestResultDTO.builder().status(Status.ASSERTION_ERROR).output(e.getMessage()).build();
        }


        return TestResultDTO.builder().status(Status.OK).output("").build();
    }
}
