package com.override.service;

import com.override.mapper.PreProjectLessonMapper;
import com.override.mapper.PreProjectLessonMentorReactionMapper;
import com.override.model.PlatformUser;
import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import dto.PreProjectLessonDTO;
import dto.PreProjectLessonMentorReactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreProjectLessonService {

    @Autowired
    private PreProjectLessonRepository preProjectLessonRepository;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private PreProjectLessonMapper preProjectLessonMapper;

    @Autowired
    private PreProjectLessonMentorReactionMapper preProjectLessonMentorReactionMapper;

    public List<PreProjectLessonMentorReactionDTO> getAll() {
        return preProjectLessonMentorReactionMapper.listEntityToDto((List<PreProjectLesson>) preProjectLessonRepository.findAll());
    }

    public PreProjectLessonDTO save(PreProjectLessonDTO preProjectLessonDTO, String login) {
        PreProjectLesson preProjectLesson = preProjectLessonMapper.dtoToEntity(preProjectLessonDTO);

        return preProjectLessonMapper.entityToDto(preProjectLessonRepository.save(PreProjectLesson.builder()
                .link(preProjectLesson.getLink())
                .taskIdentifier(preProjectLesson.getTaskIdentifier())
                .user(platformUserService.findPlatformUserByLogin(login))
                .build()));
    }

    public void save(PreProjectLesson preProjectLesson) {
        preProjectLessonRepository.save(PreProjectLesson.builder()
                .link(preProjectLesson.getLink())
                .viewed(preProjectLesson.isViewed())
                .approve(preProjectLesson.isApprove())
                .comments(preProjectLesson.getComments())
                .taskIdentifier(preProjectLesson.getTaskIdentifier())
                .user(preProjectLesson.getUser())
                .build());
    }

    public void update(PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        PreProjectLesson link = preProjectLessonMentorReactionMapper.dtoToEntity(preProjectLessonMentorReactionDTO);

        PreProjectLesson preProjectLesson = preProjectLessonRepository.findById(link.getId()).get();

        preProjectLesson.setComments(link.getComments());
        preProjectLesson.setApprove(link.isApprove());
        preProjectLesson.setViewed(link.isViewed());

        preProjectLessonRepository.save(preProjectLesson);
    }

    public List<PreProjectLessonMentorReactionDTO> getAllByPathName(PreProjectLessonDTO preProjectLessonDTO, String login) {
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        return preProjectLessonMentorReactionMapper.listEntityToDto(preProjectLessonRepository
                .findAllByTaskIdentifierAndUser(preProjectLessonDTO.getTaskIdentifier(), user));
    }
}
