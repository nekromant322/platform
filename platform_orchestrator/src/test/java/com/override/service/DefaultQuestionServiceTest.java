package com.override.service;

import com.override.mapper.QuestionMapper;
import com.override.model.DefaultQuestion;
import com.override.model.PlatformUser;
import com.override.model.Question;
import com.override.repository.DefaultQuestionRepository;
import com.override.repository.QuestionRepository;
import dto.QuestionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.override.utils.TestFieldsUtil.*;
import static com.override.utils.TestFieldsUtil.generateTestQuestionDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DefaultQuestionServiceTest {

    @InjectMocks
    private DefaultQuestionService defaultquestionService;
    @Mock
    private DefaultQuestionRepository defaultQuestionRepository;

    @Test
    void save() {
        final DefaultQuestion defaultQuestion = generateTestDefaultQuestion();

        defaultquestionService.save(defaultQuestion);
        verify(defaultQuestionRepository, times(1)).save(any());
    }

    @Test
    void findAll() {
        defaultquestionService.findAll();
        verify(defaultQuestionRepository,times(1)).findAll();
    }
    @Test
    void findAllByChapterAndSection() {
        defaultquestionService.findAllByChapterAndSection("core", 3);
        verify(defaultQuestionRepository,times(1)).findDefaultQuestionsByChapterAndSection("core", 3);
    }

    @Test
    void delete() {
        defaultquestionService.delete(1L);
        verify(defaultQuestionRepository,times(1)).deleteById(1L);
    }
}
