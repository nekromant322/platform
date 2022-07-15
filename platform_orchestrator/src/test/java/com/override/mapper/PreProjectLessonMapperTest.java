package com.override.mapper;

import com.override.model.PreProjectLesson;
import dto.PreProjectLessonDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


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
        PreProjectLesson preProjectLesson = PreProjectLesson.builder()
                .link(preProjectLessonDTO.getLink())
                .taskIdentifier(preProjectLessonDTO.getTaskIdentifier())
                .build();

        assertEquals(preProjectLessonMapper.dtoToEntity(preProjectLessonDTO),preProjectLesson);
    }

    @Test
    void entityToDto() {
        PreProjectLessonDTO preProjectLessonDTO = generateTestPreProjectLessonDTO();
        PreProjectLesson preProjectLesson = generateTestPreProjectLesson();

        assertEquals(preProjectLessonMapper.entityToDto(preProjectLesson), preProjectLessonDTO);
    }

}