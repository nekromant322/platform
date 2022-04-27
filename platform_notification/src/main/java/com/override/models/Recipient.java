package com.override.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
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

    @Email
    private String email;

    private String telegramId;

    /**
     * Аннотация @Pattern содержит регулярное выражения для российских номеров
     * формате их записи: +7..., 7..., 8..., 9998886655, 7-999-888-66-55, 7 999 888 66 55
     */
    @Pattern(regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")
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
