package com.override.service;

import com.override.model.TaskIdentifier;
import com.override.model.TestResult;
import com.override.service.test.TaskTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExecuteCodeService {

    private Map<TaskIdentifier, TaskTest> testsMap = new HashMap<>();
    private CompilerService compilerService;

    @Autowired
    public ExecuteCodeService(List<TaskTest> taskTests, CompilerService compilerService) {
        taskTests.forEach(x -> testsMap.put(x.getTaskIdentifier(), x));
        this.compilerService = compilerService;
    }


    public TestResult runCode(TaskIdentifier taskIdentifier, String studentsCode) {
        Class loadedClass = compilerService.makeClassFromCode(studentsCode);
        return testsMap.get(taskIdentifier).test(loadedClass);
    }

}
