package com.override.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class InterviewData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login")
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
    @Column(name = "meeting_link")
    private String meetingLink;

    @ApiModelProperty(value = "Уточнение, возможна ли удаленка")
    @Column(name = "distance_work")
    private String distanceWork;
}
