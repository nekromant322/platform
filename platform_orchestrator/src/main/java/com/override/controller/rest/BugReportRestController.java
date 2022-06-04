package com.override.controller.rest;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/bugs")
public class BugReportRestController {

    @Autowired
    private BugReportService bugReportService;

    @PostMapping
    @MaxFileSize("${documentSizeLimit.forBugReports}")
    public void uploadBug(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                          @RequestPart("file") MultipartFile multipartFile, @RequestPart("bugDescription") String text) throws FileUploadException {
        bugReportService.uploadFile(multipartFile, user.getUsername(), text);
    }

    @GetMapping
    public List<BugReportsDTO> getAllBugs() {
        return bugReportService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Bug bug = bugReportService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(bug.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; name=" + bug.getName())
                .body(new ByteArrayResource(bug.getContent()));
    }
}
