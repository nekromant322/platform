package com.override.controller.rest;

import com.override.model.InterviewData;
import com.override.service.InterviewDataService;
import dto.InterviewDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Возвращает список собеседований из БД")
    public List<InterviewDataDTO> findAllInterviewReports() {
        return interviewDataService.findAll();
    }

    @DeleteMapping
    @Operation(summary = "Удаляет данные собеседования по его id из БД")
    @ApiResponse(responseCode = "200", description = "Данные собеседования удалены")
    public ResponseEntity<String> deleteInterview(@Parameter(description = "id собеседования") @RequestParam Long id) {
        interviewDataService.delete(id);
        return new ResponseEntity<>("Данные собеседования удалены", HttpStatus.OK);
    }

    @PatchMapping("/save")
    @Operation(summary = "Сохраняет данные собеседования в БД")
    @ApiResponse(responseCode = "200", description = "Данные собеседования сохранены")
    public ResponseEntity<String> save(@RequestBody InterviewDataDTO interviewDataDTO) {
        interviewDataService.save(interviewDataDTO);
        return new ResponseEntity<>("Данные собеседования сохранены", HttpStatus.OK);
    }

    @GetMapping("/getData")
    @Operation(summary = "Получает из БД данные собеседования по его id")
    public ResponseEntity<InterviewData> getInterviewData(@Parameter(description = "id собеседования") @RequestParam Long id) {
        InterviewData interviewTable = interviewDataService.findById(id);
        return new ResponseEntity<>(interviewTable, HttpStatus.OK);
    }
}
