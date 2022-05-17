package dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@Builder
public class BugReportsDTO {
    Long id;
    String text;
    List<MultipartFile> files;
    String type;
}
