package dtos;

import lombok.Data;
import java.util.List;

@Data
public class MailDTO {

    private String replyTo;

    private List<String> to;

    //Copy to
    private List<String> cc;

    //Blind copy to
    private List<String> bcc;

    private String subject;

    private String text;
}
