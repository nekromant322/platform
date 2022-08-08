package com.override.service;

import com.override.model.OfferDocument;
import com.override.repository.OfferDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class OfferDocumentService {

    @Autowired
    private OfferDocumentRepository offerDocumentRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void upload(MultipartFile file, String userLogin) throws IOException {

        System.out.println("\nUPLOAD METHOD\n");

        OfferDocument offerDocument = new OfferDocument();
        offerDocument.setContent(file.getBytes());
        offerDocument.setType(file.getContentType());
        offerDocument.setName(file.getOriginalFilename());

        if(platformUserService.findPlatformUserByLogin(userLogin) != null) {
            offerDocument.setUserLogin(userLogin);
        }

        System.out.println("offerDocument.setContent(file.getBytes()) : " + offerDocument.getContent());
        System.out.println("offerDocument.setType(file.getContentType()) : " + offerDocument.getType());
        System.out.println("offerDocument.setName(file.getOriginalFilename()) : " + offerDocument.getName());
        System.out.println("offerDocument.setUserLogin(userLogin) : " + offerDocument.getUserLogin());

        offerDocumentRepository.save(offerDocument);

    }

    public OfferDocument downloadOfferFile(String login) {
        return null;
    }

    public void deleteOfferFile(String login) { }

}
