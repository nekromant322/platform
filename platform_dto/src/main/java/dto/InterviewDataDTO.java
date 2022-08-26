package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class InterviewDataDTO {
    private Long id;

    @ApiModelProperty(value = "Логин пользователя")
    private String userLogin;

    @ApiModelProperty(value = "Название компании")
    private String company;

    @ApiModelProperty(value = "Описание вакансии")
    private String description;

    @ApiModelProperty(value = "Контакты для связи")
    private String contacts;

    @ApiModelProperty(value = "Дата собеседования")
    private LocalDate date;

    @ApiModelProperty(value = "Время собеседования")
    private LocalTime time;

    @ApiModelProperty(value = "Комментарий после собеседования")
    private String comment;

    @ApiModelProperty(value = "Стек")
    private String stack;

    @ApiModelProperty(value = "Предлагаемая зарплата")
    private int salary;

    @ApiModelProperty(value = "Ссылка на встречу")
    private String meetingLink;

    @ApiModelProperty(value = "Уточнение, возможна ли удаленка")
    private String distanceWork;
}
