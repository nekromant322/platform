package com.override.service;

import com.override.mapper.CodeTryMapper;
import com.override.model.CodeTry;
import com.override.repository.CodeTryRepository;
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

import static com.override.utils.TestFieldsUtil.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
    void testWhenSaveCodeTry() {
        codeTryService.saveCodeTry(generateTestCodeTryDTO(), generateTestTestResultDTO(), "login");
        verify(codeTryRepository, times(1)).save(Mockito.any(CodeTry.class));
    }

    @Test
    void testWhenFindAllCodes() {
        when(codeTryRepository.findAllByUserLogin("Andrey"))
                .thenReturn(new ArrayList<>(Collections.singletonList(generateTestCodeTry())));

        final List<CodeTry> codeTryList = codeTryService.findAllCodes("Andrey");

        verify(codeTryRepository, times(1)).findAllByUserLogin(Mockito.any());
        assertTrue(codeTryList.contains(generateTestCodeTry()));
    }

    @Test
    void testWhenFindAllByLesson() {

        when(codeTryRepository.findByUserLoginAndChapterAndStepAndLesson("Andrey", 1, 1, 1))
                .thenReturn(new ArrayList<>(Collections.singletonList(generateTestCodeTry())));

        final List<CodeTry> codeTryList = codeTryService.findAllByLesson("Andrey", generateTestTaskIdentifierDTO());
        verify(codeTryRepository, times(1))
                .findByUserLoginAndChapterAndStepAndLesson("Andrey", 1, 1, 1);
        assertTrue(codeTryList.contains(generateTestCodeTry()));
    }
}
