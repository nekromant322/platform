package dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class InterviewReportUpdateDTO {
    private Long id;
    private Integer salary;
    private MultipartFile file;
}
