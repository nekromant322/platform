package com.override.controller;

import com.override.service.CustomStudentDetailService;
import com.override.service.OfferDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/offer-document")
public class OfferDocumentController {

    @Autowired
    private OfferDocumentService offerDocumentService;

    @PostMapping("/upload")
    public String uploadFile(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                     @RequestParam("file") MultipartFile multipartFile) throws IOException {
        System.out.println("multipartFile.getOriginalFilename() : " + multipartFile.getOriginalFilename());
        System.out.println("file.getBytes() : " + multipartFile.getBytes());
        System.out.println("multipartFile.getContentType() : " + multipartFile.getContentType());
        offerDocumentService.upload(multipartFile, user.getUsername());
        return "redirect:/interviewReports";
    }
}
