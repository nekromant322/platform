package com.override.service;

import com.override.exception.CompilingCodeException;
import com.override.service.test.AbstractTaskTest;
import dto.*;
import enums.CodeExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            AbstractTaskTest taskTest = testsMap.get(taskIdentifierDTO);
            Class loadedClass = compilerService.makeClassFromCode(taskTest.getCodePrefix() + studentsCode + taskTest.getCodePostFix());
            return taskTest.test(loadedClass);
        } catch (CompilingCodeException e) {
            return TestResultDTO.builder()
                    .codeExecutionStatus(CodeExecutionStatus.COMPILE_ERROR)
                    .output(e.getMessage())
                    .build();
        }
    }
}
