package dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class BugReportsDTO {
    private Long id;
    private String name;
    private String text;
    private List<MultipartFile> files;
    private String type;
}
