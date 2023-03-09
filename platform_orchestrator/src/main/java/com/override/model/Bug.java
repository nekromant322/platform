package com.override.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Schema(description = "Значение берется из файла MultipartFile, который приходит с фроната, " +
            "если файла нет, то подставляется дефолтное значение - \"text\"")
    private String text;

    private String type;

    @JsonIgnore
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] content;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlatformUser user;
}
