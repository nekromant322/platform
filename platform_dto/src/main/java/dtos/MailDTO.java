package dtos;

import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

@Data
public class MailDTO {
//    @Nullable
//    private String from;

    @Nullable
    private String replyTo;

    @Nullable
    private List<String> to;

    @Nullable
    private List<String> cc;

    @Nullable
    private List<String> bcc;

    @Nullable
    private Date sentDate;

    @Nullable
    private String subject;

    @Nullable
    private String text;
}
