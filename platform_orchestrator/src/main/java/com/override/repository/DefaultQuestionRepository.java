package com.override.repository;

import com.override.model.DefaultQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultQuestionRepository extends JpaRepository<DefaultQuestion, Long> {

    List<DefaultQuestion> findDefaultQuestionsByChapterAndSection(String chapter, int section);
}

