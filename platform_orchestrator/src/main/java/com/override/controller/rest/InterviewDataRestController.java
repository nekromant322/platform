package com.override.controller.rest;

import com.override.model.InterviewData;
import com.override.service.InterviewDataService;
import dto.InterviewDataDTO;
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
    public List<InterviewDataDTO> findAllInterviewReports() {
        return interviewDataService.findAll();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInterview(@RequestParam Long id) {
        interviewDataService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/save")
    public ResponseEntity<String> save(@RequestBody InterviewData interviewTable) {
        interviewDataService.save(interviewTable);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getData")
    public ResponseEntity<InterviewData> getDataInterview(@RequestParam Long id) {
        InterviewData interviewTable = interviewDataService.findById(id);
        return new ResponseEntity<>(interviewTable, HttpStatus.OK);
    }
}
