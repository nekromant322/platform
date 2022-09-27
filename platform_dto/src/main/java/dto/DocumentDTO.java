package dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentDTO {
    private Long id;
    private String name;
    private String type;
    private LocalDateTime date;
}
