package dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PreProjectLessonDTO {
    private String link;
    private String taskIdentifier;
}
