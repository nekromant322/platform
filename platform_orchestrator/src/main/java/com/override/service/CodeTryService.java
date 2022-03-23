package com.override.service;

import com.override.mappers.CodeTryMapper;
import com.override.models.CodeTry;
import com.override.repositories.CodeTryRepository;
import dtos.CodeTryDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.CodeExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeTryService {
    @Autowired
    private CodeTryMapper codeTryMapper;
    @Autowired
    private CodeTryRepository codeTryRepository;
    @Autowired
    private PlatformUserService platformUserService;

    public void saveCodeTry(CodeTryDTO codeTryDTO, TestResultDTO testResultDTO, String login) {
        codeTryRepository.save(codeTryMapper.dtoToEntity(codeTryDTO, testResultDTO,
                platformUserService.findPlatformUserByLogin(login)));
    }

    public List<CodeTry> findAllCodes(String login) {
        return codeTryRepository.findAllByUserLogin(login);
    }

    public List<CodeTry> findAllByLesson(String login, TaskIdentifierDTO taskIdentifierDTO) {
        return codeTryRepository.findByUserLoginAndChapterAndStepAndLesson(login, taskIdentifierDTO.getChapter(),
                taskIdentifierDTO.getStep(),
                taskIdentifierDTO.getLesson()
        );
    }

    public List<String> countStatsOfHardTasks() {
        return codeTryRepository.countStatsOfHardTasks();
    }

    public long countStatsByStatus(CodeExecutionStatus codeExecutionStatus){
        return codeTryRepository.countCodeTryByCodeExecutionStatus(codeExecutionStatus);
    }

    public long countStatsByStatusAndUser(CodeExecutionStatus codeExecutionStatus,
                                          String login){
        return codeTryRepository.countCodeTryCodeExecutionStatusAndUser(codeExecutionStatus,
                platformUserService.findPlatformUserByLogin(login));
    }

    public long countStatsByChapterAndStepAndStatus(int chapter, int step,
                                                    CodeExecutionStatus codeExecutionStatus){
        return codeTryRepository.countCodeTryByChapterAndStepAndCodeExecutionStatus(chapter,
                step, codeExecutionStatus);
    }
}
