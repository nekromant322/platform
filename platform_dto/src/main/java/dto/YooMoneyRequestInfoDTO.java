package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class YooMoneyRequestInfoDTO {

    @Schema(description = "Сумма платежа")
    private Double amount;

    @Schema(description = "Комментарий к платежу")
    private String comment;

    @Schema(description = "Login плательщика")
    private String login;
}
