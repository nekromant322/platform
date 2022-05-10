package com.override.controller.rest;

import com.override.models.enums.Status;
import com.override.service.InterviewReportService;
import dtos.InterviewReportDTO;
import dtos.InterviewReportUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewReport")
public class InterviewReportRestController {

    @Autowired
    private InterviewReportService interviewReportService;

    @PatchMapping
    public ResponseEntity<String> saveInterviewReport(@RequestBody InterviewReportDTO interviewReportDTO) {
        interviewReportService.save(interviewReportDTO);
        return new ResponseEntity<>("Отчёт о собеседовании сохранён!", HttpStatus.OK);
    }

    @PatchMapping("/offer")
    public ResponseEntity<String> changeStatusToOffer(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.OFFER);
        return new ResponseEntity<>("Статус собеседования изменён на Offer!", HttpStatus.OK);
    }

    @PatchMapping("/accepted")
    public ResponseEntity<String> changeStatusToAccepted(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.ACCEPTED);
        return new ResponseEntity<>("Статус собеседования изменён на Accepted!", HttpStatus.OK);
    }

    @GetMapping
    public List<InterviewReportDTO> findAllInterviewReports() {
        return interviewReportService.findAll();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInterviewReport(@RequestParam Long id) {
        interviewReportService.delete(id);
        return new ResponseEntity<>("Отчёт о собеседовании удалён!", HttpStatus.OK);
    }
}
