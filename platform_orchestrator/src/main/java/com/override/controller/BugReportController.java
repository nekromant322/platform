package com.override.controller;

import com.override.annotation.MaxFileSize;
import com.override.models.Bug;
import com.override.service.BugReportService;
import com.override.service.CustomStudentDetailService;
import dtos.BugReportsDTO;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/bugReports")
public class BugReportController {

    @Autowired
    private BugReportService bugReportService;

    @PostMapping("/upload")
    @MaxFileSize("${documentSizeLimit.forPersonalData}")
    public String uploadScreen(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                       @RequestParam("file") MultipartFile multipartFile, @RequestParam("text") String text) throws FileUploadException {
        bugReportService.uploadFile(multipartFile, user.getUsername(), text);
        return "redirect:http://localhost:8000";
    }

    @GetMapping("/allBugs")
    @ResponseBody
    public List<BugReportsDTO> getAllFilesInfoForCurrentUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return bugReportService.getAll(user.getUsername());
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Bug bug = bugReportService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(bug.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; name=" + bug.getName())
                .body(new ByteArrayResource(bug.getContent()));
    }
}
