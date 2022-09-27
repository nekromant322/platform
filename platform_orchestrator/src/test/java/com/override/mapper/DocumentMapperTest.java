package com.override.mapper;

import com.override.model.Document;
import dto.DocumentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestDocument;
import static com.override.utils.TestFieldsUtil.generateTestDocumentDTO;

@ExtendWith(MockitoExtension.class)
public class DocumentMapperTest {
        @InjectMocks
        private DocumentMapper documentMapper;

        @Test
        public void testDtoToEntity() {
            Document testDocument = generateTestDocument();
            DocumentDTO testDocumentDTO = generateTestDocumentDTO();

            DocumentDTO documentDTO = documentMapper.entityToDto(testDocument);

            Assertions.assertEquals(documentDTO.getId(), testDocumentDTO.getId());
            Assertions.assertEquals(documentDTO.getName(), testDocumentDTO.getName());
            Assertions.assertEquals(documentDTO.getType(), testDocumentDTO.getType());
            Assertions.assertEquals(documentDTO.getDate(), testDocumentDTO.getDate());
        }
    }
