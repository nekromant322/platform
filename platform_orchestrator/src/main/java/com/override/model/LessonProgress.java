package com.override.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Schema(description = "Идентификатор урока", example = "core-1-1")
    private String lesson;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PlatformUser user;
}
