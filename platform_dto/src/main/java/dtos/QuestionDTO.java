package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDTO {
    private long id;
    private String login;
    private String chapter;
    private boolean answered;
    private String question;
}
