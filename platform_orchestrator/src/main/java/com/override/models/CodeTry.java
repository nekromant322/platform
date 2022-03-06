package com.override.models;

import enums.CodeExecutionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "student-code")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CodeTry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String studentsCode;

    @Column
    private Integer chapter;

    @Column
    private Integer step;

    @Column
    private Integer lesson;

    @Column
    private CodeExecutionStatus codeExecutionStatus;

    @Column
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PlatformUser user;
}
