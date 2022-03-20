package com.override.mappers;

import com.override.models.Question;
import com.override.service.PlatformUserService;
import dtos.QuestionDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class QuestionMapper {
    @Autowired
    private PlatformUserService platformUserService;

    public Question dtoToEntity(QuestionDTO questionDTO){
        return Question.builder()
                .id(questionDTO.getId())
                .question(questionDTO.getQuestion())
                .chapter(questionDTO.getChapter())
                .answered(questionDTO.isAnswered())
                .user(platformUserService.findPlatformUserByLogin(questionDTO.getLogin()))
                .build();
    }
}
