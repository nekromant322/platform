package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PreProjectLessonMentorReactionDTO {
    private long id;
    private List<String> comments;
    private boolean approve;
    private boolean viewed;
    private String login;
    private String link;
}
