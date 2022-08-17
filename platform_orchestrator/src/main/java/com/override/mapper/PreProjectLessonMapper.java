package com.override.mapper;

import com.override.model.PreProjectLesson;
import dto.PreProjectLessonDTO;
import org.springframework.stereotype.Component;

@Component
public class PreProjectLessonMapper {

    public PreProjectLesson dtoToEntity(PreProjectLessonDTO preProjectLessonDTO) {
        return PreProjectLesson.builder()
                .taskIdentifier(preProjectLessonDTO.getTaskIdentifier())
                .link(preProjectLessonDTO.getLink())
                .build();
    }

    public PreProjectLessonDTO entityToDto(PreProjectLesson preProjectLesson) {
        return PreProjectLessonDTO.builder()
                .taskIdentifier(preProjectLesson.getTaskIdentifier())
                .link(preProjectLesson.getLink())
                .build();
    }
}
