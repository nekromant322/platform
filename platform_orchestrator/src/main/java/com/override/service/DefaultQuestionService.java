package com.override.service;

import com.override.mapper.QuestionMapper;
import com.override.model.DefaultQuestion;
import com.override.model.Question;
import com.override.repository.DefaultQuestionRepository;
import com.override.repository.QuestionRepository;
import dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultQuestionService {
    @Autowired
    private DefaultQuestionRepository defaultQuestionRepository;

    public void save(DefaultQuestion defaultQuestion) {
        defaultQuestionRepository.save(defaultQuestion);
    }

    public List<DefaultQuestion> findAll() {
        return defaultQuestionRepository.findAll();
    }

    public List<DefaultQuestion> findAllByChapterAndSection(String chapter, int section) {
        return defaultQuestionRepository.findDefaultQuestionsByChapterAndSection(chapter, section);
    }

    public void delete(long id) {
        defaultQuestionRepository.deleteById(id);
    }

    public void update(DefaultQuestion defaultQuestion) {
        defaultQuestionRepository.save(defaultQuestion);
    }
}
