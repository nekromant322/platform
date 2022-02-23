package com.override.controller;

import com.override.service.LessonStructureService;
import dtos.LessonStructureDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonStructureService lessonStructureService;

    @Autowired
    public LessonController(LessonStructureService lessonStructureService) {
        this.lessonStructureService = lessonStructureService;
    }

    @GetMapping("{course}/{chapter}/{step}/{lesson}")
    public String lessonPage(@PathVariable String course, @PathVariable Integer chapter, @PathVariable Integer step,
                             @PathVariable Integer lesson) {
        return "lessons" + "/" + course + "/" + chapter + "/" + step + "/" + lesson;
    }

    @ResponseBody
    @GetMapping("/structureOf/{course}")
    public String getLessonStructure(@PathVariable String course) {
        try {
            return lessonStructureService.scanLessonStructure(course).toString();
        } catch (IOException e) {
            // почему здесь нужен try-catch? все IOException ловятся уже в lessonStructureService.getDirectoryStructure()
            log.error("Can't send LessonStructureDTO: ", e);
            return new JSONObject("Error", "error").toString();
        }
    }

}
