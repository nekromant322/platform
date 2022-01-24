package com.override.service.test;

import com.override.exception.AssertionError;
import com.override.exception.TooLongCodeExecutionException;
import com.override.model.Status;
import com.override.model.TaskIdentifier;
import com.override.model.TestResult;
import com.override.utils.TestUtils;
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

    private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private static final String MESSAGE_TEMPLATE_HASHCODE = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.hashCode() == b.hashCode()";
    private static final String MESSAGE_TEMPLATE_EQUALS = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.equals(b)";


    @Override
    public TestResult test(Class mainClass) {
        List<TestResult> testResults = new ArrayList<>();
        for (Callable testCase : getTestCases(mainClass)) {
            Future<TestResult> future = executorService.submit(testCase);
            try {
                TestResult testResult = future.get(getTimeout(), TimeUnit.MILLISECONDS);
                testResults.add(testResult);
            } catch (TimeoutException e) {
                future.cancel(true);
                throw new TooLongCodeExecutionException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }


        return testResults.get(0);
    }

    @Override
    public TaskIdentifier getTaskIdentifier() {
        return TaskIdentifier.builder()
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
    public TestResult testCase1(Class mainClass) {
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
            return TestResult.builder().status(Status.ASSERTION_ERROR).output(e.getMessage()).build();
        }


        return TestResult.builder().status(Status.OK).output("").build();
    }
}
