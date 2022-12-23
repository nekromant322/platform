package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YooMoneyRequestInfoDTO {

    @ApiModelProperty(value = "Сумма платежа")
    private Double amount;

    @ApiModelProperty(value = "Комментарий к платежу")
    private String comment;

    @ApiModelProperty(value = "Login плательщика")
    private String login;
}
