package com.override.controller;

import com.override.annotation.MaxFileSize;
import com.override.model.Document;
import com.override.service.CustomStudentDetailService;
import com.override.service.DocumentService;
import dto.DocumentDTO;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Обновляет данные в документе у текущего пользователя и сохраняет в documentRepository")
    public String personalDataDocumentUpload(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                             @RequestParam("file") MultipartFile multipartFile) throws FileUploadException {

        documentService.uploadFile(multipartFile, user.getUsername());
        return "redirect:/personalData";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{login}")
    @ResponseBody
    @ApiOperation(value = "Возвращает List<DocumentDTO> из documentRepository по логину юзера")
    public List<DocumentDTO> getAllFilesInfo(@PathVariable String login) {
        return documentService.getAllByUserLogin(login);
    }

    @GetMapping("/currentUser")
    @ResponseBody
    @ApiOperation(value = "Возвращает List<DocumentDTO> из documentRepository по логину юзера, для текущего юзера")
    public List<DocumentDTO> getAllFilesInfoForCurrentUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return documentService.getAllByUserLogin(user.getUsername());
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    @ApiOperation(value = "Возвращает ResponseEntity.ok()\n" +
            "                .contentType(MediaType.parseMediaType(document.getType()))\n" +
            "                .header(HttpHeaders.CONTENT_DISPOSITION, \"attachment; filename=\" + document.getName())\n" +
            "                .body(new ByteArrayResource(document.getContent()));\n document возвращается из documentRepository по id.")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Document document = documentService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
                .body(new ByteArrayResource(document.getContent()));
    }

    @DeleteMapping("/{fileId}")
    @ApiOperation(value = "Удаляет документ из documentRepository по fileId ")
    public void delete(@PathVariable Long fileId) {
        documentService.delete(fileId);
    }
}
