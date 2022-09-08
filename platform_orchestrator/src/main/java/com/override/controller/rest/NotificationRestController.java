package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import com.override.service.VerificationService;
import dto.BalanceResponseFromNotificationControllerDTO;
import io.swagger.annotations.ApiOperation;
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
    NotificatorFeign notificatorFeign;

    @Autowired
    VerificationService verificationService;

    @Value("${sms.url.replenish-balance}")
    private String urlToReplenishBalance;

    @Secured("ROLE_ADMIN")
    @GetMapping("/balance")
    @ApiOperation(value = "Возвращает значение баланса для админа и ссылку на пополнение https://sms.ru/pay.php")
    public BalanceResponseFromNotificationControllerDTO getBalanceDTO() {
        return new BalanceResponseFromNotificationControllerDTO(notificatorFeign.getBalance(), urlToReplenishBalance);
    }

    @PatchMapping("/phone")
    public String getCodeCallSecurity(@RequestParam String phone) {
        return verificationService.getCodeCallSecurity(phone);
    }

    @PatchMapping("/email")
    public String getCodeEmailSecurity(@RequestParam String email) {
        return verificationService.getCodeEmailSecurity(email);
    }

    @PatchMapping("/codePhone/{codePhone}/{phoneNumber}")
    public boolean getCodePhone(@PathVariable String codePhone, @PathVariable String phoneNumber) {
        return verificationService.getCodePhone(codePhone, phoneNumber);
    }

    @PatchMapping("/codeEmail/{codeEmail}/{email}")
    public boolean getCodeEmail(@PathVariable String codeEmail, @PathVariable String email) {
        return verificationService.getCodeEmail(codeEmail, email);
    }

    @PatchMapping("/contacts/{login}/{email}/{phoneNumber}")
    public void savePhoneNumberAndEmail(@PathVariable String login, @PathVariable String email, @PathVariable Long phoneNumber) {
        verificationService.savePhoneNumberAndEmail(login, email, phoneNumber);
    }
}
