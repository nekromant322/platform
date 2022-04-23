package com.override.service;

import com.override.models.Document;
import com.override.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void upload(MultipartFile file, String login) {

        try {
            Document document = new Document();
            document.setContent(file.getBytes());
            document.setType(Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf(".")));
            document.setName(file.getOriginalFilename());
            document.setUser(platformUserService.findPlatformUserByLogin(login));
            documentRepository.save(document);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Document> getAllByUserLogin(String login) {
        return documentRepository.findAllByUserId(platformUserService.findPlatformUserByLogin(login).getId());
    }

    public Document downloadFile(Long fileId) {
        return documentRepository.getById(fileId);
    }

    public void delete(Long fileId) {
        documentRepository.delete(documentRepository.getById(fileId));
    }
}
