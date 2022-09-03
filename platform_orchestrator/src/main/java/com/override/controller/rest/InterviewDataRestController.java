package com.override.controller.rest;

import com.override.model.InterviewData;
import com.override.service.InterviewDataService;
import dto.InterviewDataDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewData")
public class InterviewDataRestController {

    @Autowired
    private InterviewDataService interviewDataService;

    @GetMapping
    @ApiOperation(value = "Возвращает список собеседований из БД")
    public List<InterviewDataDTO> findAllInterviewReports() {
        return interviewDataService.findAll();
    }

    @DeleteMapping
    @ApiOperation(value = "Удаляет данные собеседования по его id из БД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Данные собеседования удалены")
    })
    public ResponseEntity<String> deleteInterview(@ApiParam(value = "id собеседования") @RequestParam Long id) {
        interviewDataService.delete(id);
        return new ResponseEntity<>("Данные собеседования удалены", HttpStatus.OK);
    }

    @PatchMapping("/save")
    @ApiOperation(value = "Сохраняет данные собеседования в БД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Данные собеседования сохранены")
    })
    public ResponseEntity<String> save(@RequestBody InterviewDataDTO interviewDataDTO) {
        interviewDataService.save(interviewDataDTO);
        return new ResponseEntity<>("Данные собеседования сохранены", HttpStatus.OK);
    }

    @GetMapping("/getData")
    @ApiOperation(value = "Получает из БД данные собеседования по его id")
    public ResponseEntity<InterviewData> getInterviewData(@ApiParam(value = "id собеседования") @RequestParam Long id) {
        InterviewData interviewTable = interviewDataService.findById(id);
        return new ResponseEntity<>(interviewTable, HttpStatus.OK);
    }
}
