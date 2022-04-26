package com.override.models;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String login;

    private String email;

    private String telegramId;

    private String phoneNumber;

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getTelegramId() {
        return Optional.ofNullable(telegramId);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
}
