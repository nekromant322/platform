package com.override.service;

import com.override.mappers.QuestionMapper;
import com.override.models.Question;
import com.override.repositories.QuestionRepository;
import dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PlatformUserService platformUserService;

    public void save(QuestionDTO questionDTO){
        questionRepository.save(questionMapper.dtoToEntity(questionDTO));
    }

    public List<Question> findAllByUserAndChapter(String login, String chapter){
        return questionRepository.findAllByUserAndChapter(platformUserService.findPlatformUserByLogin(login), chapter);
    }

    public void delete(QuestionDTO questionDTO){

        questionRepository.deleteById(questionDTO.getId());
    }

    public void patch(QuestionDTO questionDTO){
        delete(questionDTO);
        save(questionDTO);
    }
}
