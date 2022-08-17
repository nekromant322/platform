package com.override.controller.rest;

import com.override.model.OfferDocument;
import com.override.service.OfferDocumentService;
import dto.OfferDocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@RestController
@RequestMapping("/offer-document")
public class OfferDocumentRestController {

    @Autowired
    private OfferDocumentService offerDocumentService;

    @PostMapping("/upload/{reportId}")
    public void uploadFile(@PathVariable("reportId") Long reportId,
                           @RequestParam("file") MultipartFile multipartFile,
                           HttpServletResponse response) throws IOException {

        offerDocumentService.upload(multipartFile, reportId);
        response.sendRedirect("/interviewReports");
    }

    @GetMapping("/download/{id}")
    @Transactional
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {

        OfferDocument offerDocument = offerDocumentService.download(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(offerDocument.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + offerDocument.getName())
                .contentLength(offerDocument.getContent().length)
                .body(new ByteArrayResource(offerDocument.getContent()));
    }

    @GetMapping("/{reportId}")
    public OfferDocumentDTO getFileDTO(@PathVariable("reportId") Long reportId) {
        return offerDocumentService.getByReportId(reportId);
    }
}
