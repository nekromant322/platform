package com.override.mapper;

import com.override.model.PreProjectComment;
import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import com.override.service.PlatformUserService;
import dto.PreProjectLessonMentorReactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreProjectLessonMentorReactionMapper {

    @Autowired
    private PreProjectLessonRepository preProjectLessonRepository;

    @Autowired
    private PlatformUserService platformUserService;

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

    public static PreProjectLessonMentorReactionDTO entityToDto(PreProjectLesson preProjectLesson) {
        return PreProjectLessonMentorReactionDTO.builder()
                .id(preProjectLesson.getId())
                .login(preProjectLesson.getUser().getLogin())
                .comments(toListStringComments(preProjectLesson))
                .link(preProjectLesson.getLink())
                .viewed(preProjectLesson.isViewed())
                .approve(preProjectLesson.isApprove())
                .build();
    }

    private static List<String> toListStringComments(PreProjectLesson preProjectLesson) {
        List<String> commentsList = new ArrayList<>();
        if (preProjectLesson.getComments() != null) {
            for (int i = 0; i < preProjectLesson.getComments().size(); i++) {
                commentsList.add(preProjectLesson.getComments().get(i).getComment());
            }
        }
        return commentsList;
    }

    public List<PreProjectLessonMentorReactionDTO> listEntityToDto(List<PreProjectLesson> preProjectLessonList) {
        return preProjectLessonList.stream().map(PreProjectLessonMentorReactionMapper::entityToDto).collect(Collectors.toList());
    }

    private List<PreProjectComment> listStringToComments(PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        List<String> stringList = preProjectLessonMentorReactionDTO.getComments();
        List<PreProjectComment> commentsList = new ArrayList<>();

        if (stringList != null) {
            PreProjectComment preProjectComment = PreProjectComment.builder()
                    .comment(stringList.get(stringList.size() - 1))
                    .build();

            commentsList = preProjectLessonRepository.findById(preProjectLessonMentorReactionDTO
                    .getId()).get().getComments();

            if (commentsList.isEmpty()) {
                commentsList.add(preProjectComment);
            } else if (!commentsList.get(commentsList.size() - 1).getComment().equals(preProjectComment.getComment())) {
                commentsList.add(preProjectComment);
            }
        }
        return commentsList;
    }
}