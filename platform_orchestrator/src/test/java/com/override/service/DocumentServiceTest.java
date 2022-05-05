package com.override.service;

import com.override.controller.DocumentController;
import com.override.models.Document;
import com.override.models.PlatformUser;
import com.override.repositories.DocumentRepository;
import dtos.DocumentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static com.override.utils.TestFieldsUtil.generateTestDocument;
import static com.override.utils.TestFieldsUtil.generateTestUser;
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

    @Mock
    private DocumentController documentController;

    @Test
    public void uploadTest() {
        PlatformUser platformUser = generateTestUser();
        MockMultipartFile file = new MockMultipartFile("data",
                "filename.txt",
                "text/plain",
                "some content".getBytes());

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);

        documentService.uploadFile(file, "Andrey");
        verify(documentRepository, times(1)).save(any());
    }

    @Test
    public void getAllDocsTest() {
        PlatformUser platformUser = generateTestUser();
        platformUser.setId(1L);

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);

        Document document = generateTestDocument();
        document.setUser(platformUser);

        DocumentDTO documentDTO = DocumentDTO.builder()
                .id(document.getId())
                .name(document.getName())
                .type(document.getType())
                .build();

        List<DocumentDTO> list = List.of(documentDTO);

        when(documentRepository.findAllByUserId(1L)).thenReturn(List.of(document));

        List<DocumentDTO> userList = documentService.getAllByUserLogin("Andrey");

        Assertions.assertEquals(list, userList);
    }

    @Test
    public void downloadTest() {
        Document document = generateTestDocument();

        when(documentRepository.getById(1L)).thenReturn(document);

        Document userDocument = documentService.downloadFile(1L);

        Assertions.assertEquals(userDocument, document);
    }

    @Test
    public void deleteTest() {
        Document document = generateTestDocument();

        documentService.delete(1L);

        verify(documentRepository, times(1)).deleteById(document.getId());
    }
}
