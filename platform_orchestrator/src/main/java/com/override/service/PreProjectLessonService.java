package com.override.service;

import com.override.model.PlatformUser;
import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreProjectLessonService {

    @Autowired
    private PreProjectLessonRepository preProjectLessonRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public List<PreProjectLesson> getAll() {
        return (List<PreProjectLesson>) preProjectLessonRepository.findAll();
    }

    public PreProjectLesson saveLink(PreProjectLesson preProjectLesson, String login) {
        return preProjectLessonRepository.save(PreProjectLesson.builder()
                .link(preProjectLesson.getLink())
                .viewed(preProjectLesson.isViewed())
                .approve(preProjectLesson.isApprove())
                .messages(preProjectLesson.getMessages())
                .taskIdentifier(preProjectLesson.getTaskIdentifier())
                .user(platformUserService.findPlatformUserByLogin(login))
                .build());
    }

    public void update(PreProjectLesson link) {
        PreProjectLesson preProjectLesson = preProjectLessonRepository.findById(link.getId()).get();
        preProjectLesson.setMessages(link.getMessages());
        preProjectLesson.setViewed(link.isViewed());
        preProjectLesson.setApprove(link.isApprove());
        preProjectLessonRepository.save(preProjectLesson);
    }

    public List<PreProjectLesson> getAllByPathName(PreProjectLesson link, String login) {
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        return preProjectLessonRepository.findAllByTaskIdentifierAndUser(link.getTaskIdentifier(), user);
    }
}
