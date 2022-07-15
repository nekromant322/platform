package com.override.mapper;

import com.override.model.PreProjectComments;
import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import dto.PreProjectLessonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
