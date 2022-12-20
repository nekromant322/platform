package dto;

import lombok.Data;

@Data
public class YooMoneyRequestInfoDTO {

    private Double amount;

    private String comment;

    private String login;
}
