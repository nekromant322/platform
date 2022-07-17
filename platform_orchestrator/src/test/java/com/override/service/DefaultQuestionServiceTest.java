package com.override.service;

import com.override.model.DefaultQuestion;
import com.override.repository.DefaultQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestDefaultQuestion;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DefaultQuestionServiceTest {

    @InjectMocks
    private DefaultQuestionService defaultquestionService;
    @Mock
    private DefaultQuestionRepository defaultQuestionRepository;

    @Test
    public void testWhenSave() {
        final DefaultQuestion defaultQuestion = generateTestDefaultQuestion();

        defaultquestionService.save(defaultQuestion);
        verify(defaultQuestionRepository, times(1)).save(any());
    }

    @Test
    public void testWhenFindAll() {
        defaultquestionService.findAll();
        verify(defaultQuestionRepository, times(1)).findAll();
    }

    @Test
    public void testWhenFindAllByChapterAndSection() {
        defaultquestionService.findAllByChapterAndSection("core", 3);
        verify(defaultQuestionRepository, times(1)).findDefaultQuestionsByChapterAndSection("core", 3);
    }

    @Test
    public void testWhenDelete() {
        defaultquestionService.delete(1L);
        verify(defaultQuestionRepository, times(1)).deleteById(1L);
    }
}
