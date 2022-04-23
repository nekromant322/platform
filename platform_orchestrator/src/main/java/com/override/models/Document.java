package com.override.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
public class Document {

    @Id
    @GeneratedValue
    Long id;

    String name;

    String type;

    @JsonIgnore
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    byte[] content;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    PlatformUser user;


}
