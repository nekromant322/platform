package com.override.controller.rest;

import com.override.model.UserSettings;
import com.override.service.UserSettingsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSettings")
public class UserSettingsRestController {

    @Autowired
    private UserSettingsService userSettingsService;

    @PatchMapping("/{userLogin}")
    @ApiOperation(value = "Сохраняет \"пользовательские настройки\" в БД, по сути разрешает или запрещает " +
            "отправлять уведомления пользователю в Телеграм и ВК)")
    public void patch(@RequestBody UserSettings userSettings,
                      @PathVariable String userLogin) {
        userSettingsService.save(userSettings, userLogin);
    }
}
