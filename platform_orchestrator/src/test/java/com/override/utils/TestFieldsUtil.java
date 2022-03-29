package com.override.utils;

import com.override.models.Authority;
import com.override.models.CodeTry;
import com.override.models.PlatformUser;
import dtos.CodeTryDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.CodeExecutionStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestFieldsUtil {

    public static String generateTestCode() {
        return "public class MyClass {public static void main(String[] args){ System.out.println(\"This is my Code\");}}";
    }

    public static PlatformUser generateTestUser() {
        return new PlatformUser(null, "Andrey", "a", "a",
                Collections.singletonList(new Authority(null, "admin")));
    }

    public static TaskIdentifierDTO generateTestTaskIdentifierDTO() {
        return TaskIdentifierDTO.builder()
                .chapter(1)
                .step(1)
                .lesson(1)
                .build();
    }

    public static CodeTry generateTestCodeTry() {
        return new CodeTry(null, generateTestCode(), 1, 1, 1,
                CodeExecutionStatus.OK, LocalDateTime.of(2022, 1, 1, 12, 0),
                generateTestUser());
    }

    public static TestResultDTO generateTestTestResultDTO() {
        return TestResultDTO.builder()
                .codeExecutionStatus(CodeExecutionStatus.OK)
                .output("blabla")
                .build();
    }

    public static CodeTryDTO generateTestCodeTryDTO() {
        return new CodeTryDTO(generateTestTaskIdentifierDTO(), generateTestCode());
    }

    public static List<Object[]> generateListObjects(){
        Object[] obj = new Object[]{"testUser", new BigInteger(Integer.toBinaryString(1))};
        final ArrayList<Object[]> objects = new ArrayList<>();
        objects.add(obj);
        return  objects;
    }

    public static List<Integer[]> generateListInteger(){
        Integer[] obj = new Integer[]{1, 2, 3, 4};
        final ArrayList<Integer[]> objects = new ArrayList<>();
        objects.add(obj);
        return  objects;
    }

    public static List<Long[]> generateListLong(){
        Long[] obj = new Long[]{1L, 2L, 3L, 4L};
        final ArrayList<Long[]> objects = new ArrayList<>();
        objects.add(obj);
        return  objects;
    }
}
