package com.override.mappers;

import com.override.models.PlatformUser;
import com.override.models.Question;
import com.override.service.PlatformUserService;
import dtos.QuestionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionMapperTest {
    @InjectMocks
    private QuestionMapper questionMapper;

    @Test
    public void testDtoToEntity(){
        Question testQuestion = generateTestQuestion();
        PlatformUser user = generateTestUser();
        QuestionDTO questionDTO = generateTestQuestionDTO();

        Question question = questionMapper.dtoToEntity(questionDTO, user);

        Assertions.assertEquals(question.getId(), testQuestion.getId());
        Assertions.assertEquals(question.getQuestion(), testQuestion.getQuestion());
        Assertions.assertEquals(question.getChapter(), testQuestion.getChapter());
        Assertions.assertEquals(question.getUser(), user);
    }
}
