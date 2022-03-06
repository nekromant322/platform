package com.override.service;

import com.override.mappers.CodeTryMapper;
import com.override.models.PlatformUser;
import com.override.models.CodeTry;
import com.override.repositories.CodeTryRepository;
import dtos.CodeTryDTO;
import dtos.TestResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCodeService {
    @Autowired
    CodeTryMapper mapper;
    @Autowired
    private CodeTryRepository codeTryRepository;
    @Autowired
    private PlatformUserService platformUserService;

    public List<CodeTry> findAllStudentCodes(PlatformUser user){
        return codeTryRepository.findAllByUser(user);
    }

    public void saveCodeTry(CodeTryDTO codeTryDTO, TestResultDTO testResultDTO, String login){
        codeTryRepository.save(mapper.dtoToEntity(codeTryDTO, testResultDTO,
                platformUserService.findPlatformUserByLogin(login)));
    }
}
