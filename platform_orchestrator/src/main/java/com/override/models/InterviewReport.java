package com.override.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class InterviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Email
    private String email;

    private String company;

    private String project;

    private String questions;

    private Integer impression;

    @Column(name = "min_salary")
    private String minSalary;

    @Column(name = "max_salary")
    private String maxSalary;

    @Column(name = "offer_salary")
    private String offerSalary;

    @Column(name = "result_salary")
    private String resultSalary; // 300000₽ Net or 4700$ Gross

    private String level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PlatformUser user;
}
