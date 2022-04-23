package com.override.controller.rest;

import com.override.models.Document;
import com.override.service.CustomStudentDetailService;
import com.override.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/document")
public class DocumentRestController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public void uploadToDb(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                           @RequestParam("file") MultipartFile multipartFile) {
        documentService.upload(multipartFile, user.getUsername());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{login}")
    public List<Document> getAllFilesInfo (@PathVariable String login) {
        return documentService.getAllByUserLogin(login);
    }

    @GetMapping("/currentUser")
    public List<Document> getAllFilesInfoForCurrentUser (@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return documentService.getAllByUserLogin(user.getUsername());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Document document = documentService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
                .body(new ByteArrayResource(document.getContent()));
    }

    @DeleteMapping("/{fileId}")
    public void delete(@PathVariable Long fileId) {
        documentService.delete(fileId);
    }
}
