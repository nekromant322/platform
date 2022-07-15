package com.override.mapper;

import com.override.model.PlatformUser;
import com.override.model.PreProjectComments;
import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import com.override.service.PlatformUserService;
import dto.PreProjectLessonMentorReactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PreProjectLessonMentorReactionMapper {

    @Autowired
    PreProjectLessonRepository preProjectLessonRepository;

    @Autowired
    PlatformUserService platformUserService;

    public PreProjectLesson dtoToEntity(PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        return PreProjectLesson.builder()
                .id(preProjectLessonMentorReactionDTO.getId())
                .user(platformUserService.findPlatformUserByLogin(preProjectLessonMentorReactionDTO.getLogin()))
                .comments(listStringToComments(preProjectLessonMentorReactionDTO))
                .approve(preProjectLessonMentorReactionDTO.isApprove())
                .link(preProjectLessonMentorReactionDTO.getLink())
                .viewed(preProjectLessonMentorReactionDTO.isViewed())
                .build();
    }

    public PreProjectLessonMentorReactionDTO entityToDto(PreProjectLesson preProjectLesson) {
        return PreProjectLessonMentorReactionDTO.builder()
                .id(preProjectLesson.getId())
                .login(preProjectLesson.getUser().getLogin())
                .comments(toListStringComments(preProjectLesson))
                .link(preProjectLesson.getLink())
                .viewed(preProjectLesson.isViewed())
                .approve(preProjectLesson.isApprove())
                .build();
    }

    public List<PreProjectLessonMentorReactionDTO> listEntityToDto(List<PreProjectLesson> preProjectLessonList) {
        List<PreProjectLessonMentorReactionDTO> preProjectLessonDTOList = new ArrayList<>();
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

    private List<PreProjectComments> listStringToComments(PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        List<String> stringList = preProjectLessonMentorReactionDTO.getComments();
        List<PreProjectComments> CommentsList = new ArrayList<>();

        if (stringList != null) {
            PreProjectComments preProjectComments = PreProjectComments.builder()
                    .comment(stringList.get(stringList.size() - 1))
                    .build();

            CommentsList = preProjectLessonRepository.findById(preProjectLessonMentorReactionDTO
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