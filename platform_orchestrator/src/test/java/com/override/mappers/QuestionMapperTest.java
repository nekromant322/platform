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

import static com.override.utils.TestFieldsUtil.generateTestQuestion;
import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionMapperTest {
    @InjectMocks
    QuestionMapper questionMapper;
    @Mock
    PlatformUserService platformUserService;


    @Test
    public void testDtoToEntity(){
        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());
        Question testQuestion = generateTestQuestion();
        PlatformUser user = generateTestUser();

        Question question = questionMapper.dtoToEntity(QuestionDTO.builder()
                .id(testQuestion.getId())
                .chapter(testQuestion.getChapter())
                .login(user.getLogin())
                .answered(testQuestion.isAnswered())
                .question(testQuestion.getQuestion())
                .build());

        Assertions.assertEquals(question.getId(), testQuestion.getId());
        Assertions.assertEquals(question.getQuestion(), testQuestion.getQuestion());
        Assertions.assertEquals(question.getChapter(), testQuestion.getChapter());
        Assertions.assertEquals(question.getUser(), user);
    }
}
