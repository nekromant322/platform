package com.override.mapper;

import com.override.model.PlatformUser;
import com.override.model.Question;
import com.override.service.PlatformUserService;
import dto.QuestionDTO;
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

    public Question dtoToEntity(QuestionDTO questionDTO, PlatformUser user){
        return Question.builder()
                .id(questionDTO.getId())
                .question(questionDTO.getQuestion())
                .chapter(questionDTO.getChapter())
                .answered(questionDTO.isAnswered())
                .user(user)
                .build();
    }
}
