package com.override.mapper;

import com.override.model.Document;
import dto.DocumentDTO;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentDTO entityToDto(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .name(document.getName())
                .type(document.getType())
                .date(document.getDate())
                .build();
    }
}
