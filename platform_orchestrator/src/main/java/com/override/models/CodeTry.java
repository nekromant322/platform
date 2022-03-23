package com.override.models;

import enums.CodeExecutionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "code_try")
@NoArgsConstructor
@AllArgsConstructor
public class CodeTry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String studentsCode;

    @Column
    private Integer chapter;

    @Column
    private Integer step;

    @Column
    private Integer lesson;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CodeExecutionStatus codeExecutionStatus;

    @Column
    private LocalDateTime date = LocalDateTime.now();

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlatformUser user;
}
