package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import dto.BalanceResponseFromNotificationControllerDTO;
import dto.CodeCallSecurityCodeDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Secured("ROLE_ADMIN")
    @PostMapping("/code")
    public CodeCallSecurityCodeDTO getCodeCallSecurityDTO() {
        CodeCallSecurityCodeDTO codeCallSecurityCodeDTO = new CodeCallSecurityCodeDTO(notificatorFeign.callToClient("79026526186"));
        return codeCallSecurityCodeDTO;
    }
}
