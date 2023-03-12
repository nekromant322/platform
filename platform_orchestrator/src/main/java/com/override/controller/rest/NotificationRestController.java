package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import com.override.service.VerificationService;
import dto.BalanceResponseFromNotificationControllerDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationRestController {

    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    private VerificationService verificationService;

    @Value("${sms.url.replenish-balance}")
    private String urlToReplenishBalance;

    @Secured("ROLE_ADMIN")
    @GetMapping("/balance")
    @Operation(summary = "Возвращает значение баланса для админа и ссылку на пополнение https://sms.ru/pay.php")
    public BalanceResponseFromNotificationControllerDTO getBalanceDTO() {
        return new BalanceResponseFromNotificationControllerDTO(notificatorFeign.getBalance(), urlToReplenishBalance);
    }

    @PatchMapping("/phone")
    @Operation(summary = "Пользователю на телефон поступает звонок для подтверждения номера телефона",
            description = "Последние четыре цифры номера телефона, с которого происходит звонок - код для подтверждения. " +
            "Данный код сохраняется в кэш для дальнейшего сравнения с кодом, который введет пользоваетль")
    public void getCodeCallSecurity(@RequestParam String phone) {
        verificationService.getCodeCallSecurity(phone);
    }

    @PatchMapping("/email")
    @Operation(summary = "Пользователю на электронную почту приходит сообщение с кодом подтверждения.",
            description = "Данный код сохраняется в кэш для дальнейшего сравнения с кодом, который введет пользоваетль")
    public void getCodeEmailSecurity(@RequestParam String email) {
        verificationService.getCodeEmailSecurity(email);
    }

    @PatchMapping("/codePhone/{codePhone}/{phoneNumber}")
    @Operation(summary = "Возвращает 'true', если введеный пользователем код для подтверждения номера телефона верный, либо 'false', если код не верный")
    public boolean getCodePhone(@PathVariable String codePhone, @PathVariable String phoneNumber) {
        return verificationService.getCodePhone(codePhone, phoneNumber);
    }

    @PatchMapping("/codeEmail/{codeEmail}/{email}")
    @Operation(summary = "Возвращает 'true', если введеный пользователем код для подтверждения электроннной почты верный, либо 'false', если код не верный")
    public boolean getCodeEmail(@PathVariable String codeEmail, @PathVariable String email) {
        return verificationService.getCodeEmail(codeEmail, email);
    }

    @PatchMapping("/contacts/{login}/{email}/{phoneNumber}")
    @Operation(summary = "Сохраняет подтвержденные номер телефона и адрес электронной почты в БД",
            description = "Сохраняет номер телефона и адрес электронной почты в таблицу с персональными данными в оркестратор и в таблицу получателей в нотификатор")
    public void savePhoneNumberAndEmail(@PathVariable String login, @PathVariable String email, @PathVariable Long phoneNumber) {
        verificationService.savePhoneNumberAndEmail(login, email, phoneNumber);
    }
}
