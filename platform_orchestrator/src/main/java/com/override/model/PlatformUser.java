package com.override.model;


import com.override.model.enums.CurrentCoursePart;
import enums.CoursePart;
import enums.StudyStatus;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlatformUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    private StudyStatus studyStatus;

    private CurrentCoursePart coursePart;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonalData personalData;

    @OneToOne(cascade = CascadeType.ALL)
    private UserSettings userSettings;
}
