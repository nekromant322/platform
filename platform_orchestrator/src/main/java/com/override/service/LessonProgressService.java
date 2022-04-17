package com.override.service;

import com.override.models.PlatformUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class LessonProgressService {

    @Autowired
    private PlatformUserService platformUserService;

    public void checkLesson(PlatformUser student, String lesson) {
        List<String> lessonProgress = student.getLessonProgress();
        AtomicBoolean exists = new AtomicBoolean(false);
        lessonProgress.forEach( l -> {
            if (Objects.equals(l, lesson)) {
                exists.set(true);
            }
        });
        if (!exists.get()) {
            lessonProgress.add(lesson);
            student.setLessonProgress(lessonProgress);
            platformUserService.update(student);
        }
    }
}
