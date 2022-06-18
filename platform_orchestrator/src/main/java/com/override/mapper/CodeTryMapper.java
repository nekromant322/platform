package com.override.mapper;

import com.override.model.CodeTry;
import com.override.model.PlatformUser;
import dto.CodeTryDTO;
import dto.TestResultDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CodeTryMapper {

    public CodeTry dtoToEntity(CodeTryDTO codeTryDTO, TestResultDTO testResultDTO,
                               PlatformUser user) {
        return new CodeTry(null,
                codeTryDTO.getStudentsCode(),
                codeTryDTO.getTaskIdentifier().getChapter(),
                codeTryDTO.getTaskIdentifier().getStep(),
                codeTryDTO.getTaskIdentifier().getLesson(),
                testResultDTO.getCodeExecutionStatus(),
                LocalDateTime.now(),
                user
        );
    }
}
