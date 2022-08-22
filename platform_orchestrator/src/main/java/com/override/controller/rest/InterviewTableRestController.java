package com.override.controller.rest;

import com.override.model.InterviewTable;
import com.override.service.InterviewTableService;
import dto.InterviewTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewTables")
public class InterviewTableRestController {

    @Autowired
    private InterviewTableService interviewTableService;

    @GetMapping
    public List<InterviewTableDTO> findAllInterviewReports() {
        return interviewTableService.findAll();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInterview(@RequestParam Long id) {
        interviewTableService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/save")
    public ResponseEntity<String> save(@RequestBody InterviewTable interviewTable) {
        interviewTableService.save(interviewTable);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getData")
    public ResponseEntity<InterviewTable> getDataInterview(@RequestParam Long id) {
        InterviewTable interviewTable = interviewTableService.findById(id);
        return new ResponseEntity<>(interviewTable, HttpStatus.OK);
    }
}
