package com.override.service;

import com.override.models.PlatformUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LessonProgressService {

    @Autowired
    private PlatformUserService platformUserService;

    public void checkLesson(PlatformUser student, String lesson) {
        Map<String, Boolean> lessonProgress = student.getLessonProgress();
        lessonProgress.put(lesson, true);
        student.setLessonProgress(lessonProgress);
        platformUserService.save(student);
    }

    public List<String> getPassedLessons(PlatformUser user) {
        List<String> passedLessons = new ArrayList<>();
        user.getLessonProgress().forEach((lesson, bool) -> {
            if (bool) {
                passedLessons.add(lesson);
            }
        });
        return passedLessons;
    }
}
