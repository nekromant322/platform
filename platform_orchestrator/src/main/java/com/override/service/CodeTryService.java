package com.override.service;

import com.override.mappers.CodeTryMapper;
import com.override.models.CodeTry;
import com.override.repositories.CodeTryRepository;
import dtos.CodeTryDTO;
import dtos.TestResultDTO;
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

    public List<CodeTry> findAllByLesson(String login, Integer chapter, Integer step, Integer lesson) {
        return codeTryRepository.findByUserLoginAndChapterAndStepAndLesson(login, chapter, step, lesson);
    }
}
