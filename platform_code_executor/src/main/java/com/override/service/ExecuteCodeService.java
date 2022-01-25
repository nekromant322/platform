package com.override.service;

import com.override.exception.CompilingCodeException;
import com.override.service.test.AbstractTaskTest;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExecuteCodeService {

    private Map<TaskIdentifierDTO, AbstractTaskTest> testsMap = new HashMap<>();
    private CompilerService compilerService;

    @Autowired
    public ExecuteCodeService(List<AbstractTaskTest> taskTests, CompilerService compilerService) {
        taskTests.forEach(x -> testsMap.put(x.getTaskIdentifier(), x));
        this.compilerService = compilerService;
    }


    public TestResultDTO runCode(TaskIdentifierDTO taskIdentifierDTO, String studentsCode) {
        try {
            Class loadedClass = compilerService.makeClassFromCode(studentsCode);
            return testsMap.get(taskIdentifierDTO).test(loadedClass);
        } catch (CompilingCodeException e) {
            return TestResultDTO.builder()
                    .status(Status.COMPILE_ERROR)
                    .output(e.getMessage())
                    .build();
        }


    }

}
