package com.override.service;

import com.override.service.test.TaskTest;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExecuteCodeService {

    private Map<TaskIdentifierDTO, TaskTest> testsMap = new HashMap<>();
    private CompilerService compilerService;

    @Autowired
    public ExecuteCodeService(List<TaskTest> taskTests, CompilerService compilerService) {
        taskTests.forEach(x -> testsMap.put(x.getTaskIdentifier(), x));
        this.compilerService = compilerService;
    }


    public TestResultDTO runCode(TaskIdentifierDTO taskIdentifierDTO, String studentsCode) {
        Class loadedClass = compilerService.makeClassFromCode(studentsCode);
        return testsMap.get(taskIdentifierDTO).test(loadedClass);
    }

}
