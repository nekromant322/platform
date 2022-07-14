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

    @Autowired
    private PreProjectLessonRepository preProjectLessonRepository;

    public PreProjectLesson dtoToEntity(PreProjectLessonDTO preProjectLessonDTO) {
        return PreProjectLesson.builder()
                .id(preProjectLessonDTO.getId())
                .taskIdentifier(preProjectLessonDTO.getTaskIdentifier())
                .approve(preProjectLessonDTO.isApprove())
                .link(preProjectLessonDTO.getLink())
                .viewed(preProjectLessonDTO.isViewed())
                .comments(listStringToComments(preProjectLessonDTO))
                .build();
    }

    public PreProjectLessonDTO entityToDto(PreProjectLesson preProjectLesson) {
        return PreProjectLessonDTO.builder()
                .id(preProjectLesson.getId())
                .approve(preProjectLesson.isApprove())
                .viewed(preProjectLesson.isViewed())
                .login(preProjectLesson.getUser().getLogin())
                .comments(toListStringComments(preProjectLesson))
                .taskIdentifier(preProjectLesson.getTaskIdentifier())
                .link(preProjectLesson.getLink())
                .build();
    }

    public List<PreProjectLessonDTO> listEntityToDto(List<PreProjectLesson> preProjectLessonList) {
        List<PreProjectLessonDTO> preProjectLessonDTOList = new ArrayList<>();
        for (int i = 0; i < preProjectLessonList.size(); i++) {
            preProjectLessonDTOList.add(entityToDto(preProjectLessonList.get(i)));
        }
        return preProjectLessonDTOList;
    }

    private List<String> toListStringComments(PreProjectLesson preProjectLesson) {
        List<String> commentsList = new ArrayList<>();
        if (preProjectLesson.getComments() != null) {
            for (int i = 0; i < preProjectLesson.getComments().size(); i++) {
                commentsList.add(preProjectLesson.getComments().get(i).getComment());
            }
        }
        return commentsList;
    }

    private List<PreProjectComments> listStringToComments(PreProjectLessonDTO preProjectLessonDTO) {
        List<String> stringList = preProjectLessonDTO.getComments();
        List<PreProjectComments> CommentsList = new ArrayList<>();

        if (stringList != null) {
            PreProjectComments preProjectComments = PreProjectComments.builder()
                    .comment(stringList.get(stringList.size() - 1))
                    .build();

            CommentsList = preProjectLessonRepository.findById(preProjectLessonDTO
                    .getId()).get().getComments();

            if (CommentsList.isEmpty()) {
                CommentsList.add(preProjectComments);
            } else if (!CommentsList.get(CommentsList.size() - 1).getComment().equals(preProjectComments.getComment())) {
                CommentsList.add(preProjectComments);
            }
        }
        return CommentsList;
    }
}
