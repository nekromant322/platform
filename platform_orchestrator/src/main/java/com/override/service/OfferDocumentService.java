package com.override.service;

import com.override.model.OfferDocument;
import com.override.repository.OfferDocumentRepository;
import dto.OfferDocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class OfferDocumentService {

    @Autowired
    private OfferDocumentRepository offerDocumentRepository;

    @Autowired
    private InterviewReportService interviewReportService;

    public void upload(MultipartFile file, Long id) throws IOException {

        System.out.println("\nUPLOAD METHOD\n");

        OfferDocument offerDocument = new OfferDocument();
        offerDocument.setContent(file.getBytes());
        offerDocument.setType(file.getContentType());
        offerDocument.setName(file.getOriginalFilename());
        offerDocument.setInterviewReport(interviewReportService.findReportById(id));

        System.out.println("offerDocument.setContent(file.getBytes()) : " + offerDocument.getContent());
        System.out.println("offerDocument.setType(file.getContentType()) : " + offerDocument.getType());
        System.out.println("offerDocument.setName(file.getOriginalFilename()) : " + offerDocument.getName());
        System.out.println("offerDocument.setInterviewReport(interviewReportService.findReportById(id)) : "
                + offerDocument.getInterviewReport());

        offerDocumentRepository.save(offerDocument);

    }

    @Transactional
    public OfferDocument download(Long fileId) { return offerDocumentRepository.getById(fileId); }

    @Transactional
    public OfferDocumentDTO getByReportId(Long reportId) {

        OfferDocument offerDocument = offerDocumentRepository.getOfferDocumentByInterviewReport_Id(reportId);

        if(offerDocument == null) { return OfferDocumentDTO.builder().build(); }

        return OfferDocumentDTO.builder()
                .id(offerDocument.getId())
                .name(offerDocument.getName())
                .type(offerDocument.getType())
                .build();
    }
}
