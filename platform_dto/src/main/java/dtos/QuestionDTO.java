package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDTO {
    long id;
    String login;
    int chapter;
    boolean answered;
    String question;
}
