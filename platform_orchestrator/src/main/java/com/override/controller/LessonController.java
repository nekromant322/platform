package com.override.controller;

import com.override.service.LessonStructureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonStructureService lessonStructureService;

    @GetMapping("{course}/{chapter}/{step}/{lesson}")
    @ApiOperation(value = "Возвращает строчку - \"lessons\" + \"/\" + course + \"/\" + chapter + \"/\" + step + \"/\" + lesson; " +
            "где course, chapter, step и lesson - входные параметры в данный метод")
    public String lessonPage(@PathVariable String course, @PathVariable String chapter, @PathVariable String step,
                             @PathVariable String lesson) {
        return "lessons" + "/" + course + "/" + chapter + "/" + step + "/" + lesson;
    }

    @ResponseBody
    @GetMapping(value = "/structureOf/{course}", produces = "application/json")
    @ApiOperation(value = "Не очень хорошо разобрался с этим методом. Возвращает значение JsonObject из HashMap<String, JsonObject> courseLessonStructure по ключу course")
    public String getLessonStructure(@PathVariable String course) {
        return lessonStructureService.getLessonStructure(course).toString();
    }
}


