package com.override.service.test;

import com.override.utils.TestUtils;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotEquals;


@Service
@RequiredArgsConstructor
public class ComplexNumberTaskTest extends AbstractTaskTest {

    private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private static final String MESSAGE_TEMPLATE_HASHCODE = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.hashCode() == b.hashCode()";
    private static final String MESSAGE_TEMPLATE_EQUALS = "\nComplexNumber a = new ComplexNumber(%f, %f);\n" +
            "ComplexNumber b = new ComplexNumber(%f, %f);\n" +
            "a.equals(b)";

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
                () -> testCase1(mainClass),
                () -> testCase2(mainClass),
                () -> testCase3(mainClass),
                () -> testCase4(mainClass)
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

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 1.0, 1.0);
        assertEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 1.0, 1.0);
        assertEquals(message, a, b);

        return TestResultDTO.builder().status(Status.OK).output("").build();
    }

    @SneakyThrows
    public TestResultDTO testCase2(Class mainClass) {
        Class<?> complexNumberClass = TestUtils.getInnerClass(mainClass, "ComplexNumber");

        Constructor<?> constructor = TestUtils.getConstructor(complexNumberClass,
                new int[] {Modifier.PUBLIC, 0},
                mainClass,
                Double.TYPE,
                Double.TYPE);

        Object mainInstance = mainClass.newInstance();
        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 1, 2);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 1.0, 2.0);
        assertNotEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 1.0, 2.0);
        assertNotEquals(message, a, b);

        return TestResultDTO.builder().status(Status.OK).output("").build();
    }

    @SneakyThrows
    public TestResultDTO testCase3(Class mainClass) {
        Class<?> complexNumberClass = TestUtils.getInnerClass(mainClass, "ComplexNumber");

        Constructor<?> constructor = TestUtils.getConstructor(complexNumberClass,
                new int[] {Modifier.PUBLIC, 0},
                mainClass,
                Double.TYPE,
                Double.TYPE);

        Object mainInstance = mainClass.newInstance();
        Object a = TestUtils.newInstance(constructor, mainInstance, 1, 1);
        Object b = TestUtils.newInstance(constructor, mainInstance, 42, 1);

        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 1.0, 1.0, 42.0, 1.0);
        assertNotEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 1.0, 1.0, 42.0, 1.0);
        assertNotEquals(message, a, b);


        return TestResultDTO.builder().status(Status.OK).output("").build();
    }

    @SneakyThrows
    public TestResultDTO testCase4(Class mainClass) {
        Class<?> complexNumberClass = TestUtils.getInnerClass(mainClass, "ComplexNumber");

        Constructor<?> constructor = TestUtils.getConstructor(complexNumberClass,
                new int[] {Modifier.PUBLIC, 0},
                mainClass,
                Double.TYPE,
                Double.TYPE);

        Object mainInstance = mainClass.newInstance();
        Object a = TestUtils.newInstance(constructor, mainInstance, 10.25, 1.69);
        Object b = TestUtils.newInstance(constructor, mainInstance, 10.25, 1.69);


        String message = String.format(MESSAGE_TEMPLATE_HASHCODE, 10.25, 1.69, 10.25, 1.69);
        assertEquals(message, a.hashCode(), b.hashCode());
        message = String.format(MESSAGE_TEMPLATE_EQUALS, 10.25, 1.69, 10.25, 1.69);
        assertEquals(message, a, b);


        return TestResultDTO.builder().status(Status.OK).output("").build();
    }

}
