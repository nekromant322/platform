package com.override.controller;

import com.override.model.OfferDocument;
import com.override.service.OfferDocumentService;
import dto.OfferDocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/offer-document")
public class OfferDocumentController {

    @Autowired
    private OfferDocumentService offerDocumentService;

    @PostMapping("/upload/{reportId}")
    public String uploadFile(@PathVariable("reportId") Long reportId,
                             @RequestParam("file") MultipartFile multipartFile) throws IOException {

        System.out.println("Report id : " + reportId);
        System.out.println("multipartFile.getOriginalFilename() : " + multipartFile.getOriginalFilename());
        System.out.println("file.getBytes() : " + multipartFile.getBytes());
        System.out.println("multipartFile.getContentType() : " + multipartFile.getContentType());

        offerDocumentService.upload(multipartFile, reportId);

        return "redirect:/interviewReports";
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {

        System.out.println("\nGET REQUEST\n");
        System.out.println("reportId : " + id);


        OfferDocument offerDocument = offerDocumentService.download(id);

        System.out.println("offerDocument.getContent() : " + offerDocument.getContent());
        System.out.println("offerDocument.getType() : " + offerDocument.getType());
        System.out.println("offerDocument.getName() : " + offerDocument.getName());
        System.out.println("offerDocument.getInterviewReport() : " + offerDocument.getInterviewReport());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(offerDocument.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + offerDocument.getName())
                .contentLength(offerDocument.getContent().length)
                .body(new ByteArrayResource(offerDocument.getContent()));
    }

    @GetMapping("/{reportId}")
    @ResponseBody
    public OfferDocumentDTO getFileDTO(@PathVariable("reportId") Long reportId) {
        return offerDocumentService.getByReportId(reportId);
    }

}
