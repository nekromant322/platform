package com.override.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VkCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "join_link")
    private String joinLink;

    @Column(name = "call_id")
    private String callId;

    //Добавить joinColumn с Review
    @Column(name = "review_id")
    private Long reviewId;
}
