package com.override.service;

import com.override.models.Document;
import com.override.models.PlatformUser;
import com.override.repositories.DocumentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    public void uploadTest() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setLogin("user");
        MockMultipartFile file = new MockMultipartFile("data",
                "filename.txt",
                "text/plain",
                "some content".getBytes());

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        documentService.upload(file, "login");

        verify(documentRepository, times(1)).save(any());
    }

    @Test
    public void getAllDocsTest() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setLogin("user");
        platformUser.setId(1L);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        Document document = new Document();
        document.setName("filename.txt");
        document.setType("text/plain");
        document.setContent("some content".getBytes());
        document.setUser(platformUser);

        List<Document> list = List.of(document);

        when(documentRepository.findAllByUserId(1L)).thenReturn(List.of(document));

        List<Document> userList = documentService.getAllByUserLogin("login");

        Assertions.assertEquals(list, userList);
    }

    @Test
    public void downloadTest() {
        Document document = new Document();
        document.setId(1L);
        document.setName("filename.txt");
        document.setType("text/plain");
        document.setContent("some content".getBytes());

        when(documentRepository.getById(1L)).thenReturn(document);

        Document userDocument = documentService.downloadFile(1L);

        Assertions.assertEquals(userDocument, document);
    }

    @Test
    public void deleteTest() {
        Document document = new Document();
        document.setId(1L);

        when(documentRepository.getById(1L)).thenReturn(document);

        documentService.delete(1L);

        verify(documentRepository, times(1)).delete(document);
    }
}
