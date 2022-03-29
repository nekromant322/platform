package com.override.service;

import com.override.mappers.CodeTryMapper;
import com.override.models.CodeTry;
import com.override.repositories.CodeTryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.override.utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class CodeTryServiceTest {
    @InjectMocks
    private CodeTryService codeTryService;
    @Mock
    private CodeTryRepository codeTryRepository;
    @Mock
    private PlatformUserService platformUserService;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private CodeTryMapper codeTryMapper;

    @Test
    void testSaveCodeTry() {
        codeTryService.saveCodeTry(generateTestCodeTryDTO(), generateTestTestResultDTO(), "login");
        verify(codeTryRepository, times(1)).save(Mockito.any(CodeTry.class));
    }

    @Test
    void testFindAllCodes() {
        when(codeTryRepository.findAllByUserLogin("Andrey"))
                .thenReturn(new ArrayList<>(Collections.singletonList(generateTestCodeTry())));

        final List<CodeTry> codeTryList = codeTryService.findAllCodes("Andrey");

        verify(codeTryRepository, times(1)).findAllByUserLogin(Mockito.any());
        assertTrue(codeTryList.contains(generateTestCodeTry()));
    }

    @Test
    void testFindAllByLesson() {

        when(codeTryRepository.findByUserLoginAndChapterAndStepAndLesson("Andrey", 1 , 1, 1))
                .thenReturn(new ArrayList<>(Collections.singletonList(generateTestCodeTry())));

        final List<CodeTry> codeTryList = codeTryService.findAllByLesson("Andrey", generateTestTaskIdentifierDTO());
        verify(codeTryRepository, times(1))
                .findByUserLoginAndChapterAndStepAndLesson("Andrey", 1, 1, 1);
        assertTrue(codeTryList.contains(generateTestCodeTry()));
    }
}
