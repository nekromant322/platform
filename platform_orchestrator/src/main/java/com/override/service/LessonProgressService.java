package com.override.service;

import com.override.model.LessonProgress;
import com.override.model.PlatformUser;
import com.override.repository.LessonProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LessonProgressService {

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    /**
     * @param student - пользователь платформы
     * @param lesson - идентификатор урока в формате "core-1-1"
     * указанный lesson вносится в коллекцию с пройдеными уроками, тем самым "помечает" его как пройденный.
     */
    @CacheEvict(cacheNames = "lessonProgress", key = "#student.id")
    public void markLessonAsPassed(PlatformUser student, String lesson) {
        List<LessonProgress> lessonProgress = lessonProgressRepository.findAllByUserId(student.getId());
        boolean exists = false;
        for (LessonProgress passedLesson : lessonProgress) {
            if (Objects.equals(passedLesson.getLesson(), lesson)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            LessonProgress progress = new LessonProgress();
            progress.setLesson(lesson);
            progress.setUser(student);
            lessonProgressRepository.save(progress);
        }
    }

    @Cacheable(cacheNames = "lessonProgress", key = "#platformUser.id")
    public List<String> getPassedLessons(PlatformUser platformUser) {
        List<String> progress = new ArrayList<>();
        lessonProgressRepository.findAllByUserId(platformUser.getId())
                .forEach(lessonProgress -> progress.add(lessonProgress.getLesson()));
        return progress;
    }
}
