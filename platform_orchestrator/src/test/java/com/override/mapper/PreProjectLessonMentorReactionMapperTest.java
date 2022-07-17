package com.override.mapper;

import com.override.model.PreProjectLesson;
import com.override.service.PlatformUserService;
import dto.PreProjectLessonMentorReactionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.junit.jupiter.api.Assertions.*;
import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreProjectLessonMentorReactionMapperTest {

    @InjectMocks
    private PreProjectLessonMentorReactionMapper preProjectLessonMentorReactionMapper;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    void dtoToEntity() {
        PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO = generateTestPreProjectLessonMentorReactionDTO();
        preProjectLessonMentorReactionDTO.setComments(null);

        PreProjectLesson preProjectLesson = generateTestPreProjectLesson();
        preProjectLesson.setTaskIdentifier(null);

        when(platformUserService.findPlatformUserByLogin(generateTestUser().getLogin())).thenReturn(generateTestUser());

        assertEquals(preProjectLessonMentorReactionMapper.dtoToEntity(preProjectLessonMentorReactionDTO),preProjectLesson);
    }

    @Test
    void entityToDto() {
        PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO = generateTestPreProjectLessonMentorReactionDTO();
        PreProjectLesson preProjectLesson = generateTestPreProjectLesson();

        assertEquals(preProjectLessonMentorReactionMapper.entityToDto(preProjectLesson), preProjectLessonMentorReactionDTO);
    }


    @Test
    void listEntityToDto() {
        ArrayList<PreProjectLesson> lessonArrayList = new ArrayList<>();
        lessonArrayList.add(generateTestPreProjectLesson());

        ArrayList<PreProjectLessonMentorReactionDTO> dtoArrayList = new ArrayList<>();
        dtoArrayList.add(generateTestPreProjectLessonMentorReactionDTO());

        assertEquals(preProjectLessonMentorReactionMapper.listEntityToDto(lessonArrayList), dtoArrayList);
    }


}