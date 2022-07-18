package com.override.service;

import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreProjectLessonService {

    @Autowired
    private PreProjectLessonRepository preProjectLessonRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public PreProjectLesson saveLink(PreProjectLesson preProjectLesson, String login) {
        return preProjectLessonRepository.save(PreProjectLesson.builder()
                .link(preProjectLesson.getLink())
                .taskIdentifier(preProjectLesson.getTaskIdentifier())
                .user(platformUserService.findPlatformUserByLogin(login))
                .build());
    }
}
