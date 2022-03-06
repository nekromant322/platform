package com.override.mappers;

import com.override.models.CodeTry;
import com.override.models.PlatformUser;
import dtos.CodeTryDTO;
import dtos.TestResultDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CodeTryMapper {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public CodeTry dtoToEntity(CodeTryDTO codeTryDTO, TestResultDTO testResultDTO, PlatformUser user) {
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
