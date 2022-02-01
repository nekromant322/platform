package dtos;

import lombok.Data;
import java.util.List;

@Data
public class MailDTO {

    private String replyTo;

    private List<String> to;

    private String subject;

    private String text;
}
