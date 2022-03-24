package com.override.service;

import com.override.mappers.CodeTryMapper;
import com.override.models.CodeTry;
import com.override.repositories.CodeTryRepository;
import dtos.CodeTryDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Long> countStatsOfHardTasks(int size) {
        Map<String, Long> mapOfHardTasks = new HashMap<>();
        for (Integer[] obj : codeTryRepository.countStatsOfHardTasks(size)) {
            mapOfHardTasks.put(obj[0] + "." + obj[1] + "." + obj[2], (long) obj[3]);
        }
        return mapOfHardTasks;
    }

    public Map<String, BigInteger> countStatsByStatus() {
        Map<String, BigInteger> mapOfStatus = new HashMap<>();
        for(Object[] obj: codeTryRepository.countStatsOfStatus()){
            mapOfStatus.put((String) obj[0], (BigInteger) obj[1]);
        }
        return mapOfStatus;
    }

    public Map<String, BigInteger> countStatsByStatusAndUser() {
        Map<String, BigInteger> mapOfUsers = new HashMap<>();
        for(Object[] obj : codeTryRepository.countStatsOfUsers()){
            mapOfUsers.put((String) obj[0], (BigInteger) obj[1]);
        }
        return mapOfUsers;
    }

    public Map<TaskIdentifierDTO, Integer> countStatsByChapterAndStepAndStatus() {
        Map<TaskIdentifierDTO, Integer> mapOfAllTries = new HashMap<>();
        for (Integer[] integers : codeTryRepository.countCodeTryByChapterAndStep()) {
            mapOfAllTries.put(TaskIdentifierDTO.builder()
                    .chapter(integers[0])
                    .step(integers[1])
                    .lesson(0)
                    .build(), integers[2]);
        }
        return mapOfAllTries;
    }
}
