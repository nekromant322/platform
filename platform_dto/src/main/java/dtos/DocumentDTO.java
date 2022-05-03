package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDTO {
    private Long id;
    private String name;
    private String type;
}
