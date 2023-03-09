package com.override.controller;

import com.override.annotation.MaxFileSize;
import com.override.model.Document;
import com.override.service.CustomStudentDetailService;
import com.override.service.DocumentService;
import dto.DocumentDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/personalData")
    @MaxFileSize("${documentSizeLimit.forPersonalData}")
    public String personalDataDocumentUpload(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                             @RequestParam("file") MultipartFile multipartFile) throws FileUploadException {
        documentService.uploadFile(multipartFile, user.getUsername());
        return "redirect:/personalData";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{login}")
    @ResponseBody
    public List<DocumentDTO> getAllFilesInfo(@PathVariable String login) {
        return documentService.getAllByUserLogin(login);
    }

    @GetMapping("/currentUser")
    @ResponseBody
    public List<DocumentDTO> getAllFilesInfoForCurrentUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return documentService.getAllByUserLogin(user.getUsername());
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Document document = documentService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
                .body(new ByteArrayResource(document.getContent()));
    }

    @DeleteMapping("/{fileId}")
    @ResponseBody
    public void delete(@PathVariable Long fileId) {
        documentService.delete(fileId);
    }

    @GetMapping("/documentList/{id}")
    @ResponseBody
    @Operation(summary = "Возвращает все \"документы\" студента по его id")
    public List<DocumentDTO> getStudentDocuments(@PathVariable Long id) {
        return documentService.findAllByUserId(id);
    }
}
