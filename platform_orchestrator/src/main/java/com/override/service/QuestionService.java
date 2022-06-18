package com.override.service;

import com.override.mapper.QuestionMapper;
import com.override.model.Question;
import com.override.repository.QuestionRepository;
import dto.QuestionDTO;
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
        questionRepository.save(questionMapper.dtoToEntity(questionDTO, platformUserService.findPlatformUserByLogin(questionDTO.getLogin())));
    }

    public List<Question> findAllByUserAndChapter(String login, String chapter){
        return questionRepository.findAllByUserAndChapter(platformUserService.findPlatformUserByLogin(login), chapter);
    }

    public void delete(long id){

        questionRepository.deleteById(id);
    }

    public void patch(QuestionDTO questionDTO){
        save(questionDTO);
    }
}
