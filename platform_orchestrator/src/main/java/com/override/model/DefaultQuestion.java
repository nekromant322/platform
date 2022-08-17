package com.override.model;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String question;

    @Column
    private String chapter;

    @Column
    private int section;

    public DefaultQuestion(String question, String chapter, int section) {
        this.question = question;
        this.chapter = chapter;
        this.section = section;
    }
}
