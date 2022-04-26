package dtos;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MailDTO {

    private String replyTo;

    private List<String> to;

    private String subject;

    private String text;
}
