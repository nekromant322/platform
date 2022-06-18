package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BugReportsDTO {
    private Long id;
    private String name;
    private String type;
    private String text;
    private String user;
}
