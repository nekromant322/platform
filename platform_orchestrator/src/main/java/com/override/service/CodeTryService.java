package com.override.service;

import com.override.mapper.CodeTryMapper;
import com.override.model.CodeTry;
import com.override.repository.CodeTryRepository;
import dto.CodeTryDTO;
import dto.TaskIdentifierDTO;
import dto.TestResultDTO;
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

    public List<CodeTry> findAllByUserId(Long id) {
        return codeTryRepository.findAllByUserIdOrderByDateDesc(id);
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
}
