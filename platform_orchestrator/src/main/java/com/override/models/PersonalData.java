package com.override.models;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PersonalData {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}
