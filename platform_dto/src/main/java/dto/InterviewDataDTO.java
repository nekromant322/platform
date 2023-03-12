package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class InterviewDataDTO {
    private Long id;

    @Schema(description = "Логин пользователя")
    private String userLogin;

    @Schema(description = "Название компании")
    private String company;

    @Schema(description = "Описание вакансии")
    private String description;

    @Schema(description = "Контакты для связи")
    private String contacts;

    @Schema(description = "Дата собеседования")
    private LocalDate date;

    @Schema(description = "Время собеседования")
    private LocalTime time;

    @Schema(description = "Комментарий после собеседования")
    private String comment;

    @Schema(description = "Стек")
    private String stack;

    @Schema(description = "Предлагаемая зарплата")
    private int salary;

    @Schema(description = "Ссылка на встречу")
    private String meetingLink;

    @Schema(description = "Уточнение, возможна ли удаленка")
    private String distanceWork;
}
