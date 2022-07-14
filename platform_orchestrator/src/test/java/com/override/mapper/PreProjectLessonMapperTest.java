package com.override.mapper;

import com.override.model.PreProjectLesson;
import dto.PreProjectLessonDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.override.utils.TestFieldsUtil.generateTestPreProjectLesson;
import static com.override.utils.TestFieldsUtil.generateTestPreProjectLessonDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PreProjectLessonMapperTest {

    @InjectMocks
    PreProjectLessonMapper preProjectLessonMapper;

    @Test
    void dtoToEntity() {
        PreProjectLessonDTO preProjectLessonDTO = generateTestPreProjectLessonDTO();
        preProjectLessonDTO.setComments(null);
        PreProjectLesson preProjectLesson = generateTestPreProjectLesson();
        preProjectLesson.setUser(null);

        assertEquals(preProjectLessonMapper.dtoToEntity(preProjectLessonDTO), preProjectLesson);
    }

    @Test
    void entityToDto() {
        PreProjectLessonDTO preProjectLessonDTO = generateTestPreProjectLessonDTO();
        PreProjectLesson preProjectLesson = generateTestPreProjectLesson();

        assertEquals(preProjectLessonMapper.entityToDto(preProjectLesson), preProjectLessonDTO);
    }

    @Test
    void listEntityToDto() {
        ArrayList<PreProjectLesson> lessonArrayList = new ArrayList<>();
        lessonArrayList.add(generateTestPreProjectLesson());

        ArrayList<PreProjectLessonDTO> dtoArrayList = new ArrayList<>();
        dtoArrayList.add(generateTestPreProjectLessonDTO());

        assertEquals(preProjectLessonMapper.listEntityToDto(lessonArrayList), dtoArrayList);
    }
}