package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import dto.BalanceResponseFromNotificationControllerDTO;
import dto.CodeCallSecurityCodeDTO;
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

    @Value("${sms.url.replenish-balance}")
    private String urlToReplenishBalance;

    @Secured("ROLE_ADMIN")
    @GetMapping("/balance")
    @ApiOperation(value = "Возвращает значение баланса для админа и ссылку на пополнение https://sms.ru/pay.php")
    public BalanceResponseFromNotificationControllerDTO getBalanceDTO() {
        return new BalanceResponseFromNotificationControllerDTO(notificatorFeign.getBalance(), urlToReplenishBalance);
    }


    @PatchMapping("/phone")
    public CodeCallSecurityCodeDTO getPhoneCallSecurityDTO(@RequestParam String phone) {
        CodeCallSecurityCodeDTO codeCallSecurityCodeDTO = new CodeCallSecurityCodeDTO(notificatorFeign.callToClient(phone));
        return codeCallSecurityCodeDTO;
    }

    @PatchMapping("/email")
    public void email(@RequestParam String email) {
        System.out.println("Тут отправляется письмо с кодом на почту: " + email);
    }

    @PatchMapping("/codeEmail")
    public boolean getCodeEmail(@RequestParam int code) {
        //сравнеие кода
        if (code == 5555) {
            return true;
        }
        return false;
    }

    @PatchMapping("/codePhone")
    public boolean getCodePhone(@RequestParam int code) {
        //сравнеие кода
        if (code == 6666) {
            return true;
        }
        return false;
    }
}
