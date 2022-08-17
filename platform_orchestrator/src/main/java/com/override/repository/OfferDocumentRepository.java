package com.override.repository;

import com.override.model.OfferDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferDocumentRepository extends JpaRepository<OfferDocument, Long> {

    OfferDocument getOfferDocumentByInterviewReport_Id(Long reportId);
}
