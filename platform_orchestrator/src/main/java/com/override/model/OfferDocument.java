package com.override.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
public class OfferDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @JsonIgnore
    @Lob
    @Type(type = "org.hibernate.type.MaterializedBlobType")
    private byte[] content;

    @JsonIgnore
    @JoinColumn(name = "report_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private InterviewReport interviewReport;
}
